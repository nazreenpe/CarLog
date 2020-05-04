package com.nasreen.carlog.web;

import com.nasreen.carlog.model.Activity;
import com.nasreen.carlog.request.ActivityCreate;
import com.nasreen.carlog.service.ActivityService;
import com.nasreen.carlog.service.CarService;
import com.nasreen.carlog.service.MaintenanceRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
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
}
