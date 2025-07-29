package com.carApp.SellCar_Spring.dto;

import com.carApp.SellCar_Spring.enums.UserRole;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private UserRole role;
}
