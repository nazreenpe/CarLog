package com.nasreen.carlog.service;

import com.nasreen.carlog.model.Car;
import com.nasreen.carlog.request.CarCreateRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarService {
    private List<Car> cars = new ArrayList<>();

    public Car create(CarCreateRequest request) {
        Car car = new Car(request.getMake(), request.getModel(), request.getYear(), request.getTrim());
        cars.add(car);
        return car;
    }
}
