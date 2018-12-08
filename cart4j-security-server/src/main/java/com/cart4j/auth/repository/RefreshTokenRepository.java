package com.cart4j.auth.repository;

import com.cart4j.auth.entity.RefreshToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    @Query("SELECT r FROM RefreshToken r WHERE r.refreshTokenKey = ?1")
    List<RefreshToken> findByRefreshTokenKey(String refreshTokenKey);

    @Query("SELECT r FROM RefreshToken r WHERE r.refreshTokenKey = ?1")
    Page<RefreshToken> getByRefreshTokenKey(String refreshTokenKey, Pageable pageable);

    @Modifying
    @Query("DELETE from RefreshToken t WHERE t.refreshTokenKey = ?1")
    void deleteByRefreshTokenKey(String refreshTokenKey);
}
