package com.cart4j.auth.repository;

import com.cart4j.auth.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long>, JpaSpecificationExecutor<Resource> {
    boolean existsByResourceUniqueId(String resource);
    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Resource r WHERE r.resourceUniqueId = ?1 AND r.id = ?2")
    boolean existsByResourceUniqueIdNotId(String resource, Long id);
}
