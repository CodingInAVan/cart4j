package com.cart4j.cart.controller;

import com.cart4j.cart.dto.v1.CartDtoV1;
import com.cart4j.common.dto.PageDto;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;

@RestController
@RequestMapping("/api/cart/v1")
public class CartControllerV1 {
    @GetMapping("/{username}")
    CartDtoV1 getCart(Pageable pageable) {
        return null;
    }

}
