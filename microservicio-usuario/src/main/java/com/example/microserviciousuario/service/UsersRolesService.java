package com.example.microserviciousuario.service;

import com.example.microserviciousuario.entity.UsersRoles;
import com.example.microserviciousuario.repo.UsersRolesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersRolesService {
    @Autowired
    UsersRolesRepo repo;

    public List<UsersRoles> getAll(){
        return  repo.findAll();
    }


    public UsersRoles save(UsersRoles p){

        return repo.save(p);
    }
}
