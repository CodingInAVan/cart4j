package com.cart4j.auth.service.impl;

import com.cart4j.auth.entity.AccessToken;
import com.cart4j.auth.entity.Client;
import com.cart4j.auth.provider.AuthClientDetails;
import com.cart4j.auth.repository.AccessTokenRepository;
import com.cart4j.auth.repository.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ClientDetailsServiceImpl implements ClientDetailsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientDetailsServiceImpl.class);

    @Override
    public ClientDetails loadClientByClientId(String clientUniqueId) throws ClientRegistrationException {
        Client client = clientRepository.findByClientUniqueId(clientUniqueId);
        AccessToken accessToken = accessTokenRepository.getOne(client.getId());

        return new AuthClientDetails(client, accessToken);
    }

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AccessTokenRepository accessTokenRepository;
}
