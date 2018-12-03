package com.cart4j.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PageDto<T> {
    private List<T> list;
    private Long offset;
    private Integer totalPage;
    private Integer limit;
    private Long totalRecords;
}
