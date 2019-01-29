package com.cart4j.cart.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="cart_item")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="cart_id")
    private Cart cart;

    private Long productId;

    private Integer quantity;

    private String option;

    private Date addedAt;
}
