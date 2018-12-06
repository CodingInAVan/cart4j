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
import org.springframework.security.oauth2.provider.ClientAlreadyExistsException;
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
    public ClientDto getClient(Long id) {
        if(!clientRepository.existsById(id)) {
            return null;
        }
        return ClientDto.from(clientRepository.getOne(id));
    }

    @Override
    public ClientDto addClient(ClientDto client) throws ClientAlreadyExistsException {
        if(clientRepository.existsByClientUniqueId(client.getClientUniqueId())) {
            throw new ClientAlreadyExistsException("ClientId is already existing");
        }
        Client newClient = Client.builder()
                .clientSecret(passwordEncoder.encode(client.getClientSecret()))
                .clientUniqueId(client.getClientUniqueId())
                .grantTypes(client.getGrantTypes())
                .build();

        return ClientDto.from(clientRepository.save(newClient));
    }

    @Override
    public ClientDto editClient(Long id, ClientDto client) {
        Client modifyingClient = clientRepository.getOne(id);
        modifyingClient.setClientSecret(passwordEncoder.encode(client.getClientSecret()));
        modifyingClient.setGrantTypes(client.getGrantTypes());

        return ClientDto.from(clientRepository.save(modifyingClient));
    }

    @Override
    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
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
