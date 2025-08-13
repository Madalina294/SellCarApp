package com.carApp.SellCar_Spring.repositories;

import com.carApp.SellCar_Spring.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findAllByUserId(Long customerId);
}
