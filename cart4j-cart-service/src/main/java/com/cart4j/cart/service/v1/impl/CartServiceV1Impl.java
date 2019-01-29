package com.cart4j.cart.service.v1.impl;

import com.cart4j.cart.dto.v1.CartDtoV1;
import com.cart4j.cart.entity.Cart;
import com.cart4j.cart.entity.CartItem;
import com.cart4j.cart.exception.NoUserCartException;
import com.cart4j.cart.repository.CartItemRepository;
import com.cart4j.cart.repository.CartRepository;
import com.cart4j.cart.service.v1.CartServiceV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class CartServiceV1Impl implements CartServiceV1 {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public CartDtoV1 addCart(String username, CartDtoV1 cart) {
        Cart newCart = Cart.builder()
                .accountId(cart.getAccountId())
                .addedAt(new Date())
                .session(cart.getSession())
                .username(username)
                .build();

        return CartDtoV1.from(cartRepository.save(newCart));
    }

    @Override
    public CartDtoV1 getCart(String username) throws NoUserCartException {

        List<Cart> cart = cartRepository.findAllByUsername(username, PageRequest.of(0, 1));
        if(CollectionUtils.isEmpty(cart)) {
            throw new NoUserCartException("Cart does not exists.");
        }
        return CartDtoV1.from(cart.get(0));
    }

    @Override
    public void removeCart(Long cartId) {
        Cart cart = cartRepository.getOne(cartId);
        // Delete all cart items

        List<CartItem> cartItems = cart.getCartItems();

        if(!CollectionUtils.isEmpty(cartItems)) {
            cartItems.stream().forEach(cartItemRepository::delete);
        }
        cartRepository.deleteById(cartId);
    }


}
