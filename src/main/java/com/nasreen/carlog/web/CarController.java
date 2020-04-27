package com.nasreen.carlog.web;

import com.nasreen.carlog.model.Car;
import com.nasreen.carlog.request.CarCreateRequest;
import com.nasreen.carlog.request.CarUpdateRequest;
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

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<UUID> delete(@PathVariable UUID id) {
        return service.delete(id)
                .map(deletedId -> ResponseEntity.ok(deletedId))
                .orElse(ResponseEntity.notFound().build());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Car> update(@PathVariable UUID id, @RequestBody CarUpdateRequest request) {
        return service.update(id, request)
                .map(deletedId -> ResponseEntity.ok(deletedId))
                .orElse(ResponseEntity.notFound().build());
    }
}
