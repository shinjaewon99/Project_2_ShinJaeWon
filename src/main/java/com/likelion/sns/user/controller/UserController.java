package com.likelion.sns.user.controller;

import com.likelion.sns.auth.dto.response.AuthenticationResponse;
import com.likelion.sns.user.dto.request.UserJoinRequest;
import com.likelion.sns.user.dto.request.logIn.UserLoginRequest;
import com.likelion.sns.user.dto.response.UserJoinResponse;
import com.likelion.sns.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<UserJoinResponse> join(@Valid @RequestBody final UserJoinRequest joinRequest) {
        return ResponseEntity.ok(userService.userJoin(joinRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody final UserLoginRequest loginRequest) {
        return ResponseEntity.ok(userService.userLogin(loginRequest));
    }
}
