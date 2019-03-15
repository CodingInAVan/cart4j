package com.cart4j.auth.entity;

import com.cart4j.model.security.dto.v1.ScopeDto;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="c4_scope")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Scope implements Serializable {

   private static final long serialVersionUID = 7185490324882745146L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String scope;

    private String description;

    @ManyToMany(mappedBy="scopes")
    private List<Client> clients;

    public ScopeDto toDto() {
     return ScopeDto.builder()
             .description(description)
             .scope(scope)
             .id(id)
             .build();
    }
}
