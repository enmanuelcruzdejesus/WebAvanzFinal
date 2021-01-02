package com.example.microserviciocompras.repo;

import com.example.microserviciocompras.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepo  extends JpaRepository<Payment,Integer> {

    @Query("select u from Payment u where u.payer_email = ?1")
    List<Payment> findByPayerEmail(String payer_email);


}