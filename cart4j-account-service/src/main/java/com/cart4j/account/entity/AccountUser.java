package com.cart4j.account.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="c4_account_user")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="account_id")
    private Account account;

    private String username;
}
