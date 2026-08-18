package com.backroad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class BackroadCbrApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackroadCbrApplication.class, args);
    }

    @GetMapping("/")
    public String hello() {
        return "Welcome to Backroad CBR - Spring Boot Application!";
    }

    @GetMapping("/api/health")
    public String health() {
        return "Application is running...";
    }
}
