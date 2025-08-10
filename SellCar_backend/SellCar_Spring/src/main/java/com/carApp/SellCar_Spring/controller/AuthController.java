package com.carApp.SellCar_Spring.controller;

import com.carApp.SellCar_Spring.dto.AuthenticationRequest;
import com.carApp.SellCar_Spring.dto.AuthenticationResponse;
import com.carApp.SellCar_Spring.dto.SignUpRequest;
import com.carApp.SellCar_Spring.dto.UserDto;
import com.carApp.SellCar_Spring.entities.User;
import com.carApp.SellCar_Spring.repositories.UserRepository;
import com.carApp.SellCar_Spring.services.auth.AuthService;
import com.carApp.SellCar_Spring.services.jwt.UserService;
import com.carApp.SellCar_Spring.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@CrossOrigin("*")

public class AuthController {
    private final AuthService authService;

    private final UserService userService;

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final JWTUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignUpRequest signUpRequest) {
        if(authService.hasUserWithEmail(signUpRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("User already exists");
        }
        UserDto userDto = authService.signUp(signUpRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest authenticationRequest) {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
                    authenticationRequest.getPassword()));
        }catch (BadCredentialsException e){
            throw new BadCredentialsException("Invalid username or password");
        }
        final UserDetails userDetails = userService.userDetailsService().loadUserByUsername(authenticationRequest.getEmail());
        Optional<User> optionalUser = userRepository.findFirstByEmail(authenticationRequest.getEmail());
        final String token = jwtUtil.generateToken(userDetails);
        AuthenticationResponse response = new AuthenticationResponse();
        if(optionalUser.isPresent()) {
            response.setJwt(token);
            response.setUserId(optionalUser.get().getId());
            response.setUserRole(optionalUser.get().getUserRole());
        }
        return response;
    }
}
