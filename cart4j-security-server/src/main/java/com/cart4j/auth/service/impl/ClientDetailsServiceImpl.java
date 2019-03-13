package com.cart4j.auth.service.impl;

import com.cart4j.auth.entity.Client;
import com.cart4j.auth.entity.Resource;
import com.cart4j.auth.entity.Scope;
import com.cart4j.auth.provider.AuthClientDetails;
import com.cart4j.auth.repository.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClientDetailsServiceImpl implements ClientDetailsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientDetailsServiceImpl.class);

    @Autowired
    public ClientDetailsServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public ClientDetails loadClientByClientId(String clientUniqueId) throws ClientRegistrationException {
        Client client = clientRepository.findByClientUniqueId(clientUniqueId);
        return AuthClientDetails.builder()
                .clientUniqueId(client.getClientUniqueId())
                .authorities(
                        CollectionUtils.isEmpty(client.getRoles()) ?
                                new ArrayList<>() : client.getRoles().stream().map(r -> new SimpleGrantedAuthority(r.getRole())).collect(Collectors.toSet()))
                .authorizedGrantTypes(
                        StringUtils.isEmpty(client.getGrantTypes()) ?
                                new HashSet<>() : new HashSet<>(Arrays.asList(client.getGrantTypes().split(","))))
                .clientSecret(client.getClientSecret())
                .registeredRedirectUri(
                        CollectionUtils.isEmpty(client.getRedirectUris()) ?
                                new HashSet<>() : client.getResources().stream().map(Resource::getResourceUniqueId).collect(Collectors.toSet()))
                .resourceIds(
                        CollectionUtils.isEmpty(client.getResources()) ?
                                new HashSet<>() : client.getResources().stream().map(Resource::getResourceUniqueId).collect(Collectors.toSet()))
                .scope(
                        CollectionUtils.isEmpty(client.getScopes()) ?
                                new HashSet<>() : client.getScopes().stream().map(Scope::getScope).collect(Collectors.toSet()))
                .build();
    }

    private final ClientRepository clientRepository;
}
