package com.example.microserviciousuario.service;

import com.example.microserviciousuario.dto.UserRegistrationDto;
import com.example.microserviciousuario.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService extends UserDetailsService {

    User getCurrentUser();
    User findByEmail(String email);
    User save(UserRegistrationDto entity);
}
