package com.likelion.sns.user.dto.request.logIn;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

// 로그인 클래스
@Getter
@Setter
public class UserLoginRequest {
    @NotBlank(message = "아이디를 입력해주세요.")
    private String userId;
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;
}
