package com.nasreen.carlog.service;

import com.nasreen.carlog.model.Activity;
import com.nasreen.carlog.request.ActivityCreate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ActivityService {
    private List<Activity> activities = new ArrayList<>();

    public Activity create(UUID recordId, ActivityCreate request) {
        Activity activity = new Activity(request.getType(), recordId);
        activities.add(activity);
        return activity;
    }

    public List<Activity> list(UUID recordId) {
        return activities.stream()
                .filter(activity -> activity.getRecordId().equals(recordId))
                .collect(Collectors.toList());
    }

    public Optional<Activity> get(UUID recordId, UUID id) {
        return activities.stream()
                .filter(activity -> activity.getRecordId().equals(recordId) &&
                        activity.getId().equals(id))
                .findFirst();
    }
}
