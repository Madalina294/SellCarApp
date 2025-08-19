package com.carApp.SellCar_Spring.services.customer;

import java.io.IOException;
import java.util.List;

import com.carApp.SellCar_Spring.dto.AnalyticsDto;
import com.carApp.SellCar_Spring.dto.BidDto;
import com.carApp.SellCar_Spring.dto.CarDto;
import com.carApp.SellCar_Spring.dto.SearchCarDto;

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

    List<BidDto> getBidsOnMyCars(Long ownerId);

    boolean changeBidStatus(Long bidId, String status, Long currentUserId);

    AnalyticsDto getAnalyticsDto(Long userId);
}
