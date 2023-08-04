package com.likelion.sns.user.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserReadResponse {
    private String username;
    private String profileImg;
    private String email;
    private String phone;

    public UserReadResponse(String username, String profileImg, String email, String phone) {
        this.username = username;
        this.profileImg= profileImg;
        this.email = email;
        this.phone = phone;
    }
}
