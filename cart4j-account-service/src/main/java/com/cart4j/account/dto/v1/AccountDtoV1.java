package com.cart4j.account.dto.v1;

import com.cart4j.model.account.AccountStatus;
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

    private AccountStatus status;
}
