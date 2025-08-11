package com.carApp.SellCar_Spring.repositories;

import com.carApp.SellCar_Spring.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
}
