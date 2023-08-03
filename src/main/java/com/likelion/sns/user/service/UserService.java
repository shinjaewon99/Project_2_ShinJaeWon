package com.likelion.sns.user.service;

import com.likelion.sns.auth.dto.response.AuthenticationResponse;
import com.likelion.sns.auth.service.JwtProviderService;
import com.likelion.sns.user.domain.User;
import com.likelion.sns.user.domain.UserRepository;
import com.likelion.sns.user.domain.UserRole;
import com.likelion.sns.user.dto.request.UserJoinRequest;
import com.likelion.sns.user.dto.request.logIn.UserLoginRequest;
import com.likelion.sns.user.dto.response.UserJoinResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProviderService jwtProviderService;
    private final AuthenticationManager authenticationManager;

    // 회원가입 메소드
    public UserJoinResponse userJoin(final UserJoinRequest joinRequest) {
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
        UserJoinResponse response = new UserJoinResponse();
        response.setMessage("회원가입이 완료되었습니다.");
        return response;
    }

    // 로그인 메소드
    public AuthenticationResponse userLogin(final UserLoginRequest loginRequest){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUserId(),
                        loginRequest.getPassword()
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

}
