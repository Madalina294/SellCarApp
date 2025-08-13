package com.carApp.SellCar_Spring.services.customer;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.carApp.SellCar_Spring.dto.CarDto;
import com.carApp.SellCar_Spring.dto.SearchCarDto;
import com.carApp.SellCar_Spring.entities.Car;
import com.carApp.SellCar_Spring.entities.User;
import com.carApp.SellCar_Spring.repositories.CarRepository;
import com.carApp.SellCar_Spring.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final UserRepository userRepository;

    private final CarRepository carRepository;

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
    @Transactional(readOnly = true)
    public List<CarDto> getAllCars() {
        return carRepository.findAll().stream().map(Car::getCarDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
    public List<CarDto> getCarsByCustomerId(Long customerId) {
        return carRepository.findAllByUserId(customerId).stream().map(Car::getCarDto).collect(Collectors.toList());
    }
}
