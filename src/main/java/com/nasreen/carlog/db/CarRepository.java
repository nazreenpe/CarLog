package com.nasreen.carlog.db;

import com.nasreen.carlog.model.Car;
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
public class CarRepository {
    public static final String SELECT_QUERY = "SELECT * FROM cars WHERE id = :id";
    public static final String INSERT_QUERY = "INSERT INTO cars(id, make, model, year, trim)" +
            "  VALUES(:id, :make, :model, :year, :trim)";
    public static final String SELECT_ALL_BY_ID = "SELECT * FROM cars WHERE record_id = :recordId";
    public static final String UPDATE = "UPDATE cars SET type = :type WHERE id = :id";
    public static final String DELETE = "DELETE FROM cars WHERE id = :id";
    private Jdbi jdbi;

    @Autowired
    public CarRepository(Jdbi jdbi) {
        this.jdbi = jdbi;
    }

    public Car save(Car car) {
        return jdbi.withHandle(handle -> {
            handle.createUpdate(INSERT_QUERY)
                    .bind("id", car.getId().toString())
                    .bind("make", car.getMake())
                    .bind("model", car.getModel())
                    .bind("year", car.getYear())
                    .bind("trim", car.getTrim())
                    .execute();
            return car;
        });
    }

    public Optional<Car> findById(UUID recordId, UUID id) {
        return jdbi.withHandle(handle -> handle.createQuery(SELECT_QUERY)
                .bind("recordId", recordId.toString())
                .bind("id", id.toString())
                .map(carRowMapper())
                .findFirst());
    }

    public List<Car> list(UUID recordId) {
        return jdbi.withHandle(handle -> handle.createQuery(SELECT_ALL_BY_ID)
                .bind("recordId", recordId.toString())
                .map(carRowMapper())
                .list()
                );
    }

    private RowMapper<Car> carRowMapper() {
        return (rs, ctx) -> new Car(UUID.fromString(rs.getString("id")),
                rs.getString("make"),
                rs.getString("model"),
                rs.getInt("year"),
                rs.getString("trim"));
    }

    public Optional<Car> update(Car car) {
        jdbi.withHandle(handle -> handle.createUpdate(UPDATE)
                .bind("id", car.getId().toString())
                .bind("make", car.getMake())
                .bind("model", car.getModel())
                .bind("year", car.getYear())
                .bind("trim", car.getTrim())
                .execute());
        return Optional.of(car);
    }

    public Optional<UUID> delete(UUID id) {
        jdbi.withHandle(handle -> handle.createUpdate(DELETE)
                .bind("id", id.toString())
                .execute());
        return Optional.ofNullable(id);
    }
}
