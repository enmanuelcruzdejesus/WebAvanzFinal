package com.example.microserviciocompras.service;

import com.example.microserviciocompras.entity.AppSetting;
import com.example.microserviciocompras.entity.Order;
import com.example.microserviciocompras.entity.Payment;
import com.netflix.discovery.converters.Auto;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.exolab.castor.mapping.xml.Sql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

@Service
public class ReportService {
//    @Autowired
//    OrderService service;
    
    @Autowired
    AppSettingService appSettingService;

    @Autowired
    PaymentService paymentService;




    public ReportService(){

    }

    public String exportReport(String invoiceId, String report) throws FileNotFoundException, JRException {
     //   List<Order> orders = this.service.getAll();
    //    List<AppSetting> setting = this.appSettingService.getAll();
        
//       Payment p = this.paymentService.getById(1);
       Integer i  =  Integer.parseInt(invoiceId);
       Payment payment =  this.paymentService.getById(i).get();

       // load and compile
        File file = ResourceUtils.getFile("classpath:payment.jrxml");

        JasperReport jasReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(Arrays.asList(payment));
//        Map<String,Object> map = new HashMap<>();
//        map.put("payment",invoiceId);

        JasperPrint jasPrint = JasperFillManager.fillReport(jasReport,null,dataSource);
        if(report.equalsIgnoreCase("html")){
            JasperExportManager.exportReportToHtmlFile(jasPrint,"./payment.html");

        }

        if(report.equalsIgnoreCase("pdf")){
            JasperExportManager.exportReportToPdfFile(jasPrint,"./payment.pdf");
        }

        return "report generated";

    }
}
