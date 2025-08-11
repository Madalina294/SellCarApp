package com.carApp.SellCar_Spring.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data

public class CarDto {
    private Long id;

    private String name;

    private String brand;
    private String type;
    private String transmission;
    private String color;
    private Long price;
    private Date year;
    private boolean sold;
    private String description;
    private MultipartFile image;

    private byte[] returnedImage;

}
