package com.likelion.sns.user.dto.request;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

// 회원가입 클래스
@Getter
@Setter
public class UserJoinRequest {
    private String userId;
    private String password;
    @Email
    private String email;
    private String phone;
}
