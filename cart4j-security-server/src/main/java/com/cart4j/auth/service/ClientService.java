package com.cart4j.auth.service;

import com.cart4j.auth.exception.ClientNotFoundException;
import com.cart4j.auth.exception.ScopeNotFoundException;
import com.cart4j.model.security.dto.v1.ClientDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.provider.ClientAlreadyExistsException;

import java.util.List;

public interface ClientService {
    Page<ClientDto> getClients(Pageable pageable, String searchKey);
    ClientDto getClient(Long id);
    ClientDto getClientByClientUniqueId(String clientUniqueId);
    ClientDto addClient(ClientDto client) throws ClientAlreadyExistsException;
    ClientDto setScopes(List<Long> scopeIds, Long clientId);
    ClientDto addScope(Long scopeId, Long clientId) throws ScopeNotFoundException, ClientNotFoundException;
    ClientDto editClient(Long id, ClientDto client) throws ClientAlreadyExistsException;
    void deleteClient(Long id);
}
