package com.cart4j.auth.service;

import com.cart4j.auth.dto.ClientDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.provider.ClientAlreadyExistsException;

public interface ClientService {
    Page<ClientDto> getClients(Pageable pageable, String searchKey);
    ClientDto getClient(Long id);
    ClientDto addClient(ClientDto client) throws ClientAlreadyExistsException;
    ClientDto editClient(Long id, ClientDto client) throws ClientAlreadyExistsException;
    void deleteClient(Long id);
}
