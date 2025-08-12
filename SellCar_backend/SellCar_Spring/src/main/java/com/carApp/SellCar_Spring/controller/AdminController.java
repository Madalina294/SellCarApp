package com.carApp.SellCar_Spring.controller;

import com.carApp.SellCar_Spring.dto.CarDto;
import com.carApp.SellCar_Spring.services.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
