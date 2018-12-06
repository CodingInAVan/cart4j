package com.cart4j.auth.repository;

import com.cart4j.auth.entity.RedirectUri;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RedirectUriRepository extends JpaRepository<RedirectUri, Long>, JpaSpecificationExecutor<RedirectUri> {
}
