package com.cart4j.cart.service.v1;

import com.cart4j.cart.dto.v1.CartDtoV1;
import com.cart4j.cart.dto.v1.CartItemDtoV1;

public interface CartServiceV1 {
    CartDtoV1 getCart(String username);
    CartDtoV1 removeCart(Long cartId);
}
