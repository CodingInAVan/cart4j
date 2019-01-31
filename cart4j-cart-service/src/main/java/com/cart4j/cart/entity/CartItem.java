package com.cart4j.cart.entity;

import com.cart4j.model.cart.dto.v1.CartItemDtoV1;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="cart_item")
@NoArgsConstructor
@AllArgsConstructor
@Data
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

    public CartItemDtoV1 toDtoV1() {
        return CartItemDtoV1
                .builder()
                .addedAt(addedAt.getTime())
                .id(id)
                .option(option)
                .productId(productId)
                .quantity(quantity)
                .build();
    }
}
