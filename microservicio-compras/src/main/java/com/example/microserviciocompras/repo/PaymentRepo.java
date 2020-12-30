package com.example.microserviciocompras.repo;

import com.example.microserviciocompras.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepo  extends JpaRepository<Payment,Integer> {

}