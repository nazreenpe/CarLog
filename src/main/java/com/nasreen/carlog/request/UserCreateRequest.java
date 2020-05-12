package com.nasreen.carlog.request;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserCreateRequest {
    @NotBlank
    private String name;
    @NotBlank
    @Email
    private String emailId;
    @NotBlank
    @Length(min = 10)
    private String password;

    public UserCreateRequest() {
    }

    public UserCreateRequest(String name, String emailId, String password) {
        this.name = name;
        this.emailId = emailId;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getPassword() {
        return password;
    }
}
