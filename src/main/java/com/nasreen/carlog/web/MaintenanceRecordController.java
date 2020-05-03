package com.nasreen.carlog.web;

import com.nasreen.carlog.model.MaintenanceRecord;
import com.nasreen.carlog.request.MaintenanceRecordCreateRequest;
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
@RequestMapping(value = "/cars/{carId}/mrs",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class MaintenanceRecordController {
    private final MaintenanceRecordService service;
    private final CarService carService;

    @Autowired
    public MaintenanceRecordController(MaintenanceRecordService service, CarService carService) {
        this.service = service;
        this.carService = carService;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<MaintenanceRecord> create(
            @PathVariable UUID carId,
            @RequestBody MaintenanceRecordCreateRequest request) {
        return carService.get(carId)
                .map(car -> ResponseEntity.status(HttpStatus.CREATED).body(service.create(request, car)))
                .orElse(ResponseEntity.badRequest().build());
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<MaintenanceRecord>> list(@PathVariable UUID carId) {
        return carService.get(carId)
                .map(car -> ResponseEntity.ok(service.list(car)))
                .orElse(ResponseEntity.badRequest().build());
    }
}
