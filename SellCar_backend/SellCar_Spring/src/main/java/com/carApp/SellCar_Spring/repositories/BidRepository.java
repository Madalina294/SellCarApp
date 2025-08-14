package com.carApp.SellCar_Spring.repositories;

import com.carApp.SellCar_Spring.entities.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BidRepository extends JpaRepository<Bid, Long> {
    List<Bid> findAllByUserId(Long userId);

    List<Bid> findAllByCarId(Long carId);
}
