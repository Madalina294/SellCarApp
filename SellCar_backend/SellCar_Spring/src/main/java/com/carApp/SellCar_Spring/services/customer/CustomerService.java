package com.carApp.SellCar_Spring.services.customer;

import com.carApp.SellCar_Spring.dto.CarDto;

import java.io.IOException;
import java.util.List;

public interface CustomerService {
    boolean createCar(CarDto carDto) throws IOException;

    List<CarDto> getAllCars();
}
