package com.cart4j.auth.service;

import com.cart4j.auth.dto.ClientDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClientService {
    Page<ClientDto> getClients(Pageable pageable, String searchKey);
    ClientDto addClient(ClientDto client);
}
