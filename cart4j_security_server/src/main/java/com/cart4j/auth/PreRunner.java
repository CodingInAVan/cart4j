package com.cart4j.auth;

import com.cart4j.auth.dto.ClientDto;
import com.cart4j.auth.dto.UserDto;
import com.cart4j.auth.repository.UserRepository;
import com.cart4j.auth.service.ClientService;
import com.cart4j.auth.service.UserService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Scanner;

@Component
public class PreRunner implements CommandLineRunner {
    @Value("${install}")
    private Boolean install;

    @Autowired
    private UserService userService;
    @Autowired
    private ClientService clientService;

    @Override
    public void run(String... args) throws Exception {
        /**
         * When you initially run this application on server, you will need to create tables
         * and add an admin user with basic roles.
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

            UserDto userDto = UserDto.builder()
                    .email(email)
                    .password(password)
                    .username(username)
                    .build();
            userService.addUser(userDto);

            System.out.println("Please input clientId for Auth API: ");

            String clientUniqueId = scanner.next();

            System.out.println("Please input password for it: ");

            String clientPassword = scanner.next();

            /**
             * The admin user with this client credential will be required for all api accesses for this security service.
             */

            ClientDto clientDto = ClientDto.builder()
                    .clientSecret(clientPassword)
                    .clientUniqueId(clientUniqueId)
                    .grantTypes("client_credentials,password")
                    .build();

            clientService.addClient(clientDto);

            System.out.println("The admin user and the client have been created...");
        }


    }
}
