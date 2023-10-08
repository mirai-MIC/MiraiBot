package com.catbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import top.mrxiaom.qsign.QSignService;

import java.io.File;


@EnableAspectJAutoProxy
@SpringBootApplication
public class MiraiBotApplication {

    public static void main(String[] args) {
        QSignService.Factory.init(new File("txlib/8.9.68"));
        QSignService.Factory.loadProtocols(null);
        QSignService.Factory.register();
        SpringApplication.run(MiraiBotApplication.class, args);
    }
}
