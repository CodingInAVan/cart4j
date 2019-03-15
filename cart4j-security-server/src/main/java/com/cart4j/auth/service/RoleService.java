package com.cart4j.auth.service;

import com.cart4j.auth.exception.RoleAlreadyExistingException;
import com.cart4j.model.security.dto.v1.RoleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleService {
    Page<RoleDto> getRoles(Pageable pageable, String searchKey);
    RoleDto addRole(RoleDto role) throws RoleAlreadyExistingException;
    RoleDto getRole(Long id);
    RoleDto editRole(Long id, RoleDto role) throws RoleAlreadyExistingException;
    void deleteRole(Long id);
}
