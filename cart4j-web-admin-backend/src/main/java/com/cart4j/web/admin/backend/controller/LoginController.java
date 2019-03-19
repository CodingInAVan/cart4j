package com.cart4j.web.admin.backend.controller;

import com.cart4j.model.security.dto.v1.AuthToken;
import com.cart4j.web.admin.backend.user.login.LoginRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import static java.nio.charset.StandardCharsets.UTF_8;

@RestController
@RequestMapping("/api/login")
public class LoginController {
    @Value("${security.oauth2.token.service}")
    private String authServiceUrl;

    @Value("${auth.server.client.id}")
    private String clientId;

    @Value("${auth.server.client.secret}")
    private String clientSecret;

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @PostMapping
    public AuthToken login(@RequestBody LoginRequest loginRequest) {
        return WebClient.create().post().uri(builder ->
            builder
                    .scheme("http")
                    .host(authServiceUrl)
                    .path("/oauth/token")
                    .queryParam("username", loginRequest.getUsername())
                    .queryParam("password", loginRequest.getPassword())
                    .queryParam("grant_type", "password")
                    .build()
        ).contentType(MediaType.APPLICATION_JSON_UTF8).header("Authorization", "Basic " + Base64Utils.encodeToString((clientId + ":" + clientSecret).getBytes(UTF_8))).retrieve().bodyToMono(AuthToken.class).block();
    }
}
