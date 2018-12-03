package com.cart4j.auth.dto;

import com.cart4j.auth.entity.Client;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ClientDto {
    private Long id;
    private String clientUniqueId;
    private String clientSecret;
    private String grantTypes;

    public static ClientDto from(Client client) {
        return ClientDto.builder()
                .id(client.getId())
                .clientUniqueId(client.getClientUniqueId())
                .grantTypes(client.getGrantTypes())
                .build();
    }
}
