package com.nasreen.carlog.web;

import com.nasreen.carlog.auth.AuthFilter;
import com.nasreen.carlog.model.Car;
import com.nasreen.carlog.model.User;
import com.nasreen.carlog.request.CarRequest;
import com.nasreen.carlog.service.CarService;
import org.hibernate.type.PrimitiveCharacterArrayNClobType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/cars",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class CarController {
    private CarService service;

    @Autowired
    public CarController(CarService service) {
        this.service = service;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Car create(@Validated @RequestBody CarRequest createRequest, Authentication authentication) {
         User user = (User) authentication.getPrincipal();
        return service.create(createRequest, user.getId());
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Car> list(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return service.list(user.getId());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Car> get(@PathVariable UUID id, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return service.get(id, user.getId())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<UUID> delete(@PathVariable UUID id, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return service.delete(id, user.getId())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Car> update(@PathVariable UUID id,
                                      @Validated @RequestBody CarRequest request,
                                      Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return service.update(id, user.getId(), request)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
