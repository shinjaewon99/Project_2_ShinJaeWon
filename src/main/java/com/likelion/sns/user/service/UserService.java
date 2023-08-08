package com.likelion.sns.user.service;

import com.likelion.sns.auth.dto.response.AuthenticationResponse;
import com.likelion.sns.auth.service.JwtProviderService;
import com.likelion.sns.global.exception.NotExistUserException;
import com.likelion.sns.global.exception.NotInputException;
import com.likelion.sns.user.domain.User;
import com.likelion.sns.user.domain.UserRepository;
import com.likelion.sns.user.domain.UserRole;
import com.likelion.sns.user.dto.request.UserJoinRequest;
import com.likelion.sns.user.dto.request.logIn.UserLoginRequest;
import com.likelion.sns.user.dto.response.UserCommonResponse;
import com.likelion.sns.user.dto.response.UserReadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProviderService jwtProviderService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    // 회원가입 메소드
    public UserCommonResponse userJoin(final UserJoinRequest joinRequest) {
        validateUsername(joinRequest.getUserId()); // 아이디 미입력 검증
        validatePassword(joinRequest.getPassword()); // 비밀번호 미입력 검증

        User user = new User(joinRequest.getUserId(), passwordEncoder.encode(joinRequest.getPassword()),
                joinRequest.getEmail(), joinRequest.getPhone(), UserRole.USER);

        userRepository.save(user);

        String jwt = jwtProviderService.generateToken(user);
        AuthenticationResponse
                .builder()
                .token(jwt)
                .build();
        UserCommonResponse response = new UserCommonResponse();
        response.setMessage("회원가입이 완료되었습니다.");
        return response;
    }

    // 로그인 메소드
    public AuthenticationResponse userLogin(final UserLoginRequest loginRequest) {
        validateUsername(loginRequest.getUserId()); // 아이디 미입력 검증
        validatePassword(loginRequest.getPassword()); // 비밀번호 미입력 검증
        validateExistUsername(loginRequest.getUserId()); // 등록된 회원인지 검증

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUserId());

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUserId(),
                        loginRequest.getPassword(),
                        userDetails.getAuthorities()
                )
        );

        User user = userRepository.findByUsername(loginRequest.getUserId()).orElseThrow();
        String jwt = jwtProviderService.generateToken(user);
        return AuthenticationResponse
                .builder()
                .token(jwt)
                .build();
    }

    // 이미지 등록 메소드
    public UserCommonResponse postImg(final Long userId, final MultipartFile image) throws IOException {
        User findUser = validateExistUserId(userId); // 등록된 회원이 있는지 검증

        String imageLocation = String.format("image_profile/%d/", findUser.getId());
        String imageName = image.getOriginalFilename();
        String imagePath = imageLocation + imageName;

        try {
            Files.createDirectories(Path.of(imageLocation)); // 파일 업로드 위치
        } catch (IOException exception) {
            throw new IllegalArgumentException();
        }

        try {
            image.transferTo(Path.of(imagePath)); // 파일 업로드 처리
        } catch (IOException exception) {
            throw new IllegalArgumentException();
        }

        findUser.uploadImg(imagePath);
        userRepository.save(findUser);

        UserCommonResponse response = new UserCommonResponse();
        response.setMessage("프로필 등록이 완료되었습니다.");

        return response;
    }

    // 사용자 정보 조회 메소드
    public UserReadResponse readUser(final Long userId) {
        User findUser = validateExistUserId(userId);

        return new UserReadResponse(findUser.getUsername(), findUser.getProfileImg());
    }

    private void validateUsername(final String username) {
        if (username == null) {
            throw new NotInputException("아이디를 입력해 주세요.");
        }
    }

    private void validatePassword(final String password) {
        if (password == null) {
            throw new NotInputException("비밀번호를 입력해 주세요.");
        }
    }

    private void validateExistUsername(final String username) {
        userRepository.findByUsername(username)
                .orElseThrow(() -> new NotExistUserException("등록된 회원이 아닙니다."));
    }

    private User validateExistUserId(final Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotExistUserException("등록된 회원이 없습니다."));
    }

}
