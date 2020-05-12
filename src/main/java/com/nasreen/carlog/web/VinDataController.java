package com.nasreen.carlog.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nasreen.carlog.service.VinService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
    value = "/api/vin",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
)
public class VinDataController {
    private VinService vinService;

    @Autowired
    public VinDataController(VinService vinService) {
        this.vinService = vinService;
    }

    @RequestMapping(value = "/{vin}", method = RequestMethod.GET)
    public ResponseEntity<JsonNode> decode(@PathVariable String vin) {
        return vinService.decode(vin)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.badRequest().build());

    }
}
