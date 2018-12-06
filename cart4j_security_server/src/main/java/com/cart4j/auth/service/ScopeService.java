package com.cart4j.auth.service;

import com.cart4j.auth.dto.ScopeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ScopeService {
    Page<ScopeDto> getScopes(Pageable pageable, String searchKey);
    ScopeDto addScope(ScopeDto scope);
    ScopeDto editScope(Long id, ScopeDto scope);
    void deleteScope(Long id);
}
