package com.cart4j.auth.controller;

import com.cart4j.auth.exception.ClientNotFoundException;
import com.cart4j.auth.exception.ScopeNotFoundException;
import com.cart4j.auth.service.ClientService;
import com.cart4j.model.common.ErrorResponse;
import com.cart4j.model.common.PageDto;
import com.cart4j.model.security.dto.v1.ClientDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.ClientAlreadyExistsException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
@RequestMapping("/api/auth/clients")
public class ClientController {
    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    @PreAuthorize("#oauth2.hasScope('SECURITY_API_ADMIN')")
    PageDto<ClientDto> getClients(Pageable pageable,
                                  @RequestParam (value = "searchKey", required = false) String searchKey) {
        Page<ClientDto> clientsPage = clientService.getClients(pageable, searchKey);
        return PageDto.<ClientDto>builder().limit(pageable.getPageSize())
            .list(clientsPage.getContent())
            .offset(pageable.getOffset())
            .totalPage(clientsPage.getTotalPages())
            .totalRecords(clientsPage.getTotalElements())
            .build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("#oauth2.hasScope('SECURITY_API_ADMIN')")
    ClientDto getClient(@PathVariable Long id) {
        return clientService.getClient(id);
    }

    @PostMapping
    @PreAuthorize("#oauth2.hasScope('SECURITY_API_ADMIN')")
    ClientDto addClient(Principal principal, @RequestBody ClientDto client) throws ClientAlreadyExistsException {
        System.out.println(client);
        ClientDto newClient = clientService.addClient(client);
        LOGGER.info("{} added the client[{}]", principal.getName(), newClient.getId());
        return newClient;
    }

    @PutMapping("/{id}")
    @PreAuthorize("#oauth2.hasScope('SECURITY_API_ADMIN')")
    ClientDto editClient(Principal principal, @RequestBody ClientDto client, @PathVariable Long id) {
        ClientDto modifiedClient = clientService.editClient(id, client);
        LOGGER.info("{} modified the client[{}]", principal.getName(), modifiedClient.getId());
        return modifiedClient;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("#oauth2.hasScope('SECURITY_API_ADMIN')")
    void deleteClient(Principal principal, @PathVariable Long id) {
        clientService.deleteClient(id);
        LOGGER.info("{} modified the client[{}]", principal.getName(), id);
    }

    /**
     * Adding a scope to the client.
     */
    @PostMapping("/{id}/scope/{scopeId}")
    @PreAuthorize("#oauth2.hasScope('SECURITY_API_ADMIN')")
    ClientDto addScope(@PathVariable Long id, @PathVariable Long scopeId, Principal principal) throws ClientNotFoundException, ScopeNotFoundException {
        ClientDto client = clientService.addScope(scopeId, id);
        LOGGER.info("{} added a scope[{}] to client[{}]", principal.getName(), scopeId, client.getId());
        return client;
    }

    @ExceptionHandler({ClientAlreadyExistsException.class,ClientNotFoundException.class, ScopeNotFoundException.class})
    ResponseEntity<ErrorResponse> handleClientException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.builder().errorCode(HttpStatus.PRECONDITION_FAILED.value()).message(e.getMessage()).build());
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientController.class);
    private final ClientService clientService;

}
