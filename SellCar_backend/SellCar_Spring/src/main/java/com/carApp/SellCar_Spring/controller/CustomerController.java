package com.carApp.SellCar_Spring.controller;

import com.carApp.SellCar_Spring.dto.CarDto;
import com.carApp.SellCar_Spring.services.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/car")
    public ResponseEntity<?> addCar(@ModelAttribute CarDto carDto) throws IOException {
        boolean succes = customerService.createCar(carDto);
        if(succes) return ResponseEntity.status(HttpStatus.CREATED).build();
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/cars")
    public ResponseEntity<List<CarDto>> getAllCars() {
        return ResponseEntity.ok(customerService.getAllCars());
    }
}
