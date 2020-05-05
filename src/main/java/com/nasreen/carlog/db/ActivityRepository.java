package com.nasreen.carlog.db;

import com.nasreen.carlog.model.Activity;
import com.nasreen.carlog.model.ActivityType;
import com.nasreen.carlog.request.ActivityUpdate;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.RowMapper;
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
    public static final String INSERT_QUERY = String.format("INSERT INTO activities(id, type, record_id)  VALUES(:id, :type, :recordId)");
    public static final String SELECT_ALL_BY_ID = "SELECT * FROM activities WHERE record_id = :recordId";
    public static final String UPDATE = "UPDATE activities SET type = :type WHERE id = :id";
    public static final String DELETE = "DELETE FROM activities WHERE id = :id";
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
                .map(activityRowMapper())
                .findFirst());
    }

    public List<Activity> list(UUID recordId) {
        return jdbi.withHandle(handle -> handle.createQuery(SELECT_ALL_BY_ID)
                .bind("recordId", recordId.toString())
                .map(activityRowMapper())
                .list()
                );
    }

    private RowMapper<Activity> activityRowMapper() {
        return (rs, ctx) -> new Activity(UUID.fromString(rs.getString("id")),
                ActivityType.valueOf(rs.getString("type")),
                UUID.fromString(rs.getString("record_id")));
    }

    public Optional<Activity> update(Activity activity) {
        jdbi.withHandle(handle -> handle.createUpdate(UPDATE)
                .bind("type", activity.getType())
                .bind("id", activity.getType())
                .execute());
        return Optional.of(activity);
    }

    public Optional<UUID> delete(UUID id) {
        jdbi.withHandle(handle -> handle.createUpdate(DELETE)
                .bind("id", id.toString())
                .execute());
        return Optional.ofNullable(id);
    }
}
