package com.cart4j.account.entity;

import com.cart4j.model.account.AccountStatus;
import com.cart4j.model.account.dto.v1.AccountDtoV1;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="c4_account")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String accountUniqueId;

    private String accountName;

    private String description;

    @OneToMany(mappedBy = "account")
    List<AccountUser> accountUsers;

    @Enumerated(value=EnumType.STRING)
    private AccountStatus status;

    public AccountDtoV1 toDtoV1() {
        return AccountDtoV1.builder()
                .accountName(accountName)
                .accountUniqueId(accountUniqueId)
                .description(description)
                .status(status)
                .id(id)
                .build();
    }
}
