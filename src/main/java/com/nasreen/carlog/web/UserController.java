package com.nasreen.carlog.web;

import com.nasreen.carlog.model.User;
import com.nasreen.carlog.request.UserCreateRequest;
import com.nasreen.carlog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(
        value = "/users",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class UserController {
    private UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @RequestMapping("")
    public User create(@RequestBody UserCreateRequest createRequest){
        return this.service.create(createRequest);
    }
}
