package com.carApp.SellCar_Spring.dto;

import lombok.Data;

@Data
public class AnalyticsDto {

    private Long totalCars;        // Total cars in entire application
    private Long soldCars;         // Total sold cars in entire application
    private Long myCars;           // My posted cars
    private Long mySoldCars;       // My sold cars
}
