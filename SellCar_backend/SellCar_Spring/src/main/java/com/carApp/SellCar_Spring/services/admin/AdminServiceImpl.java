package com.carApp.SellCar_Spring.services.admin;

import com.carApp.SellCar_Spring.dto.CarDto;
import com.carApp.SellCar_Spring.entities.Car;
import com.carApp.SellCar_Spring.repositories.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final CarRepository carRepository;

    @Override
    public List<CarDto> getAllCars() {
        return carRepository.findAll().stream().map(Car::getCarDto).collect(Collectors.toList());
    }
}
