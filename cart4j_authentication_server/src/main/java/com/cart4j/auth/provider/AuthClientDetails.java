package com.cart4j.auth.provider;

import com.cart4j.auth.entity.Client;
import com.cart4j.auth.entity.AccessToken;
import lombok.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Builder
public class AuthClientDetails implements ClientDetails {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthClientDetails.class);

    private static final long serialVersionUID = -6055056191220737438L;

    private Set<String> resourceIds;

    private String clientSecret;

    private Set<String> scope;

    private Set<String> authorizedGrantTypes;

    private Set<String> registeredRedirectUri;

    private Collection<GrantedAuthority> authorities;

    private String clientUniqueId;

    @Override
    public String getClientId() {
        return clientUniqueId;
    }

    @Override
    public Set<String> getResourceIds() {
        return resourceIds;
    }

    @Override
    public boolean isSecretRequired() {
        return true;
    }

    @Override
    public String getClientSecret() {
        return clientSecret;
    }

    @Override
    public boolean isScoped() {
        return false;
    }

    @Override
    public Set<String> getScope() {
        return scope;
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        return authorizedGrantTypes;
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return registeredRedirectUri;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return null;
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
