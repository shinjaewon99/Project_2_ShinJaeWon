package com.likelion.sns.user.dto.request.logIn;

import lombok.Getter;
import lombok.Setter;

// 로그인 클래스
@Getter
@Setter
public class UserLoginRequest {
    private String userId;
    private String password;
}
