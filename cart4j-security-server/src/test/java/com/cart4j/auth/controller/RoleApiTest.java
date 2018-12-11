package com.cart4j.auth.controller;

import com.cart4j.auth.Cart4jAuthApp;
import com.cart4j.auth.dto.AuthToken;
import com.cart4j.auth.dto.ClientDto;
import com.cart4j.auth.dto.RoleDto;
import com.cart4j.auth.exception.RoleAlreadyExistingException;
import com.cart4j.auth.exception.UserAlreadyExistingException;
import com.cart4j.common.dto.PageDto;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Cart4jAuthApp.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class RoleApiTest {
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

    private String tokenHost;
    private String serverHost;
    private AuthToken token;

    @Before
    public void setUp() throws UserAlreadyExistingException, RoleAlreadyExistingException {
        testAuth.install();
        tokenHost = "http://localhost:" + randomServerPort + "/oauth/token";
        serverHost = "http://localhost:" + randomServerPort + "/api/auth/roles";
        token = testAuth.requestToken(tokenHost);
    }
    @Test
    public void test_crunFunctions() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token.getAccessToken());
        RoleDto role = RoleDto
                .builder()
                .role("ROLE_TEST")
                .build();

        HttpEntity<RoleDto> requestForAdding = new HttpEntity<>(role, headers);
        ResponseEntity<RoleDto> responseForAdding = restTemplate.postForEntity(serverHost, requestForAdding, RoleDto.class);
        assertThat(responseForAdding.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseForAdding.getBody().getRole()).isEqualTo("ROLE_TEST");


    }
}
