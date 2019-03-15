package com.cart4j.cart.service.v1.impl;

import com.cart4j.cart.entity.Cart;
import com.cart4j.cart.entity.CartItem;
import com.cart4j.cart.exception.NoUserCartException;
import com.cart4j.cart.repository.CartItemRepository;
import com.cart4j.cart.repository.CartRepository;
import com.cart4j.cart.service.v1.CartServiceV1;
import com.cart4j.model.cart.dto.v1.CartDtoV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CartServiceV1Impl implements CartServiceV1 {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Autowired
    public CartServiceV1Impl(CartRepository cartRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public CartDtoV1 addCart(String username, CartDtoV1 cart) {
        Cart newCart = Cart.builder()
                .accountId(cart.getAccountId())
                .addedAt(new Date())
                .session(cart.getSession())
                .username(username)
                .build();

        return cartRepository.save(newCart).toDtoV1();
    }

    @Override
    public CartDtoV1 getCart(String username, String session) throws NoUserCartException {

        List<Cart> cart = cartRepository.findAllByUsername(username, session, PageRequest.of(0, 1));
        if(CollectionUtils.isEmpty(cart)) {
            throw new NoUserCartException("Cart does not exists.");
        }
        return cart.get(0).toDtoV1();
    }

    @Override
    public void removeCart(Long cartId) {
        Cart cart = cartRepository.getOne(cartId);
        // Delete all cart items

        List<CartItem> cartItems = cart.getCartItems();

        if(!CollectionUtils.isEmpty(cartItems)) {
            cartItems.forEach(cartItemRepository::delete);
        }
        cartRepository.deleteById(cartId);
    }


}
