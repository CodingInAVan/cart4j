package com.cart4j.auth.controller;

import com.cart4j.auth.dto.ErrorResponse;
import com.cart4j.auth.dto.ResourceDto;
import com.cart4j.auth.dto.RoleDto;
import com.cart4j.auth.exception.ResourceAlreadyExistingException;
import com.cart4j.auth.exception.RoleAlreadyExistingException;
import com.cart4j.auth.service.RoleService;
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
@RequestMapping("/api/auth/role")
public class RoleController {
    @GetMapping
    PageDto<RoleDto> getRoles(Pageable pageable, @RequestParam(name="searchKey", required = false) String searchKey) {
        Page<RoleDto> roles = roleService.getRoles(pageable, searchKey);
        return PageDto.<RoleDto>builder().totalRecords(roles.getTotalElements()).totalPage(roles.getTotalPages())
                .offset(roles.getPageable().getOffset()).list(roles.getContent()).limit(roles.getSize()).build();
    }

    @PostMapping
    RoleDto addRole(Principal principal, @RequestBody RoleDto role) throws RoleAlreadyExistingException {
        RoleDto newRole = roleService.addRole(role);
        LOGGER.info("{} added a role[{}]", principal.getName(), newRole.getId());
        return newRole;
    }

    @PutMapping("/{id}")
    RoleDto editRole(Principal principal, @PathVariable Long id, @RequestBody RoleDto role) throws RoleAlreadyExistingException {
        RoleDto updated = roleService.editRole(id, role);
        LOGGER.info("{} modified the role[{}]", principal.getName(), updated.getId());
        return updated;
    }

    @DeleteMapping("/{id}")
    void deleteRole(Principal principal, @PathVariable Long id) {
        roleService.deleteRole(id);
        LOGGER.info("{} deleted the role[{}]", principal.getName(), id);
    }

    @ExceptionHandler({RoleAlreadyExistingException.class})
    ResponseEntity<ErrorResponse> roleException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.builder().errorCode(HttpStatus.PRECONDITION_FAILED.value()).message(e.getMessage()).build());
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;
}
