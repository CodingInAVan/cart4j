package com.cart4j.auth.dto;

import com.cart4j.auth.entity.Client;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class ClientDto {
    private Long id;
    private String clientUniqueId;
    private String clientSecret;
    private String grantTypes;

    public static ClientDto from(Client client) {
        return ClientDto.builder()
                .id(client.getId())
                .clientSecret(client.getClientSecret())
                .clientUniqueId(client.getClientUniqueId())
                .grantTypes(client.getGrantTypes())
                .build();
    }
}
