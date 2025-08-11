package com.carApp.SellCar_Spring.entities;

import com.carApp.SellCar_Spring.dto.CarDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import java.util.Date;

@Entity
@Data
@Table(name = "cars")

public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private byte[] image;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    public CarDto getCarDto(){
        CarDto carDto = new CarDto();
        carDto.setId(id);
        carDto.setName(name);
        carDto.setBrand(brand);
        carDto.setType(type);
        carDto.setTransmission(transmission);
        carDto.setColor(color);
        carDto.setPrice(price);
        carDto.setYear(year);
        carDto.setSold(sold);
        carDto.setDescription(description);
        carDto.setReturnedImage(image);
        return carDto;
    }
}
