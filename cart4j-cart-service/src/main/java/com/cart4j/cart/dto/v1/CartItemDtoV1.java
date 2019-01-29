package com.cart4j.cart.dto.v1;

import com.cart4j.cart.entity.CartItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDtoV1 {
    private Long id;
    private Integer quantity;
    private String option;
    private Long addedAt;
    private Long productId;

    public static CartItemDtoV1 from(CartItem cartItem) {
        return CartItemDtoV1.builder()
                .addedAt(cartItem.getAddedAt().getTime())
                .id(cartItem.getId())
                .option(cartItem.getOption())
                .productId(cartItem.getProductId())
                .quantity(cartItem.getQuantity())
                .build();
    }
}
