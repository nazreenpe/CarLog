package com.nasreen.carlog.service;

import com.nasreen.carlog.db.CarRepository;
import com.nasreen.carlog.model.Car;
import com.nasreen.carlog.request.CarCreateRequest;
import com.nasreen.carlog.request.CarUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CarService {
    private List<Car> cars = new ArrayList<>();
    private CarRepository repository;

    @Autowired
    public CarService(CarRepository repository) {
        this.repository = repository;
    }

    public Car create(CarCreateRequest request) {
        Car car = new Car(request.getMake(), request.getModel(), request.getYear(), request.getTrim());
        return repository.save(car);
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
                    request.getTrim().ifPresent(car::setTrim);
                    request.getMake().ifPresent(car::setMake);
                    request.getModel().ifPresent(car::setModel);
                    request.getYear().ifPresent(car::setYear);
                    return car;
                });
    }

    public void deleteAll() {
        cars.clear();
    }

    public List<Car> list() {
        return cars;
    }
}
