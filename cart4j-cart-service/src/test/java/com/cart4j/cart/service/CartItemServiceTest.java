package com.cart4j.cart.service;

import com.cart4j.cart.Cart4jCartApp;
import com.cart4j.cart.entity.Cart;
import com.cart4j.cart.entity.CartItem;
import com.cart4j.cart.exception.InvalidCartException;
import com.cart4j.cart.exception.NoCartItemException;
import com.cart4j.cart.repository.CartItemRepository;
import com.cart4j.cart.repository.CartRepository;
import com.cart4j.cart.service.v1.CartItemServiceV1;
import com.cart4j.cart.service.v1.impl.CartItemServiceV1Impl;
import com.cart4j.model.cart.dto.v1.CartItemDtoV1;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Cart4jCartApp.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CartItemServiceTest {
    @TestConfiguration
    public static class Config {
        @Autowired
        private CartItemRepository cartItemRepository;
        @Autowired
        private CartRepository cartRepository;

        @Bean
        public CartItemServiceV1 cartItemServiceV1() {
            return new CartItemServiceV1Impl(cartItemRepository, cartRepository);
        }
    }

    @Autowired
    private CartItemServiceV1 cartItemServiceV1;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartRepository cartRepository;

    @Before
    public void setup() {
        Cart cart = Cart.builder()
                .accountId(1L)
                .addedAt(new Date())
                .session("session-1")
                .username("test1")
                .build();
        cartRepository.save(cart);
        CartItem cartItem = CartItem.builder()
                .cart(cart)
                .addedAt(new Date())
                .productOption("color 1")
                .productId(1L)
                .quantity(10)
                .build();
        CartItem cartItem2 = CartItem.builder()
                .cart(cart)
                .addedAt(new Date())
                .productOption("color 2")
                .productId(2L)
                .quantity(4)
                .build();
        cartItemRepository.save(cartItem);
        cartItemRepository.save(cartItem2);
    }

    @Test
    public void get_test() {
        List<CartItemDtoV1> cartItems = cartItemServiceV1.getCartItems("test1", 1L);
        assertThat(cartItems.size()).isEqualTo(2);
    }
    @Test
    public void add_delete_test() throws InvalidCartException {
        CartItemDtoV1 cartItem = CartItemDtoV1.builder()
                .addedAt(new Date().getTime())
                .productOption("color 3")
                .productId(3L)
                .quantity(1)
                .build();
        CartItemDtoV1 newCartItem = cartItemServiceV1.addCartItem("test1", 1L, cartItem);
        List<CartItemDtoV1> cartItems = cartItemServiceV1.getCartItems("test1", 1L);
        assertThat(cartItems.size()).isEqualTo(3);

        cartItemServiceV1.deleteCartItem("test1", newCartItem.getId());
        cartItems = cartItemServiceV1.getCartItems("test1", 1L);
        assertThat(cartItems.size()).isEqualTo(2);
    }
    @Test
    public void update_test() throws NoCartItemException, InvalidCartException {
        CartItemDtoV1 cartItem = cartItemServiceV1.getCartItem("test1", 1L);
        CartItemDtoV1 modifiedItem = CartItemDtoV1.builder()
                .productOption("color A")
                .quantity(2)
                .productId(2L)
                .build();
        CartItemDtoV1 updatedItem = cartItemServiceV1.updateCartItem("test1", cartItem.getId(), modifiedItem);
        assertThat(updatedItem.getProductOption()).isEqualTo("color A");
        assertThat(updatedItem.getQuantity()).isEqualTo(2);
        assertThat(updatedItem.getProductId()).isEqualTo(2L);
    }
}
