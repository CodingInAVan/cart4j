package com.cart4j.auth.dto;

import com.cart4j.auth.entity.Client;
import com.cart4j.auth.entity.RedirectUri;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RedirectUriDto {
    private Long id;
    private String redirectUri;

    public static RedirectUriDto from(RedirectUri redirectUri) {
        return RedirectUriDto.builder()
                .redirectUri(redirectUri.getRedirectUri())
                .id(redirectUri.getId())
                .build();
    }
}
