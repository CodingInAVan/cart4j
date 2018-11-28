package com.cart4j.product.controller;

import com.cart4j.product.dto.v1.PageDtoV1;
import com.cart4j.product.dto.v1.ProductDtoV1;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/product")
public class ProductControllerV1 {
    @GetMapping()
    PageDtoV1<ProductDtoV1> getList() {

        return PageDtoV1.<ProductDtoV1>builder().list(null).build();
    }
}
