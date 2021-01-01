package com.example.microserviciousuario.service;

import com.example.microserviciousuario.entity.AppSetting;
import com.example.microserviciousuario.repo.AppSettingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppSettingService {
    @Autowired
    AppSettingRepo repo;

    public List<AppSetting> getAll(){
        return  repo.findAll();
    }


    public Optional<AppSetting> getById(Integer id){
        return repo.findById(id);
    }

    public AppSetting save(AppSetting p){

        return repo.save(p);
    }
}