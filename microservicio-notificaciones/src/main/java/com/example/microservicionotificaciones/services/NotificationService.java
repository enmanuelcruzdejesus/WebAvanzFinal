package com.example.microservicionotificaciones.services;

import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
public class NotificationService {
    private JavaMailSender sender;
    


    @Autowired
    public NotificationService(JavaMailSender sender){
        this.sender = sender;
    }

    public void sendNotification(String toEmail, String fromEmail, String title, String description){

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(toEmail);
        mail.setFrom(fromEmail);
        mail.setSubject(title);
        mail.setText(description);
        sender.send(mail);

    }

    public void sendMailWithAttachment(String to,String from,  String subject, String body, String fileToAttach, String fileName)
    {
        MimeMessagePreparator preparator = new MimeMessagePreparator()
        {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {

                

                mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
                mimeMessage.setFrom(new InternetAddress(from));
                mimeMessage.setSubject(subject);
                mimeMessage.setText(body);

                FileSystemResource file = new FileSystemResource(new File(fileToAttach));
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
                helper.addAttachment(fileName, file);
            }

           
        };

        try {
            sender.send(preparator);
        }
        catch (MailException ex) {
            // simply log it and go on...
            System.err.println(ex.getMessage());
        }
    }


    public void sendSimpleMessage(String to,String from,  String subject, String body, String fileToAttach, String fileName) throws MessagingException, MessagingException {

        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

      
        helper.setSubject(subject);
        helper.setText(body);
        helper.setTo(to);
        helper.setFrom(from);
        

       // helper.addAttachment(fileName, new ClassPathResource(fileToAttach));
        helper.addAttachment(fileName,new File(fileToAttach));

        sender.send(message);

    }
}
