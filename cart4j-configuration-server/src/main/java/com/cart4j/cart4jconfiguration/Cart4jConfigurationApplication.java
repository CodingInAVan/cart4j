package com.cart4j.cart4jconfiguration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class Cart4jConfigurationApplication {

    public static void main(String[] args) {
        SpringApplication.run(Cart4jConfigurationApplication.class, args);
    }

}

