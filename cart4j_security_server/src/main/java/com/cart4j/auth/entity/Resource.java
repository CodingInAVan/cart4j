package com.cart4j.auth.entity;

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
}
