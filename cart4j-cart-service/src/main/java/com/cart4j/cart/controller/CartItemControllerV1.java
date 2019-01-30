package com.cart4j.cart.controller;


import com.cart4j.cart.dto.v1.CartItemDtoV1;
import com.cart4j.cart.exception.InvalidCartException;
import com.cart4j.cart.service.v1.CartItemServiceV1;
import com.cart4j.common.dto.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/cart-item/v1")
public class CartItemControllerV1 {
    private final CartItemServiceV1 cartItemServiceV1;

    @Autowired
    public CartItemControllerV1(CartItemServiceV1 cartItemServiceV1) {
        this.cartItemServiceV1 = cartItemServiceV1;
    }

    @GetMapping("/cart/{cartId}")
    List<CartItemDtoV1> getCartItems(Principal principal,  @PathVariable Long cartId) {
        return cartItemServiceV1.getCartItems(principal.getName(), cartId);
    }

    @PostMapping("/cart/{cartId}")
    CartItemDtoV1 addCartItem(Principal principal, @PathVariable Long cartId, @RequestBody CartItemDtoV1 cartItem) throws InvalidCartException {
        return cartItemServiceV1.addCartItem(principal.getName(), cartId, cartItem);
    }

    @PutMapping("/{cartItemId}")
    CartItemDtoV1 editCartItem(Principal principal, @PathVariable Long cartItemId, @RequestBody CartItemDtoV1 cartItem) throws InvalidCartException {
        return cartItemServiceV1.updateCartItem(principal.getName(), cartItemId, cartItem);
    }

    @DeleteMapping("/{cartItemId}")
    void deleteCartItems(Principal principal, @PathVariable Long cartItemId) throws InvalidCartException {
        cartItemServiceV1.deleteCartItem(principal.getName(), cartItemId);
    }

    @ExceptionHandler({InvalidCartException.class})
    ResponseEntity<ErrorResponse> handleInvalidCartException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.builder().errorCode(HttpStatus.PRECONDITION_FAILED.value()).message(e.getMessage()).build());
    }
}
