package com.nasreen.carlog.web;

import com.amazonaws.HttpMethod;
import com.nasreen.carlog.auth.AuthFilter;
import com.nasreen.carlog.model.*;
import com.nasreen.carlog.request.CarRequest;
import com.nasreen.carlog.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/cars",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class CarController {
    private CarService service;
    private final RecordService recordService;
    private final ActivityService activityService;
    private final DocumentService documentService;
    private AwsSignedUrlService signedUrlService;

    @Autowired
    public CarController(CarService service,
                         RecordService recordService,
                         ActivityService activityService,
                         DocumentService documentService,
                         AwsSignedUrlService signedUrlService) {
        this.service = service;
        this.recordService = recordService;
        this.activityService = activityService;
        this.documentService = documentService;
        this.signedUrlService = signedUrlService;
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

    @RequestMapping(value = "/{id}/export", method = RequestMethod.GET)
    public ResponseEntity<CarExport> export(@PathVariable UUID id, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return service.get(id, user.getId())
            .map(car -> new CarExport(user, car, recordService.list(car)
                .stream()
                .map(record -> new RecordExport(record,
                    activityService.list(record.getId()),
                    documentService.list(record.getId()).stream()
                        .map(document -> {
                            URL url = signedUrlService.generateUrl(document.getPath(), HttpMethod.GET);
                            return new DocumentExport(document.getFilename(), document.getDescription(), url);
                        })
                        .collect(Collectors.toList())))
                .collect(Collectors.toList())))
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
