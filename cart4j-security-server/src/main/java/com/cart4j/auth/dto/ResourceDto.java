package com.cart4j.auth.dto;

import com.cart4j.auth.entity.Resource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResourceDto {
    private Long id;
    private String resourceUniqueId;
    private String description;

    public static ResourceDto from(Resource resource) {
        return ResourceDto.builder()
                .description(resource.getDescription())
                .id(resource.getId())
                .resourceUniqueId(resource.getResourceUniqueId())
                .build();
    }
}
