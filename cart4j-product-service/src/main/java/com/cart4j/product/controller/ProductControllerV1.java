package com.cart4j.product.controller;

import com.cart4j.model.common.PageDto;
import com.cart4j.model.product.dto.v1.ProductDtoV1;
import com.cart4j.product.entity.Product;
import com.cart4j.product.entity.ProductDescription;
import com.cart4j.product.repository.ProductRepository;
import com.cart4j.product.service.v1.ProductServiceV1;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/v1/product")
public class ProductControllerV1 {

    @GetMapping()
    PageDto<ProductDtoV1> getList(Pageable pageable) {
        Page<ProductDtoV1> products = productServiceV1.getList(pageable);

        return PageDto.<ProductDtoV1>builder()
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .totalPage(products.getTotalPages())
                .list(products.getContent())
                .build();
    }

    @Autowired
    private ProductServiceV1 productServiceV1;
}
