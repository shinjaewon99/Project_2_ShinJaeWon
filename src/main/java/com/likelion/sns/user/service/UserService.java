package com.likelion.sns.user.service;

import com.likelion.sns.auth.dto.response.AuthenticationResponse;
import com.likelion.sns.auth.service.JwtProviderService;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
        User user = User.builder()
                .username(joinRequest.getUserId())
                .password(passwordEncoder.encode(joinRequest.getPassword()))
                .email(joinRequest.getEmail())
                .phone(joinRequest.getPhone())
                .userRole(UserRole.USER)
                .build();
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
    public UserCommonResponse postImg(MultipartFile image, String userId) {


        User findUserId = userRepository
                .findByUsername(userId).orElseThrow(() -> new UsernameNotFoundException("로그인한 회원이 아닙니다."));

        String imageLocation = String.format("image/%d/", findUserId.getId());
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

        findUserId.uploadImg(imagePath);
        userRepository.save(findUserId);

        UserCommonResponse response = new UserCommonResponse();
        response.setMessage("프로필 등록이 완료되었습니다.");

        return response;
    }

    public UserReadResponse readUserOne(Authentication authentication) {
        User findUserId = userRepository
                .findByUsername(authentication.getName()).orElseThrow(() -> new UsernameNotFoundException("로그인한 회원이 아닙니다."));

        return new UserReadResponse(findUserId.getUsername(),
                findUserId.getProfileImg(), findUserId.getEmail(), findUserId.getPhone());
    }
}
