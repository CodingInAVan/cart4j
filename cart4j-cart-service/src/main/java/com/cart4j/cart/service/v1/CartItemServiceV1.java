package com.cart4j.cart.service.v1;

import com.cart4j.cart.dto.v1.CartItemDtoV1;
import com.cart4j.cart.exception.InvalidCartException;
import com.cart4j.cart.exception.NoCartItemException;

import java.util.List;

public interface CartItemServiceV1 {
    List<CartItemDtoV1> getCartItems(String username, Long cartId);
    CartItemDtoV1 getCartItem(String username, Long cartItemId) throws NoCartItemException;
    CartItemDtoV1 addCartItem(String username, Long cartId, CartItemDtoV1 cartItem) throws InvalidCartException;
    CartItemDtoV1 updateCartItem(String username, Long id, CartItemDtoV1 cartItemDtoV1) throws InvalidCartException;
    void deleteCartItem(String username, Long cartItemId) throws InvalidCartException;
}
