package com.carApp.SellCar_Spring.controller;

import com.carApp.SellCar_Spring.dto.BidDto;
import com.carApp.SellCar_Spring.dto.CarDto;
import com.carApp.SellCar_Spring.dto.SearchCarDto;
import com.carApp.SellCar_Spring.services.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin("*")

public class AdminController {

    private final AdminService adminService;

    @GetMapping("/cars")
    public ResponseEntity<List<CarDto>> getAllCars() {
        return ResponseEntity.ok(adminService.getAllCars());
    }

    @GetMapping("/car/{id}")
    public ResponseEntity<CarDto> getCarById(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.getCarById(id));
    }

    @DeleteMapping("/car/{id}")
    public ResponseEntity<Void> deleteCarById(@PathVariable Long id) {
        adminService.deleteCar(id);
        return ResponseEntity.ok(null);
    }

    @PostMapping("/car/search")
    public ResponseEntity<List<CarDto>> searchCar(@RequestBody SearchCarDto searchCarDto) throws IOException {
        return ResponseEntity.ok(adminService.searchCar(searchCarDto));
    }

    @GetMapping("/car/bids")
    public ResponseEntity<List<BidDto>> getAllBids(){
        return ResponseEntity.ok(adminService.getAllBids());
    }
}
