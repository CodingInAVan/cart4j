package com.cart4j.auth;

import com.cart4j.auth.service.ClientService;
import com.cart4j.auth.service.RoleService;
import com.cart4j.auth.service.ScopeService;
import com.cart4j.auth.service.UserService;
import com.cart4j.model.security.dto.v1.ClientDto;
import com.cart4j.model.security.dto.v1.RoleDto;
import com.cart4j.model.security.dto.v1.ScopeDto;
import com.cart4j.model.security.dto.v1.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

@Component
public class PreRunner implements CommandLineRunner {
    @Value("${install}")
    private Boolean install;

    private final UserService userService;
    private final ClientService clientService;
    private final ScopeService scopeService;
    private final RoleService roleService;

    @Autowired
    public PreRunner(UserService userService, ClientService clientService, ScopeService scopeService, RoleService roleService) {
        this.userService = userService;
        this.clientService = clientService;
        this.scopeService = scopeService;
        this.roleService = roleService;
    }

    @Override
    public void run(String... args) throws Exception {
        /*
         * When you initially run this application on server, you will need to create tables
         * and add an admin user with admin roles.
         *
         */
        if(install) {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Please input username for admin: ");

            String username = scanner.next();

            System.out.println("Please input email for admin: ");

            String email = scanner.next();

            System.out.println("Please input password for admin: ");

            String password = scanner.next();

            UserDto user = UserDto.builder()
                    .email(email)
                    .password(password)
                    .username(username)
                    .build();
            user = userService.addUser(user);

            RoleDto role = RoleDto.builder()
                    .role("USER_AUTH_ADMIN")
                    .description("Administrator Role for Auth")
                    .build();
            role = roleService.addRole(role);

            userService.setRole(Arrays.asList(role.getId()), user.getId());

            String clientUniqueId = "CLIENT-SECURITY-API";

            System.out.println("Please input password for client: ");

            String clientPassword = scanner.next();

            /**
             * The admin user with this client credential will be required for all api accesses for this security service.
             */

            ClientDto client = ClientDto.builder()
                    .clientSecret(clientPassword)
                    .clientUniqueId(clientUniqueId)
                    .grantTypes("client_credentials,password")
                    .build();

            client = clientService.addClient(client);

            ScopeDto scope = ScopeDto.builder()
                    .scope("SECURITY_API_ADMIN")
                    .description("API Access for Security Server")
                    .build();

            scope = scopeService.addScope(scope);
            clientService.setScopes(Collections.singletonList(scope.getId()), client.getId());

            System.out.println("The admin user and the client have been created...");
        }


    }
}
