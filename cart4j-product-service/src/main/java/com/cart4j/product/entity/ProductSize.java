package com.cart4j.product.entity;

import lombok.*;

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
}
