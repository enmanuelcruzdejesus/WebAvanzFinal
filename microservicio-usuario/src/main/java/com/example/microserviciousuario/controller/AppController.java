package com.example.microserviciousuario.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;


@Controller("/")
public class AppController {


    @Value("${server.port}")
    private int puerto;

    @GetMapping("/")
    public String index(){


        return "index";

    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }



    @GetMapping("/user")
    public String userIndex() {
        return "user/index";
    }
}
