package com.likelion.sns.user.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserReadResponse {
    private String userId;
    private String profileImg;

    public UserReadResponse(String userId, String profileImg) {
        this.userId = userId;
        this.profileImg= profileImg;
    }
}
