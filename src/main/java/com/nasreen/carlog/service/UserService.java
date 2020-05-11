package com.nasreen.carlog.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.nasreen.carlog.db.UserRepository;
import com.nasreen.carlog.model.User;
import com.nasreen.carlog.request.UserCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private List<User> users = new ArrayList<>();
    private UserRepository repository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> create(UserCreateRequest request) {
        String hashed = passwordEncoder.encode(request.getPassword());
        User user = new User(request.getName(), request.getEmailId(), hashed);
        users.add(user);
        return repository.save(user);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public Optional<User> verify(String email, String password) {
        return repository.findByEmail(email)
                .filter(user -> passwordEncoder.matches(password, user.getEncryptedPassword()));
    }
}
