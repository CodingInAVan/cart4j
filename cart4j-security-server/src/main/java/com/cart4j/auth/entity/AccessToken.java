package com.cart4j.auth.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name="c4_access_token")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
public class AccessToken implements Serializable {
    private static final long serialVersionUID = 5196041473178432400L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tokenKey;

    @Lob
    private byte[] tokenValue;
    private Date expirationDate;
    private String authenticationKey;
    @Lob
    private byte[] authentication;

    private Date createdAt;

    @ManyToOne
    @JoinTable(name = "c4_user_access_token", joinColumns = @JoinColumn(name = "access_token_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private User user;

    @ManyToOne
    @JoinTable(name = "c4_client_access_token", joinColumns = @JoinColumn(name = "access_token_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "client_id", referencedColumnName = "id"))
    private Client client;

    @OneToOne
    @JoinColumn(name="refresh_token_id")
    private RefreshToken refreshToken;
}
