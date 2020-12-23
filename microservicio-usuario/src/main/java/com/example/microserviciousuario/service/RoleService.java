package com.example.microserviciousuario.service;

import com.example.microserviciousuario.entity.Role;
import com.example.microserviciousuario.repo.RoleRepo;
import com.example.microserviciousuario.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    RoleRepo repo;

    public RoleService(){}


    public List<Role> getAll(){
        return  repo.findAll();
    }

    public Role findByName(String name){ return this.repo.findByName(name); }

    public Optional<Role> getById(Long id){
        return repo.findById(id);
    }

    public Role save(Role p){

        return repo.save(p);
    }


}
