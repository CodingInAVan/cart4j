package com.cart4j.account.service.v1.impl;

import com.cart4j.account.entity.Account;
import com.cart4j.account.entity.AccountUser;
import com.cart4j.account.exception.AccountInactivatedException;
import com.cart4j.account.repository.AccountRepository;
import com.cart4j.account.repository.AccountUserRepository;
import com.cart4j.account.service.v1.AccountServiceV1;
import com.cart4j.model.account.AccountStatus;
import com.cart4j.model.account.dto.v1.AccountDtoV1;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@Transactional
public class AccountServiceImplV1 implements AccountServiceV1 {
    @Autowired
    public AccountServiceImplV1(AccountRepository accountRepository, AccountUserRepository accountUserRepository) {
        this.accountRepository = accountRepository;
        this.accountUserRepository = accountUserRepository;
    }

    @Override
    public Page<AccountDtoV1> getAccounts(Pageable pageable, String searchKey) {
        Specification<Account> spec = AccountSpec.activated();
        if(!StringUtils.isEmpty(searchKey)) {
            spec = spec.and(AccountSpec.search(searchKey));
        }
        return accountRepository.findAll(spec, pageable).map(Account::toDtoV1);
    }

    @Override
    public List<AccountDtoV1> getAccountsByUsername(String username) {
        Specification<Account> spec = AccountSpec.activated().and(AccountSpec.username(username));
        return accountRepository.findAll(spec).stream().map(Account::toDtoV1).collect(Collectors.toList());
    }

    @Override
    public AccountDtoV1 addAccount(AccountDtoV1 account) {
        Account newAccount = Account.builder()
                .accountName(account.getAccountName())
                .accountUniqueId(account.getAccountUniqueId())
                .description(account.getDescription())
                .status(AccountStatus.ACTIVATED)
                .build();
        newAccount = accountRepository.save(newAccount);
        return newAccount.toDtoV1();
    }

    @Override
    public void addUserToAccount(Long accountId, List<String> usernames) throws AccountInactivatedException {
        Account account = accountRepository.getOne(accountId);
        if(account.getStatus() == AccountStatus.INACTIVATED) {
            throw new AccountInactivatedException("Account[" + accountId +"] is not activated.");
        }

        usernames.forEach(username -> {
            AccountUser accountUser = AccountUser.builder()
                    .account(account)
                    .username(username)
                    .build();
            accountUserRepository.save(accountUser);
        });
    }

    @Override
    public AccountDtoV1 editAccount(Long accountId, AccountDtoV1 account) {
        Account updatingAccount = accountRepository.getOne(accountId);

        updatingAccount.setAccountName(account.getAccountName());
        updatingAccount.setDescription(account.getDescription());
        return accountRepository.save(updatingAccount).toDtoV1();
    }

    @Override
    public AccountDtoV1 inActivateAccount(Long accountId) {
        Account account = accountRepository.getOne(accountId);
        account.setStatus(AccountStatus.INACTIVATED);
        return accountRepository.save(account).toDtoV1();
    }

    private static class AccountSpec {
        private AccountSpec() {}
        private static Specification<Account> activated() {
            return (root, query, builder) -> builder.equal(root.get("status"), AccountStatus.ACTIVATED);
        }
        private static Specification<Account> search(String searchKey) {
            return (root, query, builder) -> builder.or(builder.equal(root.get("accountUnqiueId"), searchKey), builder.like(root.get("accountName"), "%" + searchKey + "%"));
        }
        private static Specification<Account> username(String username) {
            return (root, query, builder) -> {
                Join<Account, AccountUser> accountUser = root.join("accountUsers", JoinType.INNER);
                return builder.equal(accountUser.get("username"), username);
            };
        }
    }

    private final AccountRepository accountRepository;

    private final AccountUserRepository accountUserRepository;
}
