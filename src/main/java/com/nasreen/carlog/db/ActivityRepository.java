package com.nasreen.carlog.db;

import com.nasreen.carlog.model.Activity;
import com.nasreen.carlog.model.ActivityType;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
public class ActivityRepository {
    public static final String SELECT_QUERY = "SELECT * FROM activities WHERE record_id = :recordId AND id = :id";
    public static final String INSERT_QUERY = "INSERT INTO activities(id, type, record_id)" +
            "  VALUES(:id, :type, :recordId)";
    private Jdbi jdbi;

    @Autowired
    public ActivityRepository(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public Activity save(Activity activity) {
        return jdbi.withHandle(handle -> {
            handle.createUpdate(INSERT_QUERY)
                    .bind("id", activity.getId().toString())
                    .bind("type", activity.getType())
                    .bind("recordId", activity.getRecordId().toString())
                    .execute();
            return activity;
        });
    }

    public Optional<Activity> findById(UUID recordId, UUID id) {
        return jdbi.withHandle(handle -> handle.createQuery(SELECT_QUERY)
                .bind("recordId", recordId.toString())
                .bind("id", id.toString())
                .map((rs, ctx) -> new Activity(UUID.fromString(rs.getString("id")),
                        ActivityType.valueOf(rs.getString("type")),
                        UUID.fromString(rs.getString("record_id"))))
                .findFirst());
    }

    public List<Activity> list(UUID recordId) {
        return jdbi.withHandle(handle -> handle.createQuery("SELECT * FROM activities WHERE record_id = :recordId")
                .bind("recordId", recordId.toString())
                .map((rs, ctx) -> new Activity(UUID.fromString(rs.getString("id")),
                        ActivityType.valueOf(rs.getString("type")),
                        UUID.fromString(rs.getString("record_id"))))
                .list()
                );
    }
}
