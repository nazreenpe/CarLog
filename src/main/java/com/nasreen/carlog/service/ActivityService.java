package com.nasreen.carlog.service;

import com.nasreen.carlog.db.ActivityRepository;
import com.nasreen.carlog.model.Activity;
import com.nasreen.carlog.request.ActivityCreate;
import com.nasreen.carlog.request.ActivityUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ActivityService {
    private List<Activity> activities = new ArrayList<>();
    private ActivityRepository repository;

    @Autowired
    public ActivityService(ActivityRepository repository) {
        this.repository = repository;
    }

    public Activity create(UUID recordId, ActivityCreate request) {
        Activity activity = new Activity(request.getType(), recordId);
        activities.add(activity);
        repository.save(activity);
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

    public Optional<Activity> update(UUID recordId, UUID id, ActivityUpdate request) {
        return this.get(recordId, id)
                .map(activity -> {
                    request.getType().ifPresent(updateType -> activity.setType(updateType));
                    return activity;
                });
    }

    public Optional<UUID> delete(UUID recordId, UUID id) {
        return this.get(recordId, id)
                .map(activity -> {
                    activities.remove(activity);
                    return activity.getId();
                });
    }
}
