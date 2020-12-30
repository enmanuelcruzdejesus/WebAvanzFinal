package com.example.microserviciocompras.controller;

import com.example.microserviciocompras.entity.Mail;
import com.example.microserviciocompras.entity.Payment;
import com.example.microserviciocompras.service.PaymentService;
import com.example.microserviciocompras.service.ReportService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.core.instrument.util.IOUtils;
import net.sf.jasperreports.engine.JRException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
@RequestMapping("/")
public class AppController {

    @Autowired
    ReportService reportService;

    @Autowired
    PaymentService paymentService;

    @PostMapping(path = "/processPayment",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> processPaymentPaypal(@RequestParam("payment") String payment) throws IOException, JRException {
            try{
                ObjectMapper mapper = new ObjectMapper();
                Payment paymentObj = mapper.readValue(payment, Payment.class);

                //saving payment
                Payment result = paymentService.save(paymentObj);
                System.out.println("PAYMENT ID = "+result.getId());


                //generating payment report
                this.reportService.exportReport(result.getId(),"pdf");


                //Sending payment report to customer
                Mail mail = new Mail();
                mail.setTo(result.getPayer_email());
                mail.setFrom("enmanuelcruzdejesus@gmail.com");
                mail.setSubject("Payment");
                mail.setBody("Payment of MultiMedia Services");

                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://localhost:8282/sentEmailWithAttachment");

                //serializing object
                ObjectMapper objectMapper = new ObjectMapper();
                String mailJsonString = objectMapper.writeValueAsString(mail);
                StringBody obj = new StringBody(mailJsonString, ContentType.MULTIPART_FORM_DATA);
                FileBody bin = new FileBody(new File("./payment.pdf"),ContentType.DEFAULT_BINARY);


                MultipartEntityBuilder builder = MultipartEntityBuilder.create();
                builder.addPart("mail", obj);
                builder.addPart("file", bin);
                HttpEntity entity = builder.build();
                httppost.setEntity(entity);

                HttpResponse response = httpclient.execute(httppost);
                HttpEntity responseEntity = response.getEntity();
                // print response
                System.out.println(IOUtils.toString(responseEntity.getContent()));


                //generating order

                //broadcasting order to employees



                return new ResponseEntity<String>("payment was completed succesfuly", HttpStatus.OK);

            }catch(Exception ex){
                return new ResponseEntity<String>("error processing payment "+ex.toString(),HttpStatus.INTERNAL_SERVER_ERROR);
            }

    }
}
