package com.carApp.SellCar_Spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carApp.SellCar_Spring.entities.Car;
import com.carApp.SellCar_Spring.entities.User;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findAllByUserId(Long customerId);

    List<Car> user(User user);

    Long countByUserId(Long userId);

    Long countByUserIdAndSoldTrue(Long userId);

    Long countBySoldTrue();
    
    List<Car> findAllBySoldFalse(); // Find only unsold cars
}
