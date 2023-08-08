package com.likelion.sns.user.controller;

import com.likelion.sns.auth.dto.response.AuthenticationResponse;
import com.likelion.sns.user.dto.request.UserJoinRequest;
import com.likelion.sns.user.dto.request.logIn.UserLoginRequest;
import com.likelion.sns.user.dto.response.UserCommonResponse;
import com.likelion.sns.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<UserCommonResponse> join(@RequestBody @Valid final UserJoinRequest joinRequest) {
        return ResponseEntity.ok(userService.userJoin(joinRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody final UserLoginRequest loginRequest) {
        return ResponseEntity.ok(userService.userLogin(loginRequest));
    }

    @PostMapping(value = "/{userId}/postProfile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserCommonResponse> getUserDetails(@PathVariable final Long userId,
                                                             @RequestParam("image") final MultipartFile image) throws IOException {
        return ResponseEntity.ok(userService.postImg(userId, image));
    }
}
