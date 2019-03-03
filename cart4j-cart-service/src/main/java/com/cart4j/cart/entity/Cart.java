package com.cart4j.cart.entity;

import com.cart4j.model.cart.dto.v1.CartDtoV1;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="c4_cart")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String session;

    private String username;

    private Long accountId;

    private Date addedAt;

    @OneToMany(mappedBy="cart")
    private List<CartItem> cartItems;

    public CartDtoV1 toDtoV1() {
        return CartDtoV1
                .builder()
                .accountId(accountId)
                .addedAt(addedAt)
                .cartItems(cartItems.stream().map(CartItem::toDtoV1).collect(Collectors.toList()))
                .session(session)
                .username(username)
                .build();
    }
}
