package com.cart4j.auth.provider;

import com.cart4j.auth.entity.Client;
import com.cart4j.auth.entity.AccessToken;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class AuthClientDetails implements ClientDetails {

    private static final long serialVersionUID = -6055056191220737438L;

    private Client client;
    private Integer accessTokenValiditySeconds;
    private Integer refreshTokenValiditySeconds;

    public AuthClientDetails(Client client, AccessToken accessToken) {
        this.client = client;

        accessTokenValiditySeconds = Long.valueOf(accessToken.getExpirationDate().getTime() - (System.currentTimeMillis() / 1000)).intValue();
    }

    @Override
    public String getClientId() {
        return client.getClientUniqueId();
    }

    @Override
    public Set<String> getResourceIds() {
        if(CollectionUtils.isEmpty(client.getResources())) {
            return new HashSet<>();
        }
        return client.getResources().stream().map(r -> r.getResourceUniqueId()).collect(Collectors.toSet());
    }

    @Override
    public boolean isSecretRequired() {
        return true;
    }

    @Override
    public String getClientSecret() {
        return client.getClientSecret();
    }

    @Override
    public boolean isScoped() {
        return false;
    }

    @Override
    public Set<String> getScope() {
        if(CollectionUtils.isEmpty(client.getScopes())) {
            return new HashSet<>();
        }
        return client.getScopes().stream().map(s -> s.getScope()).collect(Collectors.toSet());
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        if(StringUtils.isEmpty(client.getGrantTypes())) {
            return new HashSet<>();
        }
        return Arrays.asList(client.getGrantTypes().split(",")).stream().collect(Collectors.toSet());
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        if(CollectionUtils.isEmpty(client.getRedirectUris())) {
            return new HashSet<>();
        }
        return client.getRedirectUris().stream().map(r -> r.getRedirectUri()).collect(Collectors.toSet());
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        if(CollectionUtils.isEmpty(client.getRoles())) {
            return new ArrayList<>();
        }
        return client.getRoles().stream().map(r -> new SimpleGrantedAuthority(r.getRole())).collect(Collectors.toSet());
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return accessTokenValiditySeconds;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return 0;
    }

    @Override
    public boolean isAutoApprove(String s) {
        return false;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return new HashMap<>();
    }
}
