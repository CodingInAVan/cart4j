package com.cart4j.web.backend.controller;

import com.cart4j.common.dto.ErrorResponse;
import com.cart4j.common.dto.PageDto;
import com.cart4j.product.dto.v1.ProductDtoV1;
import com.cart4j.web.backend.exception.InvalidResponseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/backend/product")
public class ProductController {
    @Value("${product.service.url}")
    private String productServiceUrl;

    @GetMapping
    List<ProductDtoV1> getProductList() throws InvalidResponseException {
        final String getUrl = productServiceUrl + "/product";
        PageDto<ProductDtoV1> productData = WebClient
                .create()
                .get().uri(URI.create(getUrl))
                .retrieve().bodyToFlux(new ParameterizedTypeReference<PageDto<ProductDtoV1>>() {})
                .blockFirst();
        if(Objects.nonNull(productData)) {
            return productData.getList();
        }
        throw new InvalidResponseException("No response from " + getUrl);
    }

    @ExceptionHandler({InvalidResponseException.class})
    ResponseEntity<ErrorResponse> handleInvalidResponseException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.builder().errorCode(HttpStatus.PRECONDITION_FAILED.value()).message(e.getMessage()).build());
    }
}
