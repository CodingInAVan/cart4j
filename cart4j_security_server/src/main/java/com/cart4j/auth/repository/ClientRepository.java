package com.cart4j.auth.repository;

import com.cart4j.auth.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>, JpaSpecificationExecutor<Client> {
    @Query("SELECT c FROM Client c WHERE c.clientUniqueId = ?1")
    Client findByClientUniqueId(String clientUniqueId);

    boolean existsByClientUniqueId(String clientUniqueId);
}
