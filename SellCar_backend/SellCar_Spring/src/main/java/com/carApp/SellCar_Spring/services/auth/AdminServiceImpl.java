package com.carApp.SellCar_Spring.services.auth;

import com.carApp.SellCar_Spring.entities.User;
import com.carApp.SellCar_Spring.enums.UserRole;
import com.carApp.SellCar_Spring.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor


public class AdminServiceImpl  implements AuthService{

    private final UserRepository userRepository;

    @PostConstruct
    public void createAdminAccount(){
        Optional<User> optionalAdmin = userRepository.findByUserRole(UserRole.ADMIN);
        if(optionalAdmin.isEmpty()){
            User admin = new User();
            admin.setName("admin");
            admin.setEmail("admin@gmail.com");
            admin.setUserRole(UserRole.ADMIN);
            admin.setPassword(new BCryptPasswordEncoder().encode("admin"));
            userRepository.save(admin);
            System.out.println("Admin created successfully!");
        }
        else{
            System.out.println("Admin already exists!");
        }
    }

    @Override
    public boolean hasUserWithEmail(String email){
        return userRepository.findFirstByEmail(email).isPresent();
    }
}
