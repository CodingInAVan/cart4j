package com.cart4j.auth.service.impl;

import com.cart4j.auth.dto.RoleDto;
import com.cart4j.auth.dto.ScopeDto;
import com.cart4j.auth.entity.Scope;
import com.cart4j.auth.repository.ScopeRepository;
import com.cart4j.auth.service.ScopeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;

@Transactional
@Service
public class ScopeServiceImpl implements ScopeService {
    @Override
    public Page<ScopeDto> getScopes(Pageable pageable, String searchKey) {
        Specification<Scope> spec = null;

        if(!StringUtils.isEmpty(searchKey)) {
            spec = ScopeSpec.search(searchKey);
        }
        return scopeRepository.findAll(spec, pageable).map(ScopeDto::from);
    }

    @Override
    public ScopeDto addScope(ScopeDto scope) {
        Scope newScope = Scope.builder()
                .scope(scope.getScope())
                .description(scope.getDescription())
                .build();
        return ScopeDto.from(scopeRepository.save(newScope));
    }

    @Override
    public ScopeDto editScope(Long id, ScopeDto scope) {
        Scope updatingScope = scopeRepository.getOne(id);
        updatingScope.setDescription(scope.getDescription());
        updatingScope.setScope(scope.getScope());
        return ScopeDto.from(scopeRepository.save(updatingScope));
    }

    @Override
    public void deleteScope(Long id) {
        scopeRepository.deleteById(id);
    }

    @Autowired
    private ScopeRepository scopeRepository;

    static class ScopeSpec {
        public static Specification<Scope> search(String searchKey) {
            return (root, query, builder) -> {
                String likeSearch = "%" + searchKey + "%";
                return builder.or(builder.like(root.get("scope"), likeSearch), builder.like(root.get("description"), likeSearch));
            };
        }
    }
}
