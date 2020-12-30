package com.example.microserviciocompras.controller;

import com.example.microserviciocompras.entity.AppSetting;
import com.example.microserviciocompras.entity.Mail;
import com.example.microserviciocompras.entity.Payment;
import com.example.microserviciocompras.service.AppSettingService;
import com.example.microserviciocompras.service.PaymentService;
import com.example.microserviciocompras.service.ReportService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.core.instrument.util.IOUtils;
import net.sf.jasperreports.engine.JRException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.*;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sun.nio.cs.UTF_32;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class ComprasController {


    @Autowired
    AppSettingService appSettingService;
    @Autowired
    ReportService reportService;

    @Autowired
    PaymentService paymentService;

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping(path = "/paymentForm")
    public String formularioPago(Model model){
        AppSetting app ;
        model.addAttribute("cuentaNegocio",appSettingService.getById(1).get().getValue());
        return "paymentForm";
    }

    @PostMapping(path = "/processPaymentPaypal")
    public String processPaymentPaypal(Model model, @RequestParam Map<String,String> params) throws IOException, JRException {
        Payment payment = new Payment();
        payment.setInvoice_id(params.get("invoice"));
        payment.setTransaction_id(params.get("txn_id"));
        payment.setItem_name(params.get("item_name"));
        payment.setStatus(params.get("payment_status"));

        payment.setPayment_gross(new BigDecimal(params.get("payment_gross")));
        payment.setHandling_amount(new BigDecimal(params.get("handling_amount")));
        payment.setPayment_fee(new BigDecimal(params.get("payment_fee")));
        payment.setShipping(new BigDecimal(params.get("shipping")));

       // order.setCompradorId(params.get("txn_id"));

        payment.setPayer_email(params.get("payer_email"));
        payment.setOrder_date(new Date());
        payment.setBusiness(params.get("business"));

        payment.setAddress_city(params.get("address_city"));
        payment.setAddress_zip(params.get("address_zip"));
        payment.setAddress_state(params.get("address_state"));
        payment.setAddress_name(params.get("address_name"));


        //saving payment
        Payment result = paymentService.save(payment);
        System.out.println("PAYMENT ID = "+result.getId());

        //generating payment report
        this.reportService.exportReport(result.getId(),"pdf");

        //sending report

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





        return "redirect:/orderList";
    }

    @GetMapping("/orderList")
    public String orderList(Model model){
        List<Payment> payments = paymentService.getAll();
        model.addAttribute("payments", payments);
        return "orderList";
    }

    @GetMapping("/getReport/{invoice}")
    public String getReport(@PathVariable String invoice) throws FileNotFoundException, JRException {

         this.reportService.exportReport(1,"pdf");
         return "reportHtml";

    }


}
