package com.cart4j.auth.controller;

import com.cart4j.auth.core.UserPrincipal;
import com.cart4j.auth.dto.ClientDto;
import com.cart4j.auth.service.ClientService;
import com.cart4j.auth.service.impl.ClientDetailsServiceImpl;
import com.cart4j.common.dto.PageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth/client")
public class ClientController {
    @GetMapping
    @PreAuthorize("hasAuthority('CLIENT_ADMIN') and hasAuthority('USER_AUTH_ADMIN')")
    PageDto<ClientDto> getClients(Pageable pageable, String searchKey) {
        Page<ClientDto> clientsPage = clientService.getClients(pageable, searchKey);
        return PageDto.<ClientDto>builder().limit(pageable.getPageSize())
            .list(clientsPage.getContent())
            .offset(pageable.getOffset())
            .totalPage(clientsPage.getTotalPages())
            .totalRecords(clientsPage.getTotalElements())
            .build();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CLIENT_ADMIN') and hasAuthority('USER_AUTH_ADMIN')")
    ClientDto addClient(UserPrincipal principal, ClientDto client) {
        ClientDto newClient = clientService.addClient(client);
        LOGGER.info("{} added a client {}", principal.getUsername(), newClient);
        return newClient;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientController.class);
    @Autowired
    private ClientService clientService;

}
