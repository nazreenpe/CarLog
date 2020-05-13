package com.nasreen.carlog.web;

import com.nasreen.carlog.model.Activity;
import com.nasreen.carlog.model.Document;
import com.nasreen.carlog.model.User;
import com.nasreen.carlog.request.DocumentRequest;
import com.nasreen.carlog.service.DocumentService;
import com.nasreen.carlog.service.CarService;
import com.nasreen.carlog.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/cars/{carId}/mrs/{recordId}/d",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class DocumentController {
    private DocumentService service;
    private final CarService carService;
    private final RecordService recordService;

    @Autowired
    public DocumentController(DocumentService service, CarService carService, RecordService recordService) {
        this.service = service;
        this.carService = carService;
        this.recordService = recordService;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<Document> create(
            @PathVariable UUID carId,
            @PathVariable UUID recordId,
            @Valid @RequestBody DocumentRequest request,
            Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return carService.get(carId, user.getId())
                .flatMap(car -> recordService.get(car, recordId)
                        .map(record -> service.create(record.getId(), request)))
                .map(activity -> ResponseEntity.status(HttpStatus.CREATED).body(activity))
                .orElse(ResponseEntity.badRequest().build());
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<List<Document>> list(
        @PathVariable UUID carId,
        @PathVariable UUID recordId,
        Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return carService.get(carId, user.getId())
                .flatMap(car -> recordService.get(car, recordId)
                        .map(record -> service.list(record.getId())))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Document> get(
            @PathVariable UUID carId,
            @PathVariable UUID recordId,
            @PathVariable UUID id,
            Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return carService.get(carId, user.getId())
                .flatMap(car -> recordService.get(car, recordId)
                        .flatMap(record -> service.get(record.getId(), id)))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Document> update(
            @PathVariable UUID carId,
            @PathVariable UUID recordId,
            @PathVariable UUID id,
            @RequestBody DocumentRequest request,
            Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return carService.get(carId, user.getId())
                .flatMap(car -> recordService.get(car, recordId)
                        .flatMap(record -> service.update(record.getId(), id, request)))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<UUID> delete(
            @PathVariable UUID carId,
            @PathVariable UUID recordId,
            @PathVariable UUID id,
            Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return carService.get(carId, user.getId())
                .flatMap(car -> recordService.get(car, recordId)
                        .flatMap(record -> service.delete(record.getId(), id)))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
