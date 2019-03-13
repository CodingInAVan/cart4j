package com.cart4j.auth.service.impl;

import com.cart4j.auth.entity.Role;
import com.cart4j.auth.exception.RoleAlreadyExistingException;
import com.cart4j.auth.repository.RoleRepository;
import com.cart4j.auth.service.RoleService;
import com.cart4j.model.security.dto.v1.RoleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Page<RoleDto> getRoles(Pageable pageable, String searchKey) {
        Specification<Role> spec = null;

        if(!StringUtils.isEmpty(searchKey)) {
            spec = RoleSpec.search(searchKey);
        }
        return roleRepository.findAll(spec, pageable).map(Role::toDto);
    }

    @Override
    public RoleDto getRole(Long id) {
        if(roleRepository.existsById(id)) {
            return roleRepository.getOne(id).toDto();
        }
        return null;
    }

    @Override
    public RoleDto addRole(RoleDto role) throws RoleAlreadyExistingException {
        if(roleRepository.existsByRole(role.getRole())) {
           throw new RoleAlreadyExistingException("Role[" + role.getRole() + "] is already in use.");
        }
        Role newRole = Role.builder()
                .role(role.getRole())
                .description(role.getDescription())
                .build();
        return roleRepository.save(newRole).toDto();
    }

    @Override
    public RoleDto editRole(Long id, RoleDto role) throws RoleAlreadyExistingException {
        if(roleRepository.existsByRoleNotId(role.getRole(), id)) {
            throw new RoleAlreadyExistingException("Role[" + role.getRole() + "] is already in use.");
        }
        Role updatingRole = roleRepository.getOne(id);
        updatingRole.setDescription(role.getDescription());
        updatingRole.setRole(role.getRole());
        return roleRepository.save(updatingRole).toDto();
    }

    @Override
    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }

    private static class RoleSpec {
        private static Specification<Role> search(String searchKey) {
            return (root, query, builder) -> {
                String likeSearch = "%" + searchKey + "%";
                return builder.or(builder.like(root.get("role"), likeSearch), builder.like(root.get("description"), likeSearch));
            };
        }
    }

    private final RoleRepository roleRepository;
}
