package com.cart4j.account.controller;

import com.cart4j.account.dto.v1.AccountDtoV1;
import com.cart4j.account.exception.AccountInactivatedException;
import com.cart4j.account.service.v1.AccountServiceV1;
import com.cart4j.common.dto.ErrorResponse;
import com.cart4j.common.dto.PageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.ClientAlreadyExistsException;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/accounts/v1")
public class AccountController {
    @GetMapping
    @PreAuthorize("#oauth2.hasScope('ACCOUNT_API_READ') or #oauth2.hasScope('ACCOUNT_API_WRITE')")
    PageDto<AccountDtoV1> getAccounts(Pageable pageable, @RequestParam(name="searchKey", required = false) String searchKey) {
        Page<AccountDtoV1> accountsPage = accountServiceV1.getAccounts(pageable, searchKey);
        return PageDto.<AccountDtoV1>builder()
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .totalPage(accountsPage.getTotalPages())
                .list(accountsPage.getContent())
                .build();
    }

    @GetMapping("/user/{username}")
    @PreAuthorize("#oauth2.hasScope('ACCOUNT_API_READ') or #oauth2.hasScope('ACCOUNT_API_WRITE')")
    List<AccountDtoV1> getAccountsByUsername(@PathVariable String username) {
        return accountServiceV1.getAccountsByUsername(username);
    }

    @PostMapping
    @PreAuthorize("#oauth2.hasScope('ACCOUNT_API_WRITE')")
    AccountDtoV1 addAccount(@RequestBody AccountDtoV1 account) {
        return accountServiceV1.addAccount(account);
    }

    @PutMapping("/{accountId}")
    @PreAuthorize("#oauth2.hasScope('ACCOUNT_API_WRITE')")
    AccountDtoV1 editAccount(@PathVariable Long accountId, @RequestBody AccountDtoV1 account) {
        return accountServiceV1.editAccount(accountId, account);
    }

    @DeleteMapping("/{accountId}")
    @PreAuthorize("#oauth2.hasScope('ACCOUNT_API_WRITE')")
    AccountDtoV1 deleteAccount(@PathVariable Long accountId) {
        return accountServiceV1.inActivateAccount(accountId);
    }

    @PutMapping("/{accountId}/user/{username}")
    @PreAuthorize("#oauth2.hasScope('ACCOUNT_API_WRITE')")
    void addUserToAccounts(@PathVariable Long accountId, @PathVariable String username) throws AccountInactivatedException {
        accountServiceV1.addUserToAccount(accountId, Arrays.asList(username));
    }

    @ExceptionHandler({AccountInactivatedException.class})
    ResponseEntity<ErrorResponse> handleAccountException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.builder().errorCode(HttpStatus.PRECONDITION_FAILED.value()).message(e.getMessage()).build());
    }


    @Autowired private AccountServiceV1 accountServiceV1;
}
