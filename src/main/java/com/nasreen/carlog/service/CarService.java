package com.nasreen.carlog.service;

import com.nasreen.carlog.db.CarRepository;
import com.nasreen.carlog.model.Car;
import com.nasreen.carlog.request.CarCreateRequest;
import com.nasreen.carlog.request.CarUpdateRequest;
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

    public Car create(CarCreateRequest request) {
        Car car = new Car(request.getMake(), request.getModel(), request.getYear(), request.getTrim());
        return repository.save(car);
    }

    public Optional<Car> get(UUID id) {
        return repository.findById(id);
    }

    public Optional<UUID> delete(UUID id) {
        return repository.findById(id).flatMap(car -> repository.delete(id));
    }

    public Optional<Car> update(UUID id, CarUpdateRequest request) {
        return repository.findById(id)
                .flatMap(car -> {
                    request.getTrim().ifPresent(car::setTrim);
                    request.getMake().ifPresent(car::setMake);
                    request.getModel().ifPresent(car::setModel);
                    request.getYear().ifPresent(car::setYear);
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
