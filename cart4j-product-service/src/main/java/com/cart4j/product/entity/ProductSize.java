package com.cart4j.product.entity;

import com.cart4j.model.product.ProductSizeUnit;
import com.cart4j.model.product.dto.v1.ProductSizeDtoV1;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="c4_product_size")
@Getter
@Setter
@AllArgsConstructor
@Builder
public class ProductSize {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal length;

    private BigDecimal width;

    private BigDecimal height;

    @Enumerated(EnumType.STRING)
    private ProductSizeUnit unit;

    public ProductSizeDtoV1 toDtoV1() {
        return ProductSizeDtoV1
                .builder()
                .height(height)
                .width(width)
                .length(length)
                .id(id)
                .unit(unit)
                .build();
    }
}
