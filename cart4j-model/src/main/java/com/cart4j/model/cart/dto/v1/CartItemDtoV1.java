package com.cart4j.model.cart.dto.v1;

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
    private String productOption;
    private Long addedAt;
    private Long productId;
}
