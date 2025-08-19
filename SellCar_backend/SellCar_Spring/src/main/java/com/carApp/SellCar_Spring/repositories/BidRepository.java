package com.carApp.SellCar_Spring.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.carApp.SellCar_Spring.entities.Bid;

public interface BidRepository extends JpaRepository<Bid, Long> {
    List<Bid> findAllByUserId(Long userId);

    List<Bid> findAllByCarId(Long carId);

    @Query("SELECT b FROM Bid b WHERE b.car.user.id = :ownerId")
    List<Bid> findAllBidsOnCarsByOwnerId(@Param("ownerId") Long ownerId);
}
