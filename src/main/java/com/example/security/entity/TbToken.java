package com.example.security.entity;

import lombok.Data;

@Data
public class TbToken {
    private long id;
    private String username;
    private String token;
    private int isout;

    public TbToken() {
    }

    public TbToken(String token) {
        this.token = token;
    }

    public TbToken(String username, String token) {
        this.username = username;
        this.token = token;
    }

    public TbToken(String token, int isout) {
        this.token = token;
        this.isout = isout;
    }
}
