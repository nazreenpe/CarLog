package com.nasreen.carlog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.UUID;

public class User implements Serializable {
    private String name;
    private String emailId;
    private String encryptedPassword;
    private Boolean isAdmin;
    private UUID id;

    public User() {
    }

    public User(String name, String emailId, String encryptedPassword) {
        this.name = name;
        this.emailId = emailId;
        this.encryptedPassword = encryptedPassword;
        this.isAdmin = false;
        this.id = UUID.randomUUID();
    }

    public User(UUID id, String name, String emailId, String encryptedPassword) {
        this.name = name;
        this.emailId = emailId;
        this.encryptedPassword = encryptedPassword;
        this.isAdmin = false;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getEmailId() {
        return emailId;
    }

    @JsonIgnore
    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    @JsonIgnore
    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public UUID getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
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
