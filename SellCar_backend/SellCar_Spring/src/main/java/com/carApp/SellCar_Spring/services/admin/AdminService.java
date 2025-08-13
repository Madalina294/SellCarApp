package com.carApp.SellCar_Spring.services.admin;

import com.carApp.SellCar_Spring.dto.CarDto;

import java.util.List;

public interface AdminService {
    List<CarDto> getAllCars();
    CarDto getCarById(Long id);
    void deleteCar(Long id);
}
