package com.example.microserviciocompras.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ComprasController {

    @GetMapping("/")
    public String index(){
        return "Hello from Microservicio Compras";
    }
}
