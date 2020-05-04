package com.nasreen.carlog.web;

import com.nasreen.carlog.model.Activity;
import com.nasreen.carlog.request.ActivityCreate;
import com.nasreen.carlog.request.ActivityUpdate;
import com.nasreen.carlog.service.ActivityService;
import com.nasreen.carlog.service.CarService;
import com.nasreen.carlog.service.MaintenanceRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/cars/{carId}/mrs/{recordId}/as",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class ActivityController {
    private ActivityService service;
    private final CarService carService;
    private final MaintenanceRecordService recordService;

    @Autowired
    public ActivityController(ActivityService service, CarService carService, MaintenanceRecordService recordService) {
        this.service = service;
        this.carService = carService;
        this.recordService = recordService;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Activity> create(
            @PathVariable UUID carId,
            @PathVariable UUID recordId,
            @RequestBody ActivityCreate request) {
        return carService.get(carId)
                .flatMap(car -> recordService.get(car, recordId)
                        .map(record -> service.create(record.getId(), request)))
                .map(activity -> ResponseEntity.status(HttpStatus.CREATED).body(activity))
                .orElse(ResponseEntity.badRequest().build());
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<Activity>> list(
            @PathVariable UUID carId,
            @PathVariable UUID recordId) {
        return carService.get(carId)
                .flatMap(car -> recordService.get(car, recordId)
                        .map(record -> service.list(record.getId())))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Activity> get(
            @PathVariable UUID carId,
            @PathVariable UUID recordId,
            @PathVariable UUID id) {
        return carService.get(carId)
                .flatMap(car -> recordService.get(car, recordId)
                        .flatMap(record -> service.get(record.getId(), id)))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Activity> update(
            @PathVariable UUID carId,
            @PathVariable UUID recordId,
            @PathVariable UUID id,
            @RequestBody ActivityUpdate request) {
        return carService.get(carId)
                .flatMap(car -> recordService.get(car, recordId)
                        .flatMap(record -> service.update(record.getId(), id, request)))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<UUID> delete(
            @PathVariable UUID carId,
            @PathVariable UUID recordId,
            @PathVariable UUID id) {
        return carService.get(carId)
                .flatMap(car -> recordService.get(car, recordId)
                        .flatMap(record -> service.delete(record.getId(), id)))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}