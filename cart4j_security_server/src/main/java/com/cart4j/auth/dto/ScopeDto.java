package com.cart4j.auth.dto;

import com.cart4j.auth.entity.Scope;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScopeDto {
    private Long id;
    private String scope;
    private String description;

    public static ScopeDto from(Scope scope) {
        return ScopeDto.builder()
                .id(scope.getId())
                .description(scope.getDescription())
                .scope(scope.getScope())
                .build();
    }
}
