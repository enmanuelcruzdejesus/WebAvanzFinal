package com.example.microservicionotificaciones.controller;

import com.example.microservicionotificaciones.entity.Mail;
import com.example.microservicionotificaciones.entity.ResponseMessage;
import com.example.microservicionotificaciones.services.FilesStorageService;
import com.example.microservicionotificaciones.services.NotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/")
public class AppController {
    @Autowired
    NotificationService service;

    @Autowired
    FilesStorageService storageService;

    @PostMapping(value = "/sentEmailWithAttachment", consumes = MediaType.MULTIPART_FORM_DATA_VALUE )
    public ResponseEntity<ResponseMessage> sentEmailWithAttachment(@RequestParam("mail") String mail, @RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            ObjectMapper mapper = new ObjectMapper();
            Mail mailObj = mapper.readValue(mail, Mail.class);
            
            storageService.deleteAll();
            storageService.init();
            
            storageService.save(file);
            
            service.sendSimpleMessage(mailObj.getTo(),mailObj.getFrom(),mailObj.getSubject(),mailObj.getBody(),"./uploads/payment.pdf","payment.pdf");

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @PostMapping("/sendEmail")
    public String postEmail(@RequestBody Mail mail){
        service.sendNotification(mail.getTo(),mail.getFrom(),mail.getSubject(),mail.getBody());

        return "email send";
    }
    
    @GetMapping("/sentEmail/{email}")
    public String sentEmail(@PathVariable String email){
        
        service.sendNotification(email,"enmanuelcruzdejesus@gmail.com","Sending email test","THIS IS A TEST");
        return "email send";
        
    }


 
}
