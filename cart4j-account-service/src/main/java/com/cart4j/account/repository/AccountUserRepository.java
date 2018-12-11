package com.cart4j.account.repository;

import com.cart4j.account.entity.AccountUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AccountUserRepository extends JpaRepository<AccountUser, Long>, JpaSpecificationExecutor<AccountUser> {

}
