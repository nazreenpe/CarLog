package com.nasreen.carlog.web;

import com.nasreen.carlog.model.Car;
import com.nasreen.carlog.request.CarCreateRequest;
import com.nasreen.carlog.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/cars",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class CarController {
    private CarService service;

    @Autowired
    public CarController(CarService service) {
        this.service = service;
    }

    @RequestMapping("")
    public Car create(@RequestBody CarCreateRequest createRequest) {
        return service.create(createRequest);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Car> get(@PathVariable UUID id) {
        return service.get(id)
                .map(car -> ResponseEntity.ok(car))
                .orElse(ResponseEntity.notFound().build());
    }
}
