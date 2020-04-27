package com.nasreen.carlog.service;

import com.nasreen.carlog.model.Car;
import com.nasreen.carlog.request.CarCreateRequest;
import com.nasreen.carlog.request.CarUpdateRequest;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CarService {
    private List<Car> cars = new ArrayList<>();

    public Car create(CarCreateRequest request) {
        Car car = new Car(request.getMake(), request.getModel(), request.getYear(), request.getTrim());
        cars.add(car);
        return car;
    }

    public Optional<Car> get(UUID id) {
        return cars.stream()
                .filter(car -> car.getId().equals(id))
                .findFirst();
    }

    public Optional<UUID> delete(UUID id) {
        return get(id)
                .map(car -> {
                    cars.remove(car);
                    return id;
                });
    }

    public Optional<Car> update(UUID id, CarUpdateRequest request) {
        return get(id)
                .map(car -> {
                    car.setTrim(request.getTrim().get());
                    return car;
                });
    }
}
