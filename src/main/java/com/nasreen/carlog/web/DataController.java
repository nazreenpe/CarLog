package com.nasreen.carlog.web;

import com.nasreen.carlog.model.ActivityType;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/data",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
public class DataController {
    @RequestMapping(path = "/activityTypes", method = RequestMethod.GET)
    public Map<String, String> activityTypes() {
        Map<String, String> activityMap = new HashMap<>();
        Arrays.asList(ActivityType.values())
            .forEach(activityType -> activityMap.put(activityType.name(), activityType.getPublicName()));
        return activityMap;
    }
}
