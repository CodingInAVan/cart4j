package com.cart4j.auth.dto;

import com.cart4j.auth.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {
    private Long id;
    private String role;
    private String description;

    public static RoleDto from(Role role) {
        return RoleDto.builder()
                .description(role.getDescription())
                .role(role.getRole())
                .id(role.getId())
                .build();
    }
}
