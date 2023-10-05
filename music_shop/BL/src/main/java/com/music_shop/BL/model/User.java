package com.music_shop.BL.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    public enum Role {
        CUSTOMER,
        EMPLOYEE,
        UNREGISTERED;
    }
    private String login;
    private byte[] password;
    private Role role;
    private Card card;


    @Builder
    public User(String login, byte[] password, Role role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }
}
