package com.cart4j.account.dto.v1;

import com.cart4j.account.entity.Account;
import com.cart4j.account.entity.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDtoV1 {
    private Long id;

    private String accountUniqueId;

    private String accountName;

    private String description;

    private String status;

    public static AccountDtoV1 from(Account account) {
        return AccountDtoV1.builder()
                .accountName(account.getAccountName())
                .accountUniqueId(account.getAccountUniqueId())
                .description(account.getDescription())
                .status(account.getStatus().name())
                .id(account.getId())
                .build();
    }
}
