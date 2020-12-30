package com.example.microserviciousuario.controller;

import com.example.microserviciousuario.dto.UserRegistrationDto;
import com.example.microserviciousuario.entity.Mail;
import com.example.microserviciousuario.entity.User;
import com.example.microserviciousuario.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.core.instrument.util.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;


@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

    @Autowired
    private UserService userService;





    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }



    @GetMapping
    public String showRegistrationForm(Model model) {
        return "registration";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") @Valid UserRegistrationDto userDto,
                                      BindingResult result) throws IOException {

        User existing = userService.findByEmail(userDto.getEmail());
        if (existing != null){
            result.rejectValue("email", null, "There is already an account registered with that email");
        }

        if (result.hasErrors()){
            return "registration";
        }
        //saving user
        
        userService.save(userDto);
        
        //sending email
        Mail mail = new Mail();
        mail.setTo(userDto.getEmail());
        mail.setFrom("enmanuelcruzdejesus@gmail.com");
        mail.setSubject("MutiMedia Services Confirmation Email");
        mail.setBody("http://localhost:8080/microservicio-usuario");


        HttpClient httpclient = new DefaultHttpClient();
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
        System.out.println(IOUtils.toString(responseEntity.getContent()));



        return "redirect:/registration?success";
    }



}
