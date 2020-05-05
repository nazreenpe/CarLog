package com.nasreen.carlog.db;

import com.nasreen.carlog.model.Record;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.RowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
public class RecordRepository {
    public static final String SELECT_QUERY = "SELECT * FROM records WHERE car_id = :carId AND id = :id";
    public static final String INSERT_QUERY = String.format("INSERT INTO records(id, date, car_id) " +
            " VALUES(:id, :date, :carId)");
    public static final String SELECT_ALL_BY_CAR_ID = "SELECT * FROM records WHERE car_id = :carId";
    public static final String UPDATE = "UPDATE records SET id = :id, date = :date," +
            " car_id = :carId WHERE id = :id";
    public static final String DELETE = "DELETE FROM records WHERE id = :id AND car_id = :carId";
    private Jdbi jdbi;

    @Autowired
    public RecordRepository(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public Record save(Record record) {
        return jdbi.withHandle(handle -> {
            handle.createUpdate(INSERT_QUERY)
                    .bind("id", record.getId().toString())
                    .bind("date", record.getDate())
                    .bind("carId", record.getCarId().toString())
                    .execute();
            return record;
        });
    }

    public Optional<Record> findById(UUID carId, UUID id) {
        return jdbi.withHandle(handle -> handle.createQuery(SELECT_QUERY)
                .bind("carId", carId.toString())
                .bind("id", id.toString())
                .map(recordRowMapper())
                .findFirst());
    }

    public List<Record> list(UUID carId) {
        return jdbi.withHandle(handle -> handle.createQuery(SELECT_ALL_BY_CAR_ID)
                .bind("carId", carId.toString())
                .map(recordRowMapper())
                .list()
                );
    }

    public Optional<Record> update(Record record) {
        jdbi.withHandle(handle -> handle.createUpdate(UPDATE)
                .bind("id", record.getId().toString())
                .bind("date", record.getDate())
                .bind("carId", record.getCarId().toString())
                .execute());
        return Optional.of(record);
    }

    public Optional<UUID> delete(UUID carId, UUID id) {
        jdbi.withHandle(handle -> handle.createUpdate(DELETE)
                .bind("id", id.toString())
                .bind("carId", carId.toString())
                .execute());
        return Optional.ofNullable(id);
    }

    private RowMapper<Record> recordRowMapper() {
        return (rs, ctx) -> new Record(UUID.fromString(rs.getString("id")),
                LocalDate.parse(rs.getString("date")),
                UUID.fromString(rs.getString("car_id")));
    }
}
