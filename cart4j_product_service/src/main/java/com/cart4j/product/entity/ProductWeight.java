package com.cart4j.product.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Table(name="c4_product_weight")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
public class ProductWeight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal weight;

    @Enumerated(EnumType.STRING)
    private WeightUnit unit;
}
