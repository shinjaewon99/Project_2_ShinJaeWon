package com.likelion.sns.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

// 회원가입 클래스
@Getter
@Setter
public class UserJoinRequest {

    @NotBlank(message = "아이디를 입력해주세요.")
    private String userId;
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @Email
    private String email;
    private String phone;
}
