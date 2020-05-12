package com.nasreen.carlog.service;

import com.nasreen.carlog.db.CarRepository;
import com.nasreen.carlog.model.Car;
import com.nasreen.carlog.request.CarRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CarService {
    private CarRepository repository;

    @Autowired
    public CarService(CarRepository repository) {
        this.repository = repository;
    }

    public Car create(CarRequest request, UUID userId) {
        Car car = new Car(request.getMake(), request.getModel(), request.getYear(), request.getTrim(), userId);
        return repository.save(car);
    }

    public Optional<Car> get(UUID id) {
        return repository.findById(id);
    }

    public Optional<UUID> delete(UUID id) {
        return repository.findById(id).flatMap(car -> repository.delete(id));
    }

    public Optional<Car> update(UUID id, CarRequest request) {
        return repository.findById(id)
                .flatMap(car -> {
                    car.setMake(request.getMake());
                    car.setModel(request.getModel());
                    car.setYear(request.getYear());
                    car.setTrim(request.getTrim());
                    return repository.update(car);
                });
    }

    public void deleteAll() {
        repository.deleteAll();
    }

    public List<Car> list() {
        return repository.list();
    }
}
