package com.cart4j.account.entity;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountUniqueId;

    private String accountName;

    private String description;

    @OneToMany(mappedBy = "account")
    List<AccountUser> accountUsers;

    @Enumerated(value=EnumType.STRING)
    private AccountStatus status;
}
