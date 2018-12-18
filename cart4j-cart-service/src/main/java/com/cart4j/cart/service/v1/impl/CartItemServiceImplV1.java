package com.cart4j.cart.service.v1.impl;

import com.cart4j.cart.dto.v1.CartItemDtoV1;
import com.cart4j.cart.service.v1.CartItemServiceV1;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class CartItemServiceImplV1 implements CartItemServiceV1 {
    @Override
    public List<CartItemDtoV1> getCartItems() {
        return null;
    }

    @Override
    public CartItemDtoV1 addCartItem(Long cartId, CartItemDtoV1 cartItem) {
        return null;
    }

    @Override
    public CartItemDtoV1 updateCartItem(Long id, CartItemDtoV1 cartItemDtoV1) {
        return null;
    }

    @Override
    public void updateQuantity(Long id, Integer quantity) {

    }
}
