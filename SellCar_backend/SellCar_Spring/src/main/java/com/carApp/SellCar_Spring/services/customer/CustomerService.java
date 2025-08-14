package com.carApp.SellCar_Spring.services.customer;

import com.carApp.SellCar_Spring.dto.BidDto;
import com.carApp.SellCar_Spring.dto.CarDto;
import com.carApp.SellCar_Spring.dto.SearchCarDto;

import java.io.IOException;
import java.util.List;

public interface CustomerService {
    boolean createCar(CarDto carDto) throws IOException;

    List<CarDto> getAllCars();

    CarDto getCarById(Long id);

    void deleteCar(Long id);

    boolean updateCar(Long id, CarDto carDto) throws IOException;

    List<CarDto> searchCar(SearchCarDto searchCarDto);

    List<CarDto> getCarsByCustomerId(Long customerId);

    boolean bidACar(BidDto bidDto);

    List<BidDto> getBidsByUserId(Long userId);

    List<BidDto> getBidsByCarId(Long carId);

    boolean changeBidStatus(Long bidId, String status);
}
