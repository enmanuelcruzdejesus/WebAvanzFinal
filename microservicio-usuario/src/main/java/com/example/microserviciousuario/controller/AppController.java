package com.example.microserviciousuario.controller;

import com.example.microserviciousuario.entity.AppSetting;
import com.example.microserviciousuario.entity.Payment;
import com.example.microserviciousuario.entity.Product;
import com.example.microserviciousuario.service.AppSettingService;
import com.example.microserviciousuario.service.PaymentService;
import com.example.microserviciousuario.service.ProductService;
import com.example.microserviciousuario.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.discovery.converters.Auto;
import io.micrometer.core.instrument.util.IOUtils;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;


@Controller("/")
public class AppController {

    List<Product> cartItems =new ArrayList<>();


    @Autowired
    UserService userService;

    @Autowired
    AppSettingService appSettingService;

    @Autowired
    PaymentService paymentService;

    @Autowired
    ProductService productService;

    @Value("${server.port}")
    private int puerto;

    @GetMapping("/")
    public String index(Model model){

        List<Product> products = this.productService.getAll();
        model.addAttribute("products",products);
        return "index";

    }


    @GetMapping(path = "/addToCart/{id}")
    public ResponseEntity<String> addToCart(@PathVariable("id") String id){
        int productId  = Integer.parseInt(id);
        List<Product> products = this.productService.getAll();
        for(int i =0; i < products.size(); i++){
            Product item = products.get(i);
            if(item.getPid() == productId){
                this.cartItems.add(item);
            }
        }
        System.out.println("PRODUCT = "+productId+" added in cart");
        return new ResponseEntity<String>("product = "+productId+" added!", HttpStatus.OK);
    }

    @GetMapping(path = "/paymentForm")
    public String formularioPago(Model model){
        AppSetting app ;
        model.addAttribute("cuentaNegocio",appSettingService.getById(1).get().getValue());
        model.addAttribute("cartItems",this.cartItems);
        return "paymentForm";
    }

    @PostMapping(path = "/processPaymentPaypal")
    public RedirectView processPaymentPaypal(Model model, @RequestParam Map<String,String> params) throws IOException {
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

        //processing payment with paymentservice
        String result = this.paymentService.processPayment(payment);
        System.out.println("Payment response = "+result);


        return new RedirectView("http://localhost:8080/microservicio-usuario/payments");
    }

    @GetMapping("/payments")
    public String orderList(Model model) throws IOException {

        List<Payment> payments = paymentService.getPayments(this.userService.getCurrentUser().getEmail());
        model.addAttribute("payments", payments);
        return "payments";
    }

    @GetMapping("/shoppingPage")
    public String shoppingPage(){
        return "shoppingPage";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }



    @GetMapping("/user")
    public String userIndex() {
        return "user/index";
    }
}
