package com.nasreen.carlog.web;

import com.nasreen.carlog.model.Record;
import com.nasreen.carlog.model.User;
import com.nasreen.carlog.request.RecordCreateRequest;
import com.nasreen.carlog.request.RecordUpdate;
import com.nasreen.carlog.service.CarService;
import com.nasreen.carlog.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/cars/{carId}/mrs",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class RecordController {
    private final RecordService service;
    private final CarService carService;

    @Autowired
    public RecordController(RecordService service, CarService carService) {
        this.service = service;
        this.carService = carService;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Record> create(
        @PathVariable UUID carId,
        @RequestBody RecordCreateRequest request,
        Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return carService.get(carId, user.getId())
                .map(car -> ResponseEntity.status(HttpStatus.CREATED).body(service.create(request, car)))
                .orElse(ResponseEntity.badRequest().build());
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<Record>> list(@PathVariable UUID carId,
                                             Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return carService.get(carId, user.getId())
                .map(car -> ResponseEntity.ok(service.list(car)))
                .orElse(ResponseEntity.badRequest().build());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Optional<Record>> get(@PathVariable UUID carId,
                                                @PathVariable UUID id,
                                                Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return carService.get(carId, user.getId())
                .map(car -> ResponseEntity.ok(service.get(car, id)))
                .orElse(ResponseEntity.notFound().build());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Optional<Record>> update(
            @PathVariable UUID carId,
            @PathVariable UUID id,
            @RequestBody RecordUpdate update,
            Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return carService.get(carId, user.getId())
                .map(car -> ResponseEntity.ok(service.update(car, id, update)))
                .orElse(ResponseEntity.notFound().build());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity delete(
            @PathVariable UUID carId,
            @PathVariable UUID id, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return carService.get(carId, user.getId())
                .map(car -> {
                    service.delete(car, id);
                    return ResponseEntity.accepted().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
