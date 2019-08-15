package com.cart4j.auth.service.impl;

import com.cart4j.auth.Cart4jAuthApp;
import com.cart4j.auth.service.ClientService;
import com.cart4j.auth.service.RedirectUriService;
import com.cart4j.model.security.dto.v1.ClientDto;
import com.cart4j.model.security.dto.v1.RedirectUriDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        Cart4jAuthApp.class})
@ActiveProfiles("test")
public class RedirectUriServiceImplTest {

    @Test
    public void test_persistFunction() {
        ClientDto client = ClientDto.builder()
                .grantTypes("password")
                .clientUniqueId("TEST")
                .clientSecret("12345")
                .build();
        client = clientService.addClient(client);
        RedirectUriDto redirectUriDto = RedirectUriDto.builder()
                .redirectUri("http://localhost")
                .build();
        redirectUriDto = redirectUriService.addRedirectUri(client.getId(), redirectUriDto);
        assertThat(redirectUriDto.getId()).isNotNull();
        assertThat(redirectUriDto.getRedirectUri()).isEqualTo("http://localhost");

        RedirectUriDto updated = RedirectUriDto.builder()
                .redirectUri("http://127.0.0.1").build();
        updated = redirectUriService.editRedirectUri(redirectUriDto.getId(), updated);
        assertThat(updated.getId()).isEqualTo(redirectUriDto.getId());
        assertThat(updated.getRedirectUri()).isEqualTo("http://127.0.0.1");

        redirectUriService.deleteRedirectUri(updated.getId());
        assertThat(redirectUriService.getRedirectUri(updated.getId())).isNull();
    }

    @Autowired
    ClientService clientService;

    @Autowired
    RedirectUriService redirectUriService;
}
