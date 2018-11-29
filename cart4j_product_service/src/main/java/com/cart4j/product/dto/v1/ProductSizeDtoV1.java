package com.cart4j.product.dto.v1;

import com.cart4j.product.entity.ProductSize;
import com.cart4j.product.entity.ProductSizeUnit;
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

    public static ProductSizeDtoV1 from(ProductSize productSize) {
        return ProductSizeDtoV1.builder()
                .height(productSize.getHeight())
                .id(productSize.getId())
                .length(productSize.getLength())
                .unit(productSize.getUnit())
                .width(productSize.getWidth())
                .build();
    }
}
