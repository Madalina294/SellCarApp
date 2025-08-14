package com.carApp.SellCar_Spring.dto;

import com.carApp.SellCar_Spring.enums.BidStatus;
import lombok.Data;

@Data
public class BidDto {
    private Long id;
    private Long price;
    private BidStatus bidStatus;
    private Long userId;
    private Long carId;
    private String userName;
    private String carName;
    private String carBrand;
    private String email;
    private String sellerName;
}
