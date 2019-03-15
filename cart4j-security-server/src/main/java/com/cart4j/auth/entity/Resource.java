package com.cart4j.auth.entity;

import com.cart4j.model.security.dto.v1.ResourceDto;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="c4_resource")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String resourceUniqueId;

    private String description;

    @ManyToMany(mappedBy = "resources")
    private List<Client> clients;

    public ResourceDto toDto() {
        return ResourceDto.builder()
                .description(description)
                .id(id)
                .resourceUniqueId(resourceUniqueId)
                .build();
    }
}
