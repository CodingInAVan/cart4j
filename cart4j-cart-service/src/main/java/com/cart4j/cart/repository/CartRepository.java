package com.cart4j.cart.repository;

import com.cart4j.cart.entity.Cart;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query("SELECT c FROM Cart c WHERE c.username = ?1 ORDER BY added_at DESC")
    List<Cart> findAllByUsername(String username, Pageable pageable);
}
