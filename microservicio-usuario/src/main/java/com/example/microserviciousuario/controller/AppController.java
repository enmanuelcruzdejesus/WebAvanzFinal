package com.example.microserviciousuario.controller;

import com.example.microserviciousuario.entity.Role;
import com.example.microserviciousuario.entity.User;
import com.example.microserviciousuario.entity.AppSetting;
import com.example.microserviciousuario.entity.Payment;
import com.example.microserviciousuario.entity.Product;
import com.example.microserviciousuario.entity.WorkByStatus;
import com.example.microserviciousuario.entity.WorkSolicitude;
import com.example.microserviciousuario.service.AppSettingService;
import com.example.microserviciousuario.service.PaymentService;
import com.example.microserviciousuario.service.ProductService;
import com.example.microserviciousuario.service.UserService;
import com.example.microserviciousuario.service.WorkSolicitudeService;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.logging.Logger;


@Controller("/")
public class AppController {

    Double total = new Double(0);

    List<Product> cartItems =new ArrayList<>();

    List<String> workStatus = new ArrayList<>();

    Payment currentPayment = new Payment();

    @Autowired
    UserService userService;

    @Autowired
    AppSettingService appSettingService;

    @Autowired
    PaymentService paymentService;

    @Autowired
    ProductService productService;

    @Autowired
    WorkSolicitudeService workSolicitudeService;

    @Value("${server.port}")
    private int puerto;

    public AppController(){
        this.workStatus.add("In Progress");
        this.workStatus.add("Completed");
    }

    @GetMapping("/")
    public String index(Model model){
        String role = this.userService.getCurrentUser().getRole();
        System.out.println("ROLE USER === "+role);
        model.addAttribute("role",role);
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
                this.total += item.getPrice();
            }
        }
        System.out.println("PRODUCT = "+productId+" added in cart");
        return new ResponseEntity<String>("product = "+productId+" added!", HttpStatus.OK);
    }

    @GetMapping(path = "/paymentForm")
    public String formularioPago(Model model){
        String role = this.userService.getCurrentUser().getRole();
        System.out.println("ROLE USER === "+role);
        model.addAttribute("role",role);

        String item_name = "";
        for(int i =0; i < this.cartItems.size(); i++){
            Product item = this.cartItems.get(i);
            if(i > 0)
              item_name = item_name+","+item.getProductName();
            else
                item_name = item.getProductName();
        }

        Integer invoice_id = this.paymentService.getPaymentSeq();
        model.addAttribute("invoice_id","FA"+invoice_id.toString());
        model.addAttribute("item_name",item_name);
        model.addAttribute("cuentaNegocio",appSettingService.getById(1).get().getValue());
        model.addAttribute("total",this.total);
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

        //cleaning shopping cart
        this.clean();

        //return "redirect:/payments";
        return new RedirectView("http://localhost:8080/microservicio-usuario/payments");
    }

    @PostMapping(path="/postWorkSolicitude")
    public String postWorkSolicitude(@ModelAttribute("work") @Valid WorkSolicitude work, BindingResult result ) throws JsonProcessingException {

       String response =  this.workSolicitudeService.postWorkSolicitude(work);
       return "redirect:/workSolicitudes";

    }



    @GetMapping("/payments")
    public String orderList(Model model) throws IOException {
        String role = this.userService.getCurrentUser().getRole();
        System.out.println("ROLE USER === "+role);
        model.addAttribute("role",role);

        List<Payment> payments = paymentService.getPayments(this.userService.getCurrentUser().getEmail());
        model.addAttribute("payments", payments);
        return "payments";
    }

    @GetMapping("/shoppingPage")
    public String shoppingPage(){
        return "shoppingPage";
    }

    @GetMapping("/workSolicitudes")
    public String getWorks(Model model) throws IOException {
        String role = this.userService.getCurrentUser().getRole();
        System.out.println("ROLE USER === "+role);
        model.addAttribute("role",role);

        List<WorkSolicitude> works =   this.workSolicitudeService.getAll();
        model.addAttribute("works",works);
        return "workSolicitudes";
    }

    @GetMapping("/workSolicitudesDetail/{id}")
    public String getWorkById(@PathVariable("id") String id,Model model) throws JsonProcessingException {


        WorkSolicitude work = workSolicitudeService.getById(id);
        List<User> employees = getEmps();
        model.addAttribute("work",work);
        model.addAttribute("employees",employees);
        model.addAttribute("status",workStatus);

        return "workSolicitudesDetail";

    }
    @GetMapping("/getWorkByStatus")
    public ResponseEntity<List<WorkByStatus>> getWorkByStatus() throws JsonProcessingException {
        List<WorkByStatus> work = this.workSolicitudeService.getWorkByStatus();
        return new ResponseEntity<List<WorkByStatus>>(work,HttpStatus.OK);

    }


    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/logout")
    public String logout(){
          this.clean();
        return "logout";
    }

    @GetMapping("/user")
    public String userIndex() {
        return "user/index";
    }

    @GetMapping("/getEmployees")
    public ResponseEntity<List<User>> getEmployees() {
        List<User> employees = getEmps();
        return new ResponseEntity<List<User>>(employees,HttpStatus.OK);
    }

    @GetMapping(path="/charts")
    public String getCharts(){
        return "charts";
    }


    private List<User> getEmps(){
        List<User> result = new ArrayList<User>();
        List<User> users = this.userService.getAll();

        for(int i  =0; i < users.size(); i++){
            User u  = users.get(i);
            Collection<Role> roles = u.getRoles();
            for(Role role : roles){
                if(role.getId() == 4)
                    result.add(u);
            }


        }
        return result;

    }


    void clean(){
        this.cartItems = null;
        this.cartItems = new ArrayList<>();
        this.total  = new Double(0);

    }
}
