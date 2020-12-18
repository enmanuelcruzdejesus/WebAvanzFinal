package com.example.microserviciousuario.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario/")
public class UsuarioController {

    @GetMapping("/")
    public String index(){
        return "Usuario controller ";
    }
}
