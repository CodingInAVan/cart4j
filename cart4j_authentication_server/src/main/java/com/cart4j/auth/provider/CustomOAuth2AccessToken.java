package com.cart4j.auth.provider;

import com.cart4j.auth.entity.AccessToken;
import com.cart4j.auth.entity.Scope;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomOAuth2AccessToken implements OAuth2AccessToken, Serializable {

    private static final long serialVersionUID = -4734460091698115595L;

    private AccessToken accessToken;
    private Map<String, Object> additionalInformation;
    private Set<String> scopes;
    private Date expiration;

    public CustomOAuth2AccessToken(AccessToken accessToken, List<Scope> scopes, Map<String, Object> additionalInformation) {
        this.accessToken = accessToken;
        this.expiration = new Date(accessToken.getExpirationDate().getTime());
        if(!CollectionUtils.isEmpty(scopes)) {
            this.scopes = scopes.stream().map(s -> s.getScope()).collect(Collectors.toSet());
        }
        this.additionalInformation = additionalInformation;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return additionalInformation;
    }

    @Override
    public Set<String> getScope() {
        return scopes;
    }

    @Override
    public OAuth2RefreshToken getRefreshToken() {
        return accessToken.getRefreshToken();
    }

    @Override
    public String getTokenType() {
        return OAuth2AccessToken.BEARER_TYPE;
    }

    @Override
    public boolean isExpired() {
        return expiration != null && expiration.before(new Date());
    }

    @Override
    public Date getExpiration() {
        return expiration;
    }

    @Override
    public int getExpiresIn() {
        return expiration != null ? Long.valueOf((expiration.getTime() - System.currentTimeMillis()) / 1000L).intValue()
                : 0;
    }

    @Override
    public String getValue() {
        return new String(accessToken.getTokenValue());
    }
}
