package com.cart4j.product.entity;

import com.cart4j.model.product.WeightUnit;
import com.cart4j.model.product.dto.v1.ProductWeightDtoV1;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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

    public ProductWeightDtoV1 toDtoV1() {
        return ProductWeightDtoV1.builder()
                .id(id)
                .unit(unit)
                .weight(weight)
                .build();
    }
}
