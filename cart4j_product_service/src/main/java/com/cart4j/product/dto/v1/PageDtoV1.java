package com.cart4j.product.dto.v1;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PageDtoV1<T> {
    private List<T> list;
    private Integer offset;
    private Integer totalPage;
    private Integer limit;
}
