package com.cart4j.product.entity;

import lombok.*;

import javax.persistence.*;

@Table(name="c4_product_description")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
public class ProductDescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private String metaTitle;

    private String metaDescription;

    private String metaKeyword;
}
