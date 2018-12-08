package com.cart4j.auth.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="c4_redirect_uri")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RedirectUri {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String redirectUri;
    @ManyToOne
    @JoinColumn(name="client_id")
    private Client client;
}
