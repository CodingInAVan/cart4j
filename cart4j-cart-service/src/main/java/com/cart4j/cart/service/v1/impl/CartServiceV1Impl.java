package com.cart4j.cart.service.v1.impl;

import com.cart4j.cart.dto.v1.CartDtoV1;
import com.cart4j.cart.service.v1.CartServiceV1;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CartServiceV1Impl implements CartServiceV1 {
    @Override
    public CartDtoV1 getCart(String username) {
        return null;
    }

    @Override
    public CartDtoV1 removeCart(Long cartId) {
        return null;
    }
}
