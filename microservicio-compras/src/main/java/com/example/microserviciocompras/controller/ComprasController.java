package com.example.microserviciocompras.controller;

import com.example.microserviciocompras.entity.AppSetting;
import com.example.microserviciocompras.entity.Order;
import com.example.microserviciocompras.entity.Payment;
import com.example.microserviciocompras.service.AppSettingService;
import com.example.microserviciocompras.service.OrderService;
import com.example.microserviciocompras.service.PaymentService;
import com.example.microserviciocompras.service.ReportService;
import com.netflix.discovery.converters.Auto;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class ComprasController {

    @Autowired
    OrderService orderService;
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
        model.addAttribute("cuentaNegocio",appSettingService.getById(1).get().getValue());
        return "paymentForm";
    }

    @PostMapping(path = "/processPaymentPaypal")
    public String processPaymentPaypal(Model model, @RequestParam Map<String,String> params){
        Payment order = new Payment();
        order.setInvoice_id(params.get("invoice"));
        order.setTransaction_id(params.get("txn_id"));
        order.setItem_name(params.get("item_name"));
        order.setStatus(params.get("payment_status"));

        order.setPayment_gross(new BigDecimal(params.get("payment_gross")));
        order.setHandling_amount(new BigDecimal(params.get("handling_amount")));
        order.setPayment_fee(new BigDecimal(params.get("payment_fee")));
        order.setShipping(new BigDecimal(params.get("shipping")));

       // order.setCompradorId(params.get("txn_id"));

        order.setPayer_email(params.get("payer_email"));
        order.setOrder_date(new Date());
        order.setBusiness(params.get("business"));

        order.setAddress_city(params.get("address_city"));
        order.setAddress_zip(params.get("address_zip"));
        order.setAddress_state(params.get("address_state"));
        order.setAddress_name(params.get("address_name"));

        paymentService.save(order);

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

         this.reportService.exportReport(invoice,"pdf");
         return "reportHtml";

    }


}
