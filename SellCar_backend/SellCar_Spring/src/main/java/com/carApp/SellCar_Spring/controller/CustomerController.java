package com.carApp.SellCar_Spring.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carApp.SellCar_Spring.dto.BidDto;
import com.carApp.SellCar_Spring.dto.CarDto;
import com.carApp.SellCar_Spring.dto.SearchCarDto;
import com.carApp.SellCar_Spring.entities.User;
import com.carApp.SellCar_Spring.repositories.UserRepository;
import com.carApp.SellCar_Spring.services.customer.CustomerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CustomerController {

    private final CustomerService customerService;
    private final UserRepository userRepository;

    @PostMapping("/car")
    public ResponseEntity<?> addCar(@ModelAttribute CarDto carDto) throws IOException {
        boolean succes = customerService.createCar(carDto);
        if (succes) return ResponseEntity.status(HttpStatus.CREATED).build();
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/cars")
    public ResponseEntity<List<CarDto>> getAllCars() {
        return ResponseEntity.ok(customerService.getAllCars());
    }

    @GetMapping("/car/{id}")
    public ResponseEntity<CarDto> getCarById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getCarById(id));
    }

    @DeleteMapping("/car/{id}")
    public ResponseEntity<Void> deleteCarById(@PathVariable Long id) {
        customerService.deleteCar(id);
        return ResponseEntity.ok(null);
    }

    @PutMapping("/car/{id}")
    public ResponseEntity<?> updateCarById(@PathVariable Long id, @ModelAttribute CarDto carDto) throws IOException {
        boolean succes = customerService.updateCar(id, carDto);
        if (succes) return ResponseEntity.status(HttpStatus.OK).build();
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }

    @PostMapping("/car/search")
    public ResponseEntity<List<CarDto>> searchCar(@RequestBody SearchCarDto searchCarDto) throws IOException {
        return ResponseEntity.ok(customerService.searchCar(searchCarDto));
    }

    @GetMapping("/my-cars/{id}")
    public ResponseEntity<List<CarDto>> getCarsByCustomerId(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getCarsByCustomerId(id));
    }

    @PostMapping("/car/bid")
    public ResponseEntity<?> bidACar(@RequestBody BidDto bidDto) {
        boolean succes = customerService.bidACar(bidDto);
        if (succes) return ResponseEntity.status(HttpStatus.CREATED).build();
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/car/bids/{id}")
    public ResponseEntity<List<BidDto>> getBidsByUserId(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getBidsByUserId(id));

    }

    @GetMapping("/car/{carId}/bids")
    public ResponseEntity<List<BidDto>> getBidsByCarId(@PathVariable Long carId) {
        return ResponseEntity.ok(customerService.getBidsByCarId(carId));

    }

    @GetMapping("/bids-on-my-cars/{ownerId}")
    public ResponseEntity<List<BidDto>> getBidsOnMyCars(@PathVariable Long ownerId) {
        return ResponseEntity.ok(customerService.getBidsOnMyCars(ownerId));
    }

    @PutMapping("/car/bid/{bidId}/{status}")
    public ResponseEntity<?> updateBidStatus(@PathVariable Long bidId, @PathVariable String status) {
        try {
            // Get current user from security context
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = authentication.getName();
            Optional<User> currentUser = userRepository.findFirstByEmail(userEmail);
            
            if (currentUser.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            
            boolean success = customerService.changeBidStatus(bidId, status, currentUser.get().getId());
            if(success) return ResponseEntity.ok().build();
            else return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/car/analytics/{userId}")
    public ResponseEntity<?> getAnalyticsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(customerService.getAnalyticsDto(userId));

    }
}
