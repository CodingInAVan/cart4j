package com.cart4j.auth.repository;

import com.cart4j.auth.entity.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ScopeRepository extends JpaRepository<Scope, Long>, JpaSpecificationExecutor<Scope> {
}
