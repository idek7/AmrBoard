package me.amr.model;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginObj {
    private String userId;
    private String userPW;

    public LoginObj() {}

    @Builder
    public LoginObj(String userId, String userPw) {
        this.userId = userId;
        this.userPW = userPw;
    }
}
