package com.nasreen.carlog.request;

public class UserCreateRequest {
    private String username;
    private String emailId;
    private String password;

    public UserCreateRequest(String username, String emailId, String password) {
        this.username = username;
        this.emailId = emailId;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getPassword() {
        return password;
    }
}
