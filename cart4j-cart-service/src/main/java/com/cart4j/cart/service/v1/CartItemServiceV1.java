package com.cart4j.cart.service.v1;

import com.cart4j.cart.dto.v1.CartItemDtoV1;

import java.util.List;

public interface CartItemServiceV1 {
    List<CartItemDtoV1> getCartItems();
    CartItemDtoV1 addCartItem(Long cartId, CartItemDtoV1 cartItem);
    CartItemDtoV1 updateCartItem(Long id, CartItemDtoV1 cartItemDtoV1);
    void updateQuantity(Long id, Integer quantity);
}
