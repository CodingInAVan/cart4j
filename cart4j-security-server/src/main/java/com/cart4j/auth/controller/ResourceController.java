package com.cart4j.auth.controller;

import com.cart4j.common.dto.ErrorResponse;
import com.cart4j.auth.dto.ResourceDto;
import com.cart4j.auth.exception.ResourceAlreadyExistingException;
import com.cart4j.auth.service.ResourceService;
import com.cart4j.common.dto.PageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
@RequestMapping("/api/auth/resources")
public class ResourceController {
    @GetMapping
    PageDto<ResourceDto> getResources(Pageable pageable, @RequestParam(name="searchKey", required = false) String searchKey) {
        Page<ResourceDto> resourcesPage = resourceService.getResources(pageable, searchKey);
        return PageDto.<ResourceDto>builder().totalRecords(resourcesPage.getTotalElements()).totalPage(resourcesPage.getTotalPages())
                .offset(resourcesPage.getPageable().getOffset()).list(resourcesPage.getContent()).limit(resourcesPage.getSize()).build();
    }

    @PostMapping
    ResourceDto addResource(Principal principal, @RequestBody ResourceDto resource) throws ResourceAlreadyExistingException {
        ResourceDto newResource = resourceService.addResource(resource);
        LOGGER.info("{} added a resource[{}]", principal.getName(), newResource.getId());
        return newResource;
    }

    @PutMapping("/{id}")
    ResourceDto editResource(Principal principal, @PathVariable Long id, @RequestBody ResourceDto resource) throws ResourceAlreadyExistingException {
        ResourceDto updated = resourceService.editResource(id, resource);
        LOGGER.info("{} modified the resource[{}]", principal.getName(), updated.getId());
        return updated;
    }

    @DeleteMapping("/{id}")
    void deleteResource(Principal principal, @PathVariable Long id) {
        resourceService.deleteResource(id);
        LOGGER.info("{} deleted the resource[{}]", principal.getName(), id);
    }


    @ExceptionHandler({ResourceAlreadyExistingException.class})
    ResponseEntity<ErrorResponse> handleResourcException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.builder().errorCode(HttpStatus.PRECONDITION_FAILED.value()).message(e.getMessage()).build());
    }
    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceController.class);
    @Autowired
    private ResourceService resourceService;
}
