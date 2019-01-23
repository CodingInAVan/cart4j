package com.cart4j.cart.repository;

import com.cart4j.cart.entity.Cart;
import com.cart4j.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @Query("SELECT ci FROM CartItem ci JOIN ci.cart c WHERE c.username = ?1 and c.id = ?2")
    List<CartItem> findAllByUsernameAndCartId(String username, Long cartId);
}
