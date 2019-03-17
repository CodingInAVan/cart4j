package com.cart4j.auth.controller;

import com.cart4j.auth.exception.RoleAlreadyExistingException;
import com.cart4j.auth.exception.UserAlreadyExistingException;
import com.cart4j.auth.service.ClientService;
import com.cart4j.auth.service.RoleService;
import com.cart4j.auth.service.ScopeService;
import com.cart4j.auth.service.UserService;
import com.cart4j.model.security.dto.v1.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;


@Component
public class TestAuth {
    @Autowired
    private UserService userService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private ScopeService scopeService;
    @Autowired
    private RoleService roleService;

    private RestTemplate restTemplate = new RestTemplate();

    public static final String TEST_USERNAME = "tester";
    public static final String TEST_PASSWORD = "12345";
    public static final String CLIENT_ID = "CLIENT-SECURITY-API";
    public static final String CLIENT_SECRET = "12345";

    public TestAuth() {}

    /***
     * Install an admin user and client for testing
     *
     * @throws UserAlreadyExistingException
     * @throws RoleAlreadyExistingException
     */
    public void install() throws UserAlreadyExistingException, RoleAlreadyExistingException {
        try {
            UserDto user = UserDto.builder()
                    .email("test@cart4j.com")
                    .password(TEST_PASSWORD)
                    .username(TEST_USERNAME)
                    .build();
            user = userService.addUser(user);

            RoleDto role = RoleDto.builder()
                    .role("USER_AUTH_ADMIN")
                    .build();
            role = roleService.addRole(role);
            userService.setRole(Arrays.asList(role.getId()), user.getId());

            ClientDto client = ClientDto.builder()
                    .clientSecret(CLIENT_SECRET)
                    .clientUniqueId(CLIENT_ID)
                    .grantTypes("password")
                    .build();

            client = clientService.addClient(client);

            ScopeDto scope = ScopeDto.builder()
                    .scope("SECURITY_API_ADMIN")
                    .build();

            scope = scopeService.addScope(scope);
            clientService.setScopes(Arrays.asList(scope.getId()), client.getId());
        } catch (UserAlreadyExistingException | RoleAlreadyExistingException e) {
            //Skip if it exists
        }


    }

    public AuthToken requestToken(String host) {

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(TestAuth.CLIENT_ID, TestAuth.CLIENT_SECRET);
        String queryString = "?grant_type=password&username=" + TestAuth.TEST_USERNAME + "&password=" + TestAuth.TEST_PASSWORD;
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<AuthToken> response = restTemplate.postForEntity(host + queryString, request, AuthToken.class);
        return response.getBody();
    }
}
