package com.cart4j.auth.service;

import com.cart4j.auth.dto.ResourceDto;
import com.cart4j.auth.entity.Resource;
import com.cart4j.auth.exception.ResourceAlreadyExistingException;
import com.cart4j.auth.exception.RoleAlreadyExistingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ResourceService {
    Page<ResourceDto> getResources(Pageable pageable, String searchKey);
    ResourceDto addResource(ResourceDto resource) throws ResourceAlreadyExistingException;
    ResourceDto editResource(Long resourceId, ResourceDto resource) throws ResourceAlreadyExistingException;
    void deleteResource(Long resourceId);
}
