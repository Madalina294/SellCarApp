package com.carApp.SellCar_Spring.repositories;

import com.carApp.SellCar_Spring.entities.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BidRepository extends JpaRepository<Bid, Long> {
}
