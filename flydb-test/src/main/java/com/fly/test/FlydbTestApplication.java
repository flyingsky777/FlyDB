package com.fly.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.fly.test", "com.flydb"})
public class FlydbTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlydbTestApplication.class, args);
    }
}