package com.cart4j.auth.controller;

import com.cart4j.auth.Cart4jAuthApp;
import com.cart4j.auth.dto.AuthToken;
import com.cart4j.auth.dto.ClientDto;
import com.cart4j.auth.dto.RedirectUriDto;
import com.cart4j.auth.dto.ResourceDto;
import com.cart4j.auth.exception.RoleAlreadyExistingException;
import com.cart4j.auth.exception.UserAlreadyExistingException;
import com.cart4j.auth.service.ClientService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Cart4jAuthApp.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class RedirectUriApiTest {
    @LocalServerPort
    int randomServerPort;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public TestAuth testAuth(){
            return new TestAuth();
        }
    }

    @Autowired
    private TestAuth testAuth;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ClientService clientService;

    private String tokenHost;
    private String serverHost;
    private AuthToken token;

    @Before
    public void setUp() throws UserAlreadyExistingException, RoleAlreadyExistingException {
        testAuth.install();
        tokenHost = "http://localhost:" + randomServerPort + "/oauth/token";
        serverHost = "http://localhost:" + randomServerPort + "/api/auth/redirect-uri/client/";
        token = testAuth.requestToken(tokenHost);
    }
    @Test
    public void test_crudFunctions() throws IOException {
        ClientDto client = clientService.getClientByClientUniqueId(TestAuth.CLIENT_ID);
        serverHost = serverHost + client.getId();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token.getAccessToken());
        RedirectUriDto resource = RedirectUriDto.builder()
                .redirectUri("REDIRECT_URI_TEST")
                .build();

        HttpEntity<RedirectUriDto> requestForAdding = new HttpEntity<>(resource, headers);
        ResponseEntity<RedirectUriDto> responseForAdding = restTemplate.postForEntity(serverHost, requestForAdding, RedirectUriDto.class);
        assertThat(responseForAdding.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseForAdding.getBody().getRedirectUri()).isEqualTo("REDIRECT_URI_TEST");


    }
}
