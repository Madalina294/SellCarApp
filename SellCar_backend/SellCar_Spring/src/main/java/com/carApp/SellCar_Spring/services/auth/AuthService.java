package com.carApp.SellCar_Spring.services.auth;

import com.carApp.SellCar_Spring.dto.SignUpRequest;
import com.carApp.SellCar_Spring.dto.UserDto;

public interface AuthService {
    public boolean hasUserWithEmail(String email);
    public UserDto signUp(SignUpRequest signUpRequest);
}
