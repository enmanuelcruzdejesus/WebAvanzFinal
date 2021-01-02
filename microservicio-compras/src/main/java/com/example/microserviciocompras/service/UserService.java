package com.example.microserviciocompras.service;

import com.example.microserviciocompras.entity.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {


    @Autowired
    RestClientService restService;

    public List<User> getEmployees() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        
//        System.out.println(this.restService.get("/getEmployees"));
        List<User> users = mapper.readValue(this.restService.get("/getEmployees"), new TypeReference<List<User>>(){});
        return users;
    }
    
}
