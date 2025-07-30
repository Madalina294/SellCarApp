package com.carApp.SellCar_Spring.dto;

import lombok.Data;

@Data
public class SignUpRequest {
    String email;
    String password;
    String name;
}
