package com.cart4j.auth.service.impl;

import com.cart4j.auth.Cart4jAuthApp;
import com.cart4j.auth.dto.ClientDto;
import com.cart4j.auth.service.ClientService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Cart4jAuthApp.class})
@ActiveProfiles("test")
public class ClientServiceImplTest {

    @Test
    public void test_persistFunction() {
        ClientDto client = ClientDto.builder()
                .clientUniqueId("TEST_1")
                .grantTypes("password")
                .build();
        client = clientService.addClient(client);
        System.out.println(client);
        assertThat(client.getId()).isNotNull();


        ClientDto updating = ClientDto.builder()
                .clientUniqueId("TEST_2")
                .build();
        updating = clientService.editClient(client.getId(), updating);
        // Does not allow to change the client unique id
        assertThat(updating.getClientUniqueId()).isEqualTo("TEST_1");

        clientService.deleteClient(updating.getId());
        assertThat(clientService.getClient(updating.getId())).isNull();
    }

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ClientService clientService;
}
