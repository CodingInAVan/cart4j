package com.cart4j.auth.repository;

import com.cart4j.auth.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {
    boolean existsByRole(String role);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN 1 ELSE 0 END FROM Role r WHERE r.role = ?1 AND r.id = ?2")
    boolean existsByRoleNotId(String role, Long id);
}
