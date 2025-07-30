package com.carApp.SellCar_Spring.dto;

import lombok.Data;

@Data
public class AuthenticationRequest {
   private String email;
   private String password;
}

