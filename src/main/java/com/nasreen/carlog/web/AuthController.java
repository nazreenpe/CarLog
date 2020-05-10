package com.nasreen.carlog.web;

import com.nasreen.carlog.auth.AuthFilter;
import com.nasreen.carlog.model.User;
import com.nasreen.carlog.request.UserCreateRequest;
import com.nasreen.carlog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping(
        value = "/api/auth",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class AuthController {
    private UserService service;

    @Autowired
    public AuthController(UserService service) {
        this.service = service;
    }

    @RequestMapping("/signup")
    public ResponseEntity<User> create(@Validated @RequestBody UserCreateRequest createRequest,
                                       HttpServletRequest servletRequest) {
        return this.service.create(createRequest)
                .map(user -> {
                    servletRequest.getSession().setAttribute(AuthFilter.LOGGED_IN_USER, user);
                    return ResponseEntity.ok(user);
                })
                .orElse(ResponseEntity.badRequest().build());
    }
}
