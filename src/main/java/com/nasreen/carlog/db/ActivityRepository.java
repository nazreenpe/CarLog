package com.nasreen.carlog.db;

import com.nasreen.carlog.model.Activity;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public class ActivityRepository {
    private Jdbi jdbi;

    @Autowired
    public ActivityRepository(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public Activity save(Activity activity) {
        return jdbi.withHandle(handle -> {
            handle.createUpdate("INSERT INTO activities(id, type, record_id)" +
                    "  VALUES(:id, :type, :record_id)")
                    .bind("id", activity.getId())
                    .bind("type", activity.getType())
                    .bind("record_id", activity.getRecordId())
                    .execute();
            return activity;
        });
    }
}
