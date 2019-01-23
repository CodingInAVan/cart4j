package com.cart4j.cart.controller;


import com.cart4j.cart.dto.v1.CartItemDtoV1;
import com.cart4j.cart.service.v1.CartItemServiceV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/cart-item/v1")
public class CartItemControllerV1 {
    @Autowired
    private CartItemServiceV1 cartItemServiceV1;

    @GetMapping("/cart/{cartId}")
    List<CartItemDtoV1> getCartItems(Principal principal,  @PathVariable Long cartId) {
        return cartItemServiceV1.getCartItems(principal.getName(), cartId);
    }

    @PostMapping("/cart/{cartId}")
    CartItemDtoV1 addCartItem(Principal principal, @PathVariable Long cartId, @RequestBody CartItemDtoV1 cartItem) {
        return cartItemServiceV1.addCartItem(principal.getName(), cartId, cartItem);
    }

    @PutMapping("/{cartItemId}")
    CartItemDtoV1 editCartItem(Principal principal, @PathVariable Long cartItemId, @RequestBody CartItemDtoV1 cartItem) {
        return cartItemServiceV1.updateCartItem(principal.getName(), cartItemId, cartItem);
    }

    @DeleteMapping("/ids/{cartItemIds}")
    void deleteCartItems(Principal principal, @PathVariable String cartItemIds) {
        cartItemServiceV1.deleteCartItems(principal.getName(), Stream.of(cartItemIds.split(",")).map(Long::valueOf).collect(Collectors.toList()));
    }
}
