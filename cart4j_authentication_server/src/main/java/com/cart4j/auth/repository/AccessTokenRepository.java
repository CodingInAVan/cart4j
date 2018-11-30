package com.cart4j.auth.repository;

import com.cart4j.auth.entity.AccessToken;
import com.cart4j.auth.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccessTokenRepository extends JpaRepository<AccessToken, Long> {
    AccessToken findByTokenKey(String tokenKey);
    AccessToken findByAuthenticationKey(String authenticationKey);

    @Query("SELECT a FROM AccessToken a INNER JOIN a.client c WHERE c.clientUniqueId = ?1")
    List<AccessToken> getByCilentUniqueId(String clientUniqueId);

    @Query("SELECT a FROM AccessToken a WHERE a.refreshToken.id = ?1")
    AccessToken getOneByRefreshTokenId(Long refreshTokenId);

    @Query("SELECT a FROM AccessToken a INNER JOIN a.client c INNER JOIN a.user u WHERE u.username = ?2 and c.clientUniqueId = ?1")
    List<AccessToken> findByClientUniqueIdAndUsername(String clientUniqueId, String username);

    @Modifying
    @Query("DELETE FROM AccessToken t WHERE t.tokenKey = ?1")
    void deleteByTokenKey(String tokenKey);

    @Modifying
    @Query("DELETE FROM AccessToken t WHERE t.refreshToken.tokenKey = ?1")
    void deleteByRefreshTokenKey(String refreshTokenKey);
}
