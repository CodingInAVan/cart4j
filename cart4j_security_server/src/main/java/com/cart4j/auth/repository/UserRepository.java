package com.cart4j.auth.repository;

import com.cart4j.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.email = ?1 AND u.username <> ?2")
    boolean existsByEmailAndNotUsername(String email, String username);
    boolean existsByUsername(String username);
    @Query("SELECT u FROM User u WHERE (u.email = ?1 or u.username = ?1) AND u.activated = 1")
    User getByUsernameOrEmail(String usernameOrEmail);
}
