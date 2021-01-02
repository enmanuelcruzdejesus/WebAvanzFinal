package com.example.microserviciocompras.service;

import com.example.microserviciocompras.entity.Order;
import com.example.microserviciocompras.repo.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    OrderRepo repo;


    public List<Order> getAll(){
        return  repo.findAll();
    }


    public Optional<Order> getById(Integer id){
        return repo.findById(id);
    }

    public Order save(Order p){

        return repo.save(p);
    }
}
