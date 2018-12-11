package com.cart4j.account.service.v1;

import com.cart4j.account.dto.v1.AccountDtoV1;
import com.cart4j.account.exception.AccountInactivatedException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AccountServiceV1 {
    Page<AccountDtoV1> getAccounts(Pageable pageable, String searchKey);
    List<AccountDtoV1> getAccountsByUsername(String username);
    AccountDtoV1 addAccount(AccountDtoV1 account);
    void addUserToAccount(Long accountId, List<String> usernames) throws AccountInactivatedException;
    AccountDtoV1 editAccount(Long accountId, AccountDtoV1 account);
    AccountDtoV1 inActivateAccount(Long accountId);
}
