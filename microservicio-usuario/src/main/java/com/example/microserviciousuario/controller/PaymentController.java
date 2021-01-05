package com.example.microserviciousuario.controller;

import com.example.microserviciousuario.entity.Payment;
import com.example.microserviciousuario.service.PaymentService;
import com.example.microserviciousuario.service.UserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;
import java.util.Random;

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


    /**
     * Simulando una parada del metodo por tiempo...
     * @return
     * @throws InterruptedException
     */
    @HystrixCommand(fallbackMethod = "salidaCircuitoAbierto")
    @RequestMapping("/breaker")
    public String simularParada()  {
//        LOGGER.info("Prueba simulación de parada.");
        Random random = new Random();
        int valorGenerado = random.nextInt(3000);
//        LOGGER.info("El valor generado: "+valorGenerado);
        if(valorGenerado > 1000){
            throw new RuntimeException("Error provocado...");
        }
        return "Mostrando información";
    }

    public String salidaCircuitoAbierto(){
//        LOGGER.info("Circuito Abierto...");
        return "Con la ejecución del metodo.... Abriendo el circuito...";
    }



}
