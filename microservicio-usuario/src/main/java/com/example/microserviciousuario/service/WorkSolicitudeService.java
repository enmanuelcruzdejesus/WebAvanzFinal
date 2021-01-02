package com.example.microserviciousuario.service;

import com.example.microserviciousuario.entity.WorkSolicitude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class WorkSolicitudeService {
    @Autowired
    RestClientService restService;
    
    public List<WorkSolicitude> getAll() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<WorkSolicitude> payments = mapper.readValue(this.restService.get("/getWorkSolicitudes"), new TypeReference<List<WorkSolicitude>>(){});
        return payments;
    }
}
