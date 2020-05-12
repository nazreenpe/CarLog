package com.nasreen.carlog.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.DoubleStream;

@Service
public class VinService {
    public static String URL = "https://vpic.nhtsa.dot.gov/api/vehicles/decodevin/%s?format=json";
    private ObjectMapper objectMapper;
    private final OkHttpClient client;

    @Autowired
    public VinService(ObjectMapper objectMapper, OkHttpClient client) {
        this.objectMapper = objectMapper;
        this.client = client;
    }

    public Optional<JsonNode> decode(String vin) {
        String url = String.format(URL, vin);
        Request request = new Request.Builder()
            .url(url)
            .build();

        try {
            Response response = client.newCall(request).execute();
            if(response.isSuccessful()) {
                JsonNode node = objectMapper.readValue(response.body().string(), JsonNode.class);
                return Optional.of(node);
            } else {
                return Optional.empty();
            }
        } catch (Exception ignored) {
            return Optional.empty();
        }
    }
}
