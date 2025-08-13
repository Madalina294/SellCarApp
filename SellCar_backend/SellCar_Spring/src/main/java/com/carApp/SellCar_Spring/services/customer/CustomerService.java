package com.carApp.SellCar_Spring.services.customer;

import com.carApp.SellCar_Spring.dto.CarDto;

import java.io.IOException;
import java.util.List;

public interface CustomerService {
    boolean createCar(CarDto carDto) throws IOException;

    List<CarDto> getAllCars();

    CarDto getCarById(Long id);

    void deleteCar(Long id);

    boolean updateCar(Long id, CarDto carDto) throws IOException;
}
