package com.cart4j.auth.controller;

import com.cart4j.auth.dto.ResourceDto;
import com.cart4j.auth.entity.Resource;
import com.cart4j.auth.service.ResourceService;
import com.cart4j.common.dto.PageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth/resource")
public class ResourceController {
    @GetMapping
    PageDto<ResourceDto> getResources(Pageable pageable, @RequestParam(name="searchKey", required = false) String searchKey) {
        Page<ResourceDto> resourcesPage = resourceService.getResources(pageable, searchKey);
        return PageDto.<ResourceDto>builder().totalRecords(resourcesPage.getTotalElements()).totalPage(resourcesPage.getTotalPages())
                .offset(resourcesPage.getPageable().getOffset()).list(resourcesPage.getContent()).limit(resourcesPage.getSize()).build();
    }

    @Autowired
    private ResourceService resourceService;
}
