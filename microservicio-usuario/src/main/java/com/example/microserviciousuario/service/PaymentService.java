package com.example.microserviciousuario.service;

import com.example.microserviciousuario.entity.Payment;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.niws.client.http.RestClient;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@Service
public class PaymentService {

    @Autowired
    RestClientService restService;


    public List<Payment> getPayments(String email) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<Payment> payments = mapper.readValue(this.restService.get("/getPayments/"+email), new TypeReference<List<Payment>>(){});

        System.out.println(this.restService.get("/getPayments/"+email));
        return payments;
    }

    public String processPayment(Payment payment) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(payment);
        String result = restService.post("/processPayment",json);
        System.out.println(result);
        return result;
    }
    
    public Integer getPaymentSeq(){
     
        String result = restService.get("/getPaymentSeq");
        System.out.println("PAYMENT ID = "+result);
        return Integer.parseInt(result);
    }




}
