package com.cart4j.cart.service;

import com.cart4j.cart.Cart4jCartApp;
import com.cart4j.cart.service.v1.CartItemServiceV1;
import com.cart4j.cart.service.v1.impl.CartItemServiceV1Impl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Cart4jCartApp.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CartItemServiceTest {
    @TestConfiguration
    public static class Config {
        @Bean
        public CartItemServiceV1 cartItemServiceV1() {
            return new CartItemServiceV1Impl();
        }
    }

    @Autowired
    private CartItemServiceV1 cartItemServiceV1;

    @Test
    public void serviceTest() {

    }
}
