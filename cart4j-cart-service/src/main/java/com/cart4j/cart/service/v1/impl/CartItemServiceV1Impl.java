package com.cart4j.cart.service.v1.impl;

import com.cart4j.cart.entity.Cart;
import com.cart4j.cart.entity.CartItem;
import com.cart4j.cart.exception.InvalidCartException;
import com.cart4j.cart.exception.NoCartItemException;
import com.cart4j.cart.repository.CartItemRepository;
import com.cart4j.cart.repository.CartRepository;
import com.cart4j.cart.service.v1.CartItemServiceV1;
import com.cart4j.model.cart.dto.v1.CartItemDtoV1;
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
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;

    @Autowired
    public CartItemServiceV1Impl(CartItemRepository cartItemRepository, CartRepository cartRepository) {
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    public List<CartItemDtoV1> getCartItems(String username, Long cartId) {
        return cartItemRepository.findAllByUsernameAndCartId(username, cartId).stream().map(CartItem::toDtoV1).collect(Collectors.toList());
    }

    @Override
    public CartItemDtoV1 getCartItem(String username, Long cartItemId) throws NoCartItemException {
        CartItem cartItem = cartItemRepository.getOneByUsernameAndCartItemId(username, cartItemId);
        if(cartItem == null) {
            throw new NoCartItemException("There is no cart item with cartItemId = " + cartItemId);
        }
        return cartItem.toDtoV1();
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
                .productOption(cartItem.getProductOption())
                .productId(cartItem.getProductId())
                .quantity(cartItem.getQuantity())
                .build();

        return cartItemRepository.save(newCartItem).toDtoV1();
    }

    @Override
    public CartItemDtoV1 updateCartItem(String username, Long id, CartItemDtoV1 cartItemDtoV1) throws InvalidCartException {
        CartItem cartItem = cartItemRepository.getOne(id);
        if(!cartItem.getCart().getUsername().equals(username)) {
            throw new InvalidCartException("The cart is not owned by requester");
        }
        cartItem.setProductOption(cartItemDtoV1.getProductOption());
        cartItem.setQuantity(cartItemDtoV1.getQuantity());
        cartItem.setProductId(cartItemDtoV1.getProductId());
        return cartItemRepository.save(cartItem).toDtoV1();
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
