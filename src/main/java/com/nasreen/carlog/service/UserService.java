package com.nasreen.carlog.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.nasreen.carlog.model.User;
import com.nasreen.carlog.request.UserCreateRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private List<User> users = new ArrayList<>();

    public User create(UserCreateRequest request) {
        byte[] hashed = BCrypt.withDefaults().hash(10, request.getPassword().getBytes());
        User user = new User(request.getUsername(), request.getEmailId(), hashed.toString());
        users.add(user);
        return user;
    }
}