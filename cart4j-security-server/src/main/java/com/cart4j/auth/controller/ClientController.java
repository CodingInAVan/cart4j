package com.cart4j.auth.controller;

import com.cart4j.auth.dto.ClientDto;
import com.cart4j.auth.dto.ErrorResponse;
import com.cart4j.auth.exception.ClientNotFoundException;
import com.cart4j.auth.exception.ScopeNotFoundException;
import com.cart4j.auth.service.ClientService;
import com.cart4j.common.dto.PageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.ClientAlreadyExistsException;
import org.springframework.web.bind.annotation.*;
import sun.security.acl.PrincipalImpl;

import java.security.Principal;


@RestController
@RequestMapping("/api/auth/client")
public class ClientController {
    @GetMapping
    @PreAuthorize("#oauth2.hasScope('SECURITY_API_ADMIN') and hasAuthority('USER_AUTH_ADMIN')")
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
    @PreAuthorize("#oauth2.hasScope('SECURITY_API_ADMIN') and hasAuthority('USER_AUTH_ADMIN')")
    ClientDto addClient(Principal principal, @RequestBody ClientDto client) throws ClientAlreadyExistsException {
        ClientDto newClient = clientService.addClient(client);
        LOGGER.info("{} added the client[{}]", principal.getName(), newClient.getId());
        return newClient;
    }

    @PutMapping("/{id}")
    @PreAuthorize("#oauth2.hasScope('SECURITY_API_ADMIN') and hasAuthority('USER_AUTH_ADMIN')")
    ClientDto editClient(Principal principal, @RequestBody ClientDto client, @PathVariable Long id) {
        ClientDto modifiedClient = clientService.editClient(id, client);
        LOGGER.info("{} modified the client[{}]", principal.getName(), modifiedClient.getId());
        return modifiedClient;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("#oauth2.hasScope('SECURITY_API_ADMIN') and hasAuthority('USER_AUTH_ADMIN')")
    void deleteClient(Principal principal, @PathVariable Long id) {
        clientService.deleteClient(id);
        LOGGER.info("{} modified the client[{}]", principal.getName(), id);
    }

    /**
     * Adding a scope to the client.
     */
    @PostMapping("/{id}/scope/{scopeId}")
    @PreAuthorize("#oauth2.hasScope('SECURITY_API_ADMIN') and hasAuthority('USER_AUTH_ADMIN')")
    ClientDto addScope(@PathVariable Long id, @PathVariable Long scopeId, Principal principal) throws ClientNotFoundException, ScopeNotFoundException {
        ClientDto client = clientService.addScope(scopeId, id);
        LOGGER.info("{} added a scope[{}] to client[{}]", principal.getName(), scopeId, client.getId());
        return client;
    }

    @ExceptionHandler({ClientAlreadyExistsException.class,ClientNotFoundException.class, ScopeNotFoundException.class})
    ResponseEntity<ErrorResponse> clientException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.builder().errorCode(HttpStatus.PRECONDITION_FAILED.value()).message(e.getMessage()).build());
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientController.class);
    @Autowired
    private ClientService clientService;

}
