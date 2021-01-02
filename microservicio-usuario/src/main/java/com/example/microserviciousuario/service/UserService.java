package com.example.microserviciousuario.service;

import com.example.microserviciousuario.dto.UserRegistrationDto;
import com.example.microserviciousuario.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;


public interface UserService extends UserDetailsService {
    
    List<User> getAll();
    User getCurrentUser();
    UserRegistrationDto getUserDto();
    User findByEmail(String email);
    User save(UserRegistrationDto entity);
}
