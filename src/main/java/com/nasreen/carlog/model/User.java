package com.nasreen.carlog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.UUID;

public class User {
    private String username;
    private String emailId;
    private String encryptedPassword;
    private Boolean isAdmin;
    private UUID id;

    public User() {
    }

    public User(String username, String emailId, String encryptedPassword) {
        this.username = username;
        this.emailId = emailId;
        this.encryptedPassword = encryptedPassword;
        this.isAdmin = false;
        this.id = UUID.randomUUID();
    }

    public String getUsername() {
        return username;
    }

    public String getEmailId() {
        return emailId;
    }

    @JsonIgnore
    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public UUID getId() {
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }
}
