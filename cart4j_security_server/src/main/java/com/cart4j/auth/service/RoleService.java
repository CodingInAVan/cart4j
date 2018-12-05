package com.cart4j.auth.service;

import com.cart4j.auth.dto.RoleDto;
import com.cart4j.auth.exception.RoleAlreadyExistingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleService {
    Page<RoleDto> getRoles(Pageable pageable, String searchKey);
    RoleDto addRole(RoleDto role) throws RoleAlreadyExistingException;
    RoleDto editRole(Long id, RoleDto role) throws RoleAlreadyExistingException;
    void deleteRole(Long id);
}
