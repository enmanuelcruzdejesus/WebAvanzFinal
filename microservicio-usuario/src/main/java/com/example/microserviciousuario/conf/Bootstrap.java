package com.example.microserviciousuario.conf;

import com.example.microserviciousuario.entity.AppSetting;
import com.example.microserviciousuario.service.AppSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements ApplicationRunner {

    @Autowired
    AppSettingService service;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        service.save(new AppSetting(1, "CUENTA_NEGOCIO_PAYPAL", "cruzdejesusenmanuel@gmail.com"));

    }
}