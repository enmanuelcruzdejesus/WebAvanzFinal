package com.example.microserviciocompras.service;

import com.example.microserviciocompras.entity.Order;
import com.example.microserviciocompras.entity.Payment;
import com.example.microserviciocompras.repo.OrderRepo;
import com.example.microserviciocompras.repo.PaymentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {
    @Autowired
    PaymentRepo repo;


    public List<Payment> getAll(){
        return  repo.findAll();
    }




    public Optional<Payment> getById(Integer id){
        return repo.findById(id);
    }

    public Payment save(Payment p){

        return repo.save(p);
    }

}
