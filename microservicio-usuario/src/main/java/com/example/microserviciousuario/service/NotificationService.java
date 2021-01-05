package com.example.microserviciousuario.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.core.instrument.util.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.stereotype.Service;
import com.example.microserviciousuario.entity.Mail;

import java.io.IOException;

@Service
public class NotificationService {

    public String sentMail(Mail mail) throws IOException {
        CloseableHttpClient httpclient = new DefaultHttpClient();
        try{

            HttpPost httppost = new HttpPost("http://localhost:8282/sentMail");

            //serializing object
            ObjectMapper objectMapper = new ObjectMapper();
            String mailJsonString = objectMapper.writeValueAsString(mail);
            System.out.println("JSON STRING  = "+mailJsonString);
            StringBody obj = new StringBody(mailJsonString, ContentType.MULTIPART_FORM_DATA);



            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addPart("mail", obj);
            HttpEntity entity = builder.build();
            httppost.setEntity(entity);

            HttpResponse response = httpclient.execute(httppost);
            HttpEntity responseEntity = response.getEntity();
            // print response
            //   System.out.println(IOUtils.toString(responseEntity.getContent()));


            return IOUtils.toString(responseEntity.getContent());
        }finally {
            httpclient.close();
        }


    }
}
