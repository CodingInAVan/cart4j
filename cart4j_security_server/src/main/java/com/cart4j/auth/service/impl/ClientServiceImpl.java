package com.cart4j.auth.service.impl;

import com.cart4j.auth.dto.ClientDto;
import com.cart4j.auth.entity.Client;
import com.cart4j.auth.repository.ClientRepository;
import com.cart4j.auth.service.ClientService;
import com.cart4j.common.dto.PageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {
    @Override
    public Page<ClientDto> getClients(Pageable pageable, String searchKey) {
        Specification<Client> spec = null;
        if(!StringUtils.isEmpty(searchKey)) {
            spec = ClientSpec.searchKey(searchKey);
        }
        return clientRepository.findAll(spec, pageable).map(ClientDto::from);
    }

    @Override
    public ClientDto addClient(ClientDto client) {
        Client newClient = Client.builder()
                .clientSecret(passwordEncoder.encode(client.getClientSecret()))
                .clientUniqueId(client.getClientUniqueId())
                .grantTypes(client.getGrantTypes())
                .build();

        return ClientDto.from(clientRepository.save(newClient));
    }

    static class ClientSpec {
        public static Specification<Client> searchKey(String searchKey) {
            return (root, query, builder) -> builder.like(root.get("client_unique_id"), "%" + searchKey + "%");
        }
    }

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
}
