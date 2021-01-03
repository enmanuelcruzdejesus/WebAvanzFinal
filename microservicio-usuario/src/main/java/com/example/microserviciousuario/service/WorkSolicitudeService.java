package com.example.microserviciousuario.service;

import com.example.microserviciousuario.entity.WorkSolicitude;
import com.fasterxml.jackson.core.JsonProcessingException;
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
    
    public WorkSolicitude getById(String id) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        WorkSolicitude work = mapper.readValue(this.restService.get("/getWorkSolicitudes/"+id), new TypeReference<WorkSolicitude>(){});
        return work;
    }
    public List<WorkSolicitude> getAll() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<WorkSolicitude> workSolicitudes = mapper.readValue(this.restService.get("/getWorkSolicitudes"), new TypeReference<List<WorkSolicitude>>(){});
        return workSolicitudes;
    }

    public String postWorkSolicitude(WorkSolicitude work) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(work);
        String result = restService.post("/postWorkSolicitude",json);
        System.out.println(result);
        return result;
    }
}
