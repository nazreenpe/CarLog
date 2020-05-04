package com.nasreen.carlog.service;

import com.nasreen.carlog.model.Activity;
import com.nasreen.carlog.request.ActivityCreate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ActivityService {
    private List<Activity> activities = new ArrayList<>();

    public Activity create(UUID recordId, ActivityCreate request) {
        Activity activity = new Activity(request.getType(), recordId);
        activities.add(activity);
        return activity;
    }
}
