package com.strong.news.model.auth;

import lombok.Data;

@Data
public class AuthenticationRequest {
    private String email;
    private String password;

}
