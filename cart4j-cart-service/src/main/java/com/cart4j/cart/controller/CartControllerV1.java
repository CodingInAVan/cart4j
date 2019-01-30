package com.cart4j.cart.controller;

import com.cart4j.cart.dto.v1.CartDtoV1;
import com.cart4j.cart.exception.NoUserCartException;
import com.cart4j.cart.service.v1.CartServiceV1;
import com.cart4j.common.dto.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/cart/v1")
public class CartControllerV1 {

    @GetMapping("/{username}/{session}")
    CartDtoV1 getCart(@PathVariable String username, @PathVariable String session) throws NoUserCartException {
        return cartServiceV1.getCart(username, session);
    }

    @PostMapping
    CartDtoV1 addToCart(Principal principal, CartDtoV1 cartDtoV1) {
        return cartServiceV1.addCart(principal.getName(), cartDtoV1);
    }

    private CartServiceV1 cartServiceV1;
    @Autowired
    public CartControllerV1(CartServiceV1 cartServiceV1) {
        this.cartServiceV1 = cartServiceV1;
    }

    @ExceptionHandler({NoUserCartException.class})
    ResponseEntity<ErrorResponse> handleNoUserCartException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.builder().errorCode(HttpStatus.PRECONDITION_FAILED.value()).message(e.getMessage()).build());
    }
}
