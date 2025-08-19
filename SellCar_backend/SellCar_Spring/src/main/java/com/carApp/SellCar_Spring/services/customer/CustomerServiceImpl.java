package com.carApp.SellCar_Spring.services.customer;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.carApp.SellCar_Spring.dto.AnalyticsDto;
import com.carApp.SellCar_Spring.dto.BidDto;
import com.carApp.SellCar_Spring.dto.CarDto;
import com.carApp.SellCar_Spring.dto.SearchCarDto;
import com.carApp.SellCar_Spring.entities.Bid;
import com.carApp.SellCar_Spring.entities.Car;
import com.carApp.SellCar_Spring.entities.User;
import com.carApp.SellCar_Spring.enums.BidStatus;
import com.carApp.SellCar_Spring.repositories.BidRepository;
import com.carApp.SellCar_Spring.repositories.CarRepository;
import com.carApp.SellCar_Spring.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final UserRepository userRepository;

    private final CarRepository carRepository;
    private final BidRepository bidRepository;

    @Override
    public boolean createCar(CarDto carDto) throws IOException {
        Optional<User> optionalUser = userRepository.findById(carDto.getUserId());
        if (optionalUser.isPresent()) {
            Car car = new Car();
            car.setName(carDto.getName());
            car.setPrice(carDto.getPrice());
            car.setBrand(carDto.getBrand());
            car.setColor(carDto.getColor());
            car.setYear(carDto.getYear());
            car.setType(carDto.getType());
            car.setSold(false);
            car.setDescription(carDto.getDescription());
            car.setTransmission(carDto.getTransmission());
            car.setImage(carDto.getImage().getBytes());
            car.setUser(optionalUser.get());
            carRepository.save(car);
            return true;
        }
        return false;
    }

    @Override
    public List<CarDto> getAllCars() {
        return carRepository.findAll().stream().map(Car::getCarDto).collect(Collectors.toList());
    }

    @Override
    public CarDto getCarById(Long id) {
        Optional<Car> optionalCar = carRepository.findById(id);
        return optionalCar.map(Car::getCarDto).orElse(null);
    }

    @Override
    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }

    @Override
    public boolean updateCar(Long id, CarDto carDto) throws IOException {
        Optional<Car> optionalCar = carRepository.findById(id);
        if (optionalCar.isPresent()) {
            Car car = optionalCar.get();
            car.setName(carDto.getName());
            car.setPrice(carDto.getPrice());
            car.setBrand(carDto.getBrand());
            car.setColor(carDto.getColor());
            car.setYear(carDto.getYear());
            car.setType(carDto.getType());
            car.setDescription(carDto.getDescription());
            car.setTransmission(carDto.getTransmission());
            if(carDto.getImage() != null){
                car.setImage(carDto.getImage().getBytes());
            }
            carRepository.save(car);
            return true;
        }
        return false;
     }

    @Override
    public List<CarDto> searchCar(SearchCarDto searchCarDto) {
        Car car = new Car();
        car.setBrand(searchCarDto.getBrand());
        car.setType(searchCarDto.getType());
        car.setColor(searchCarDto.getColor());
        car.setTransmission(searchCarDto.getTransmission());
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("brand", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("type", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("color", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher("transmission", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
        Example<Car> example = Example.of(car, exampleMatcher);
        List<Car> cars = carRepository.findAll(example);
        return cars.stream().map(Car::getCarDto).collect(Collectors.toList());
    }

    @Override
    public List<CarDto> getCarsByCustomerId(Long customerId) {
        return carRepository.findAllByUserId(customerId).stream().map(Car::getCarDto).collect(Collectors.toList());
    }

    @Override
    public boolean bidACar(BidDto bidDto) {
        Optional<Car> optionalCar = carRepository.findById(bidDto.getCarId());
        Optional<User> optionalUser = userRepository.findById(bidDto.getUserId());
        if (optionalCar.isPresent() && optionalUser.isPresent()) {
            Bid bid = new Bid();
            bid.setCar(optionalCar.get());
            bid.setUser(optionalUser.get());
            bid.setPrice(bidDto.getPrice());
            bid.setBidStatus(BidStatus.PENDING);
            bidRepository.save(bid);
            return true;
        }
        return false;
    }

    @Override
    public List<BidDto> getBidsByUserId(Long userId) {
        return bidRepository.findAllByUserId(userId).stream().map(Bid::getBidDto).collect(Collectors.toList());
    }

    @Override
    public List<BidDto> getBidsByCarId(Long carId) {
        return bidRepository.findAllByCarId(carId).stream().map(Bid::getBidDto).collect(Collectors.toList());
    }

    @Override
    public List<BidDto> getBidsOnMyCars(Long ownerId) {
        return bidRepository.findAllBidsOnCarsByOwnerId(ownerId).stream().map(Bid::getBidDto).collect(Collectors.toList());
    }

    @Override
    public boolean changeBidStatus(Long bidId, String status, Long currentUserId) {
        try {
            Optional<Bid> bid = bidRepository.findById(bidId);
            if (bid.isPresent()) {
                Bid existingBid = bid.get();
                
                // Check if the current user is the owner of the car
                if (!Objects.equals(existingBid.getCar().getUser().getId(), currentUserId)) {
                    return false; // User is not authorized to change this bid status
                }
                
                // Check if car is already sold
                if(existingBid.getCar().isSold()) return false;
                
                // Update bid status
                if(Objects.equals(status, "Approved")) {
                    existingBid.setBidStatus(BidStatus.APPROVED);
                } else if(Objects.equals(status, "Rejected")) {
                    existingBid.setBidStatus(BidStatus.REJECTED);
                } else {
                    return false; // Invalid status
                }
                
                bidRepository.save(existingBid);
                return true;
            }
            return false;
        } catch (Exception e) {
            // Log the exception in a real application
            System.err.println("Error changing bid status: " + e.getMessage());
            return false;
        }
    }

    @Override
    public AnalyticsDto getAnalyticsDto(Long userId) {
        AnalyticsDto analyticsDto = new AnalyticsDto();
        analyticsDto.setTotalCars(carRepository.countByUserId(userId));
        analyticsDto.setSoldCars(carRepository.countByUserIdAndSoldTrue(userId));
        return analyticsDto;
    }
}
