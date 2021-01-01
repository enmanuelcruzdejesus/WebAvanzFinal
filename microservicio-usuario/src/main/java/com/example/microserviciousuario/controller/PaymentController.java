package com.example.microserviciousuario.controller;

import com.example.microserviciousuario.entity.Payment;
import com.example.microserviciousuario.service.PaymentService;
import com.example.microserviciousuario.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/payment")
public class PaymentController {


    @Autowired
    UserService userService;

    @Autowired
    PaymentService paymentService;

    @GetMapping("/getAll")
    public String orderList(Model model) throws IOException {

        List<Payment> payments = paymentService.getPayments(this.userService.getCurrentUser().getEmail());
        model.addAttribute("payments", payments);
        return "payments";
    }
    
}
