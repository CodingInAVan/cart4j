package com.cart4j.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class Cart4jAuthApp {
    public static void main(String[] args) {
        SpringApplication.run(Cart4jAuthApp.class, args);
    }
}

