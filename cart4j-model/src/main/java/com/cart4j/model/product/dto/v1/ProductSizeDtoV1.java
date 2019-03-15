package com.cart4j.model.product.dto.v1;

import com.cart4j.model.product.ProductSizeUnit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSizeDtoV1 {
    private Long id;

    private BigDecimal length;

    private BigDecimal width;

    private BigDecimal height;

    private ProductSizeUnit unit;
}
