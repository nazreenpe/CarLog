package com.nasreen.carlog.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.nasreen.carlog.db.UserRepository;
import com.nasreen.carlog.model.User;
import com.nasreen.carlog.request.UserCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private List<User> users = new ArrayList<>();
    private UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User create(UserCreateRequest request) {
        byte[] hashed = BCrypt.withDefaults().hash(10, request.getPassword().getBytes());
        User user = new User(request.getName(), request.getEmailId(), hashed.toString());
        users.add(user);
        repository.save(user);
        return user;
    }

    public void deleteAll() {
        repository.deleteAll();
    }
}
