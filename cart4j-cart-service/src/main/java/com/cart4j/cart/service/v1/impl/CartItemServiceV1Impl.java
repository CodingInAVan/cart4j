package com.cart4j.cart.service.v1.impl;

import com.cart4j.cart.dto.v1.CartItemDtoV1;
import com.cart4j.cart.entity.Cart;
import com.cart4j.cart.entity.CartItem;
import com.cart4j.cart.exception.InvalidCartException;
import com.cart4j.cart.repository.CartItemRepository;
import com.cart4j.cart.repository.CartRepository;
import com.cart4j.cart.service.v1.CartItemServiceV1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Transactional
@Service
public class CartItemServiceV1Impl implements CartItemServiceV1 {
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private CartRepository cartRepository;

    @Override
    public List<CartItemDtoV1> getCartItems(String username, Long cartId) {
        return cartItemRepository.findAllByUsernameAndCartId(username, cartId).stream().map(CartItemDtoV1::from).collect(Collectors.toList());
    }

    @Override
    public CartItemDtoV1 addCartItem(String username, Long cartId, CartItemDtoV1 cartItem) throws InvalidCartException {
        Cart cart = cartRepository.getOne(cartId);
        if(Objects.isNull(cart)) {
            throw new InvalidCartException("No cart existing");
        }
        if(!cart.getUsername().equals(username)) {
            throw new InvalidCartException("Invalid cart");
        }
        CartItem newCartItem = CartItem
                .builder()
                .cart(cart)
                .addedAt(new Date())
                .option(cartItem.getOption())
                .productId(cartItem.getProductId())
                .quantity(cartItem.getQuantity())
                .build();

        return CartItemDtoV1.from(cartItemRepository.save(newCartItem));
    }

    @Override
    public CartItemDtoV1 updateCartItem(String username, Long id, CartItemDtoV1 cartItemDtoV1) throws InvalidCartException {
        CartItem cartItem = cartItemRepository.getOne(id);
        if(!cartItem.getCart().getUsername().equals(username)) {
            throw new InvalidCartException("The cart is not owned by requester");
        }
        cartItem.setOption(cartItemDtoV1.getOption());
        cartItem.setQuantity(cartItemDtoV1.getQuantity());
        cartItem.setProductId(cartItemDtoV1.getProductId());
        return CartItemDtoV1.from(cartItemRepository.save(cartItem));
    }

    @Override
    public void deleteCartItem(String username, Long id) throws InvalidCartException {
        CartItem cartItem = cartItemRepository.getOne(id);
        if(!cartItem.getCart().getUsername().equals(username)) {
            throw new InvalidCartException("The cart is not owned by requester");
        }
        cartItemRepository.delete(cartItem);
    }
}
