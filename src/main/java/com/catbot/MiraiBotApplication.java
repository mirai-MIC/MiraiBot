package com.catbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class MiraiBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(MiraiBotApplication.class, args);
    }


}
