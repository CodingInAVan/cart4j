package com.cart4j.auth.entity;

import com.cart4j.model.security.dto.v1.RoleDto;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name="c4_role")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Role implements Serializable {

    private static final long serialVersionUID = 6415196704389724547L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String role;
    private String description;

    public RoleDto toDto() {
        return RoleDto.builder()
                .description(description)
                .id(id)
                .role(role)
                .build();
    }
}
