package com.cart4j.cart.service.v1;

import com.cart4j.cart.exception.NoUserCartException;
import com.cart4j.model.cart.dto.v1.CartDtoV1;

public interface CartServiceV1 {
    CartDtoV1 addCart(String username, CartDtoV1 cart);
    CartDtoV1 getCart(String username, String session) throws NoUserCartException;
    void removeCart(Long cartId);
}
