package com.strong.news.model.auth;

import lombok.Data;

@Data
public class RegisterRequest {
    private String fullName;
    private String email;
    private String password;

}
