package com.likelion.sns.user.controller;

import com.likelion.sns.auth.dto.response.AuthenticationResponse;
import com.likelion.sns.user.dto.request.UserJoinRequest;
import com.likelion.sns.user.dto.request.logIn.UserLoginRequest;
import com.likelion.sns.user.dto.response.UserCommonResponse;
import com.likelion.sns.user.dto.response.UserReadResponse;
import com.likelion.sns.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Slf4j
public class UserController {
    private final UserService userService;
    private final UserDetailsService userDetailsService;

    @PostMapping("/join")
    public ResponseEntity<UserCommonResponse> join(@RequestBody final UserJoinRequest joinRequest) {
        return ResponseEntity.ok(userService.userJoin(joinRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody final UserLoginRequest loginRequest) {
        return ResponseEntity.ok(userService.userLogin(loginRequest));
    }

    @PostMapping(value = "/postProfile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserCommonResponse> getUserDetails(@RequestParam("image") final MultipartFile image,
                                                             final Authentication authentication) {
        String userId = authentication.getName();

        return ResponseEntity.ok(userService.postImg(image, userId));
    }

    @GetMapping("/myProfile/read")
    public UserReadResponse myProfileRead(final Authentication authentication) {
        return userService.readUserOne(authentication);
    }
}
