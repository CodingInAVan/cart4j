package com.cart4j.auth.service.impl;

import com.cart4j.auth.entity.AccessToken;
import com.cart4j.auth.entity.Client;
import com.cart4j.auth.provider.AuthClientDetails;
import com.cart4j.auth.repository.AccessTokenRepository;
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

@Transactional
public class ClientDetailsServiceImpl implements ClientDetailsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientDetailsServiceImpl.class);

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
                                new HashSet<>() : Arrays.asList(client.getGrantTypes().split(",")).stream().collect(Collectors.toSet()))
                .clientSecret(client.getClientSecret())
                .registeredRedirectUri(
                        CollectionUtils.isEmpty(client.getRedirectUris()) ?
                                new HashSet<>() : client.getResources().stream().map(r -> r.getResourceUniqueId()).collect(Collectors.toSet()))
                .resourceIds(
                        CollectionUtils.isEmpty(client.getResources()) ?
                                new HashSet<>() : client.getResources().stream().map(r -> r.getResourceUniqueId()).collect(Collectors.toSet()))
                .scope(
                        CollectionUtils.isEmpty(client.getScopes()) ?
                                new HashSet<>() : client.getScopes().stream().map(s -> s.getScope()).collect(Collectors.toSet()))
                .build();
    }

    @Autowired
    private ClientRepository clientRepository;
}
