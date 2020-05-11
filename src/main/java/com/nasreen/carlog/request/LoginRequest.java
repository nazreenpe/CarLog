package com.nasreen.carlog.request;

import javax.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank
    private String email;
    @NotBlank
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
