package com.cart4j.auth.controller;

import com.cart4j.auth.dto.ErrorResponse;
import com.cart4j.auth.dto.RoleDto;
import com.cart4j.auth.dto.ScopeDto;
import com.cart4j.auth.exception.RoleAlreadyExistingException;
import com.cart4j.auth.service.RoleService;
import com.cart4j.auth.service.ScopeService;
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
@RequestMapping("/api/auth/scope")
public class ScopeController {
    @GetMapping
    PageDto<ScopeDto> getRoles(Pageable pageable, @RequestParam(name="searchKey", required = false) String searchKey) {
        Page<ScopeDto> scopes = scopeService.getScopes(pageable, searchKey);
        return PageDto.<ScopeDto>builder().totalRecords(scopes.getTotalElements()).totalPage(scopes.getTotalPages())
                .offset(scopes.getPageable().getOffset()).list(scopes.getContent()).limit(scopes.getSize()).build();
    }

    @PostMapping
    ScopeDto addScope(Principal principal, @RequestBody ScopeDto scope) {
        ScopeDto newScope = scopeService.addScope(scope);
        LOGGER.info("{} added a scope[{}]", principal.getName(), newScope.getId());
        return newScope;
    }

    @PutMapping("/{id}")
    ScopeDto editScope(Principal principal, @PathVariable Long id, @RequestBody ScopeDto scope) {
        ScopeDto updated = scopeService.editScope(id, scope);
        LOGGER.info("{} modified the scope[{}]", principal.getName(), updated.getId());
        return updated;
    }

    @DeleteMapping("/{id}")
    void deleteScope(Principal principal, @PathVariable Long id) {
        scopeService.deleteScope(id);
        LOGGER.info("{} deleted the scope[{}]", principal.getName(), id);
    }

    @ExceptionHandler({RoleAlreadyExistingException.class})
    ResponseEntity<ErrorResponse> roleException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.builder().errorCode(HttpStatus.PRECONDITION_FAILED.value()).message(e.getMessage()).build());
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private ScopeService scopeService;
}
