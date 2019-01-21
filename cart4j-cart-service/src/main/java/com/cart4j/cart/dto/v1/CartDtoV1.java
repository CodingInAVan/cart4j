package com.cart4j.cart.dto.v1;

import com.cart4j.cart.entity.Cart;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CartDtoV1 {
    private Long id;
    /**
     * Session will be created for each client connection and remaining until the expiry date.
     */
    private String session;
    /**
     * Username. For a visitor, this field will be empty when carts allows anonymous user.
     * After a user put items into their cart without logged in and then log in, the list of items will be merge into the user's cart.
     */
    private String username;

    /**
     * Account ID.
     * Cart4j can provide multiple accounts. It means you can create multiple stores and storing all information separately.
     */
    private Long accountId;

    private List<CartItemDtoV1> cartItems;

    private Date addedAt;

    public static CartDtoV1 from(Cart cart) {
        List<CartItemDtoV1> cartItems = null;
        if(!CollectionUtils.isEmpty(cart.getCartItems())) {
            cartItems = cart.getCartItems().stream().map(CartItemDtoV1::from).collect(Collectors.toList());;
        }
        return CartDtoV1.builder()
                .accountId(cart.getAccountId())
                .addedAt(cart.getAddedAt())
                .cartItems(cartItems)
                .id(cart.getId())
                .session(cart.getSession())
                .username(cart.getUsername())
                .build();
    }
}
