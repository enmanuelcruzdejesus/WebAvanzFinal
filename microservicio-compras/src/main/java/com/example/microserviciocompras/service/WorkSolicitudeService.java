package com.example.microserviciocompras.service;


import com.example.microserviciocompras.entity.Payment;
import com.example.microserviciocompras.entity.WorkByStatus;
import com.example.microserviciocompras.entity.WorkSolicitude;
import com.example.microserviciocompras.repo.PaymentRepo;
import com.example.microserviciocompras.repo.WorkSolicitudeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkSolicitudeService {

    @Autowired
    WorkSolicitudeRepo repo;


    public List<WorkSolicitude> getAll(){
        return  repo.findAll();
    }
    
    public List<WorkByStatus> findStatusCount(){
        return this.repo.findStatusCount();
    }

    public Optional<WorkSolicitude> getById(Integer id){
        return repo.findById(id);
    }

    public WorkSolicitude save(WorkSolicitude p){

        return repo.save(p);
    }
}
