package com.cart4j.account.service.v1.impl;

import com.cart4j.account.dto.v1.AccountDtoV1;
import com.cart4j.account.entity.Account;
import com.cart4j.account.entity.AccountStatus;
import com.cart4j.account.entity.AccountUser;
import com.cart4j.account.exception.AccountInactivatedException;
import com.cart4j.account.repository.AccountRepository;
import com.cart4j.account.repository.AccountUserRepository;
import com.cart4j.account.service.v1.AccountServiceV1;
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

@Service
@Transactional
public class AccountServiceImplV1 implements AccountServiceV1 {
    @Override
    public Page<AccountDtoV1> getAccounts(Pageable pageable, String searchKey) {
        Specification<Account> spec = AccountSpec.activated();
        if(!StringUtils.isEmpty(searchKey)) {
            spec = spec.and(AccountSpec.search(searchKey));
        }
        return accountRepository.findAll(spec, pageable).map(AccountDtoV1::from);
    }

    @Override
    public List<AccountDtoV1> getAccountsByUsername(String username) {
        Specification<Account> spec = AccountSpec.activated().and(AccountSpec.username(username));
        return accountRepository.findAll(spec).stream().map(AccountDtoV1::from).collect(Collectors.toList());
    }

    @Override
    public AccountDtoV1 addAccount(AccountDtoV1 account) {
        Account newAccount = Account.builder()
                .accountName(account.getAccountName())
                .accountUniqueId(account.getAccountUniqueId())
                .description(account.getDescription())
                .status(AccountStatus.valueOf(account.getStatus()))
                .build();
        return AccountDtoV1.from(accountRepository.save(newAccount));
    }

    @Override
    public void addUserToAccount(Long accountId, List<String> usernames) throws AccountInactivatedException {
        Account account = accountRepository.getOne(accountId);
        if(account.getStatus().equals(AccountStatus.INACTIVATED)) {
            throw new AccountInactivatedException("Account[" + accountId +"] is not activated.");
        }

        usernames.stream().forEach(username -> {
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
        return AccountDtoV1.from(accountRepository.save(updatingAccount));
    }

    @Override
    public AccountDtoV1 inActivateAccount(Long accountId) {
        Account account = accountRepository.getOne(accountId);
        account.setStatus(AccountStatus.INACTIVATED);
        return AccountDtoV1.from(accountRepository.save(account));
    }

    static class AccountSpec {
        public static Specification<Account> activated() {
            return (root, query, builder) -> builder.equal(root.get("status"), AccountStatus.ACTIVATED);
        }
        public static Specification<Account> search(String searchKey) {
            return (root, query, builder) -> builder.or(builder.equal(root.get("accountUnqiueId"), searchKey), builder.like(root.get("accountName"), "%" + searchKey + "%"));
        }
        public static Specification<Account> username(String username) {
            return (root, query, builder) -> {
                Join<Account, AccountUser> accountUser = root.join("accountUser", JoinType.INNER);
                return builder.equal(accountUser.get("username"), username);
            };
        }
    }

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountUserRepository accountUserRepository;
}
