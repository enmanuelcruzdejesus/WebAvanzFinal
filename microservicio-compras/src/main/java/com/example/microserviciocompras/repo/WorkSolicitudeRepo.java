package com.example.microserviciocompras.repo;

import com.example.microserviciocompras.entity.Payment;
import com.example.microserviciocompras.entity.WorkSolicitude;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkSolicitudeRepo extends JpaRepository<WorkSolicitude,Integer> {
}
