package com.cart4j.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="c4_redirect_uri")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RedirectUri {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String redirectUri;
    @ManyToMany(mappedBy="redirectUris")
    private List<Client> clients;
}
