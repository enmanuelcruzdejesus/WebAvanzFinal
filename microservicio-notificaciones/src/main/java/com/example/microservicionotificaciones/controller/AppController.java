package com.example.microservicionotificaciones.controller;

import com.example.microservicionotificaciones.entity.ResponseMessage;
import com.example.microservicionotificaciones.services.FilesStorageService;
import com.example.microservicionotificaciones.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            storageService.save(file);

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }
    
    @GetMapping("/sentEmail/{email}")
    public String sentEmail(@PathVariable String email){
        
        service.sendNotification(email,"enmanuelcruzdejesus@gmail.com","Sending email test","THIS IS A TEST");
        return "email send";
        
    }

    @GetMapping("/sentEmailWithAttachment")
    public String sentEmailWithAttachment() throws MessagingException {
        service.sendSimpleMessage("enmanuelcruzdejesus@gmail.com","enmanuelcruzdejesus@gmail.com","Payment","Payment from Ecommerce","./uploads/payment.pdf","payment.pdf");
      //  service.sendMailWithAttachment("enmanuelcruzdejesus@gmail.com","enmanuelcruzdejesus@gmail.com","Payment","Payment from Ecommerce","/Users/enmanuelcruz/Desktop/webavanzfinal/microservicio-notificaciones/payment.pdf","payment.pdf");
        return "email sended";
    }
 
}
