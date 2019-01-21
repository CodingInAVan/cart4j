package com.cart4j.cart.controller;

import com.cart4j.cart.dto.v1.CartDtoV1;
import com.cart4j.cart.service.v1.CartServiceV1;
import com.cart4j.common.dto.PageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.security.Principal;

@RestController
@RequestMapping("/api/cart/v1")
public class CartControllerV1 {

    @GetMapping("/{username}")
    CartDtoV1 getCart(Pageable pageable) {
        return null;
    }

    @PostMapping
    CartDtoV1 addToCart(Principal principal, CartDtoV1 cartDtoV1) {
        return cartServiceV1.addCart(principal.getName(), cartDtoV1);
    }

    @Autowired
    private CartServiceV1 cartServiceV1;
}
