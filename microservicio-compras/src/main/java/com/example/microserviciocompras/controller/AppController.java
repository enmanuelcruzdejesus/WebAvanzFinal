package com.example.microserviciocompras.controller;

import com.example.microserviciocompras.entity.Mail;
import com.example.microserviciocompras.entity.Order;
import com.example.microserviciocompras.entity.Payment;
import com.example.microserviciocompras.entity.User;
import com.example.microserviciocompras.entity.WorkSolicitude;
import com.example.microserviciocompras.service.*;
import com.example.microserviciocompras.service.PaymentService;
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
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/")
public class AppController {

    @Autowired
    ReportService reportService;

    @Autowired
    PaymentService paymentService;

    @Autowired
    OrderService orderService;

    @Autowired
    WorkSolicitudeService workSolicitudeService;

    @Autowired
    UserService userService;

    @Autowired
    NotificationService notificationService;

    @PostMapping(path = "/processPayment")
    public ResponseEntity<String> processPaymentPaypal(@RequestBody Payment payment) throws IOException, JRException {
            try{
//                ObjectMapper mapper = new ObjectMapper();
//                Payment paymentObj = mapper.readValue(payment, Payment.class);

                //saving payment
                Payment result = paymentService.save(payment);
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
                WorkSolicitude o = new WorkSolicitude();
                o.setCustomer_email(result.getPayer_email());
                o.setEmployee_asig("");
                o.setOrder_date(new Date());
                o.setTotal(result.getPayment_gross());
                o.setStatus("In Progress");
                WorkSolicitude savedSolicitude  =  this.workSolicitudeService.save(o);


               //getting all employees
                List<User> employees = this.userService.getEmployees();

                //broadcasting order to employees
                for(int i =0; i < employees.size(); i++){

                    User u  = employees.get(i);
                    Mail m = new Mail();
                    m.setTo(u.getEmail());
                    m.setFrom("enmanuelcruzdejesus@gmail.com");
                    m.setSubject("A new ORDER has been generated");
                    m.setBody("Payment of MultiMedia Services");
                    String r = this.notificationService.sentMail(m);
                    System.out.println(r);

                }

                return new ResponseEntity<String>("payment was completed succesfuly", HttpStatus.OK);

            }catch(Exception ex){
                return new ResponseEntity<String>("error processing payment "+ex.toString(),HttpStatus.INTERNAL_SERVER_ERROR);
            }

    }


    @GetMapping(path = "/getEmployees")
    public ResponseEntity<List<User>> getEmployees() throws IOException {
        return new ResponseEntity<List<User>>(this.userService.getEmployees(),HttpStatus.OK);
    }

    @GetMapping(path ="/getPaymentSeq")
    public ResponseEntity<Integer> getPaymentSequence(){
        Integer i  = this.paymentService.getAll().size()+1;
        return new ResponseEntity<Integer>(i,HttpStatus.OK);
    }

    @GetMapping(path="/getPayments/{email}")
    public ResponseEntity<Object> getPayments(@PathVariable String email){
        List<Payment>result = this.paymentService.findByPayerEmail(email);
        if(result != null)
        return new ResponseEntity<Object>(result,HttpStatus.OK);


        return new ResponseEntity<Object>("there are not payments made from  "+email, HttpStatus.NO_CONTENT);
    }

    @GetMapping(path="/getWorkSolicitudes")
    public ResponseEntity<List<WorkSolicitude>> getWorkSolicitudes(){

        return new ResponseEntity<List<WorkSolicitude>>(this.workSolicitudeService.getAll(),HttpStatus.OK);

    }

    @GetMapping(path="/getWorkSolicitudes/{id}")
    public ResponseEntity<Object> getWorkSolicitudes(@PathVariable("id") String id){

        Integer i  = Integer.parseInt(id);
        WorkSolicitude w  = this.workSolicitudeService.getById(i).get();

        if(w == null)
            return new ResponseEntity<Object>("no work solicitude with "+id,HttpStatus.NO_CONTENT);

        return new ResponseEntity<Object>(w,HttpStatus.OK);

    }

    @PostMapping(path="/postWorkSolicitude")
    public ResponseEntity<Object> postWorkSolicitude(@RequestBody WorkSolicitude work){

        if(work==null)
            return new ResponseEntity<Object>("work is null",HttpStatus.INTERNAL_SERVER_ERROR);

        WorkSolicitude w  = this.workSolicitudeService.save(work);

        return new ResponseEntity<Object>(w,HttpStatus.OK);
    }


    @GetMapping(path="/getOrders")
    public ResponseEntity<List<Order>> getOrders(){

        return new ResponseEntity<List<Order>>(this.orderService.getAll(),HttpStatus.OK);

    }
}
