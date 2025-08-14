package com.carApp.SellCar_Spring.services.admin;

import com.carApp.SellCar_Spring.dto.BidDto;
import com.carApp.SellCar_Spring.dto.CarDto;
import com.carApp.SellCar_Spring.dto.SearchCarDto;

import java.util.List;

public interface AdminService {
    List<CarDto> getAllCars();
    CarDto getCarById(Long id);
    void deleteCar(Long id);

    List<CarDto> searchCar(SearchCarDto searchCarDto);

    List<BidDto> getAllBids();

    boolean changeBidStatus(Long bidId, String status);
}
