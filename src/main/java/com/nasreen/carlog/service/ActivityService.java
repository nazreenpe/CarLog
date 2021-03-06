package com.nasreen.carlog.service;

import com.nasreen.carlog.db.ActivityRepository;
import com.nasreen.carlog.model.Activity;
import com.nasreen.carlog.request.ActivityRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ActivityService {
    private ActivityRepository repository;

    @Autowired
    public ActivityService(ActivityRepository repository) {
        this.repository = repository;
    }

    public Activity create(UUID recordId, ActivityRequest request) {
        Activity activity = new Activity(request.getType(), request.getNotes(), recordId);
        repository.save(activity);
        return activity;
    }

    public List<Activity> list(UUID recordId) {
        return repository.list(recordId);
    }

    public Optional<Activity> get(UUID recordId, UUID id) {
        return repository.findById(recordId, id);
    }

    public Optional<Activity> update(UUID recordId, UUID id, ActivityRequest request) {
        return repository.findById(recordId, id)
                .flatMap(activity -> {
                    activity.setNotes(request.getNotes());
                    activity.setType(request.getType());
                    return repository.update(activity);
                });
    }

    public Optional<UUID> delete(UUID recordId, UUID id) {
        return repository.findById(recordId, id)
                .flatMap(activity -> repository.delete(id));
    }
}
