package com.carApp.SellCar_Spring.services.customer;

import com.carApp.SellCar_Spring.dto.CarDto;
import com.carApp.SellCar_Spring.entities.Car;
import com.carApp.SellCar_Spring.entities.User;
import com.carApp.SellCar_Spring.repositories.CarRepository;
import com.carApp.SellCar_Spring.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
}
