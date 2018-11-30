package com.cart4j.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;

import javax.persistence.*;

@Entity
@Table(name="c4_refresh_token")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken implements OAuth2RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "refresh_token_key")
    private String refreshTokenKey;

    @Lob
    @Column(name = "refresh_token_value")
    private byte[] refreshTokenValue;

    @Lob
    @Column(name = "authentication_value")
    private byte[] authenticationValue;

    @OneToOne(mappedBy = "refreshToken")
    private AccessToken accessToken;

    @Override
    public String getValue() {
        return new String(refreshTokenValue);
    }
}
