package com.example.microserviciousuario.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping("/")
public class AppController {


    @Value("${server.port}")
    private int puerto;

    @GetMapping("/")
    public String index(){


        return "hello from microservicio usuario en el puerto = "+puerto;

    }
}
