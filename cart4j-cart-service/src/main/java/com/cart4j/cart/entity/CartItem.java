package com.cart4j.cart.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CartItem {
    private Long id;

    @ManyToOne
    @JoinColumn(name="cart_id")
    private Cart cart;

    private Long productId;

    private Integer quantity;

    private String option;

    private Date addedAt;
}
