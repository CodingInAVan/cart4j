package com.cart4j.auth.provider;

import com.cart4j.auth.entity.AccessToken;
import com.cart4j.auth.entity.Client;
import com.cart4j.auth.entity.RefreshToken;
import com.cart4j.auth.entity.User;
import com.cart4j.auth.repository.AccessTokenRepository;
import com.cart4j.auth.repository.ClientRepository;
import com.cart4j.auth.repository.RefreshTokenRepository;
import com.cart4j.auth.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.common.util.SerializationUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Transactional
public class AuthTokenStore implements TokenStore {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthTokenStore.class);

    @Override
    public OAuth2Authentication readAuthentication(OAuth2AccessToken oAuth2AccessToken) {

        LOGGER.debug("readAuthentication...");
        return readAuthentication(oAuth2AccessToken.getValue());
    }

    @Override
    public OAuth2Authentication readAuthentication(String token) {
        AccessToken accessToken = accessTokenRepository.findByTokenKey(extractTokenKey(token));
        if(accessToken != null) {
            return SerializationUtils.deserialize(accessToken.getAuthentication());
        }
        return null;
    }

    @Override
    public void storeAccessToken(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        if (readAccessToken(oAuth2AccessToken.getValue()) != null) {
            // Already existing ... remove
            LOGGER.debug("Removing...");
            removeAccessToken(oAuth2AccessToken);
        }

        RefreshToken refreshToken = null;
        if(oAuth2AccessToken.getRefreshToken() != null) {
            Page<RefreshToken> refreshTokens = refreshTokenRepository.getByRefreshTokenKey(extractTokenKey(oAuth2AccessToken.getRefreshToken().getValue()), PageRequest.of(0, 1));

            if(CollectionUtils.isEmpty(refreshTokens.getContent())) {
                refreshToken = RefreshToken.builder()
                        .refreshTokenKey(extractTokenKey(oAuth2AccessToken.getRefreshToken().getValue()))
                        .refreshTokenValue(SerializationUtils.serialize(oAuth2AccessToken.getRefreshToken()))
                        .authenticationValue(SerializationUtils.serialize(oAuth2Authentication)).build();

                refreshToken = refreshTokenRepository.save(refreshToken);
            }
        }

        String clientUniqueId = oAuth2Authentication.getOAuth2Request().getClientId();

        Client client = clientRepository.findByClientUniqueId(clientUniqueId);

        Calendar cal = Calendar.getInstance();
        cal.setTime(new java.util.Date());
        cal.add(Calendar.DATE, 1); // 1 day

        // Assign Client's Roles
        Map<String, Set<String>> additionalInfo = new HashMap<>();
        additionalInfo.put("roles", client.getRoles().stream().map(r -> r.getRole()).collect(Collectors.toSet()));

        User user = null;
        if(!oAuth2Authentication.isClientOnly()) {
            user = userRepository.getByUsernameOrEmail(oAuth2Authentication.getName());
            List<String> roles = user.getRoles().stream().map(r -> r.getRole()).collect(Collectors.toList());
            Set<String> roleInfo = additionalInfo.get("roles");
            roleInfo.addAll(roles);
            ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(Collections.unmodifiableMap(additionalInfo));
        }

        AccessToken accessToken = AccessToken.builder()
                .tokenKey(extractTokenKey(oAuth2AccessToken.getValue()))
                .tokenValue(SerializationUtils.serialize(oAuth2AccessToken))
                .refreshToken(refreshToken)
                .authenticationKey(authenticationKeyGenerator.extractKey(oAuth2Authentication))
                .authentication(SerializationUtils.serialize(oAuth2Authentication))
                .client(client)
                .user(user)
                .createdAt(new Date())
                .expirationDate(new Timestamp(cal.getTime().getTime()))
                .build();
        accessTokenRepository.save(accessToken);
        LOGGER.debug("Token Stored");
    }

    @Override
    public OAuth2AccessToken readAccessToken(String tokenValue) {
        AccessToken accessToken = accessTokenRepository.findByTokenKey(extractTokenKey(tokenValue));
        OAuth2AccessToken oAuth2AccessToken = null;
        if(accessToken != null) {
            oAuth2AccessToken = SerializationUtils.deserialize(accessToken.getTokenValue());
        }
        return oAuth2AccessToken;
    }

    @Override
    public void removeAccessToken(OAuth2AccessToken oAuth2AccessToken) {
        LOGGER.debug("removeAccessToken:" + extractTokenKey(oAuth2AccessToken.getValue()));
        accessTokenRepository.deleteByTokenKey(extractTokenKey(oAuth2AccessToken.getValue()));
    }

    @Override
    public void storeRefreshToken(OAuth2RefreshToken oAuth2RefreshToken, OAuth2Authentication oAuth2Authentication) {
        RefreshToken refreshToken = RefreshToken.builder()
                .authenticationValue(SerializationUtils.serialize(oAuth2Authentication))
                .refreshTokenKey(extractTokenKey(oAuth2RefreshToken.getValue()))
                .refreshTokenValue(SerializationUtils.serialize(oAuth2RefreshToken)).build();

        refreshTokenRepository.save(refreshToken);
    }

    @Override
    public OAuth2RefreshToken readRefreshToken(String tokenValue) {
        Page<RefreshToken> refreshTokens = refreshTokenRepository.getByRefreshTokenKey(extractTokenKey(tokenValue),
                PageRequest.of(0, 1));
        if (!CollectionUtils.isEmpty(refreshTokens.getContent())) {
            RefreshToken refreshToken = refreshTokens.getContent().get(0);
            return SerializationUtils.deserialize(refreshToken.getRefreshTokenValue());
        }
        return null;
    }

    @Override
    public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken oAuth2RefreshToken) {
        Page<RefreshToken> authRefreshTokens = refreshTokenRepository
                .getByRefreshTokenKey(extractTokenKey(oAuth2RefreshToken.getValue()), PageRequest.of(0,1));
        if (!CollectionUtils.isEmpty(authRefreshTokens.getContent())) {
            RefreshToken refreshToken = authRefreshTokens.getContent().get(0);
            return SerializationUtils.deserialize(refreshToken.getAuthenticationValue());
        }
        return null;
    }

    @Override
    public void removeRefreshToken(OAuth2RefreshToken oAuth2RefreshToken) {
        refreshTokenRepository.deleteByRefreshTokenKey(extractTokenKey(oAuth2RefreshToken.getValue()));
    }

    @Override
    public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken oAuth2RefreshToken) {
        AccessToken accessToken = accessTokenRepository.getOneByRefreshTokenKey(extractTokenKey(oAuth2RefreshToken.getValue()));
        accessTokenRepository.delete(accessToken);
    }

    @Override
    public OAuth2AccessToken getAccessToken(OAuth2Authentication oAuth2Authentication) {
        String key = authenticationKeyGenerator.extractKey(oAuth2Authentication);
        LOGGER.debug("findByAuthenticationKey: " + key);
        AccessToken accessToken = accessTokenRepository.findByAuthenticationKey(key);
        OAuth2AccessToken oAuth2AccessToken = null;
        if(accessToken != null) {
            oAuth2AccessToken = SerializationUtils.deserialize(accessToken.getTokenValue());
            try {
                String authenticationKey = authenticationKeyGenerator.extractKey(readAuthentication(oAuth2AccessToken));
                if(oAuth2AccessToken != null && !key.equals(authenticationKey)) {
                    LOGGER.debug("Remove the old one and generate a new");
                    oAuth2AccessToken = SerializationUtils.deserialize(accessToken.getTokenValue());
                    removeAccessToken(oAuth2AccessToken);

                    storeAccessToken(oAuth2AccessToken, oAuth2Authentication);
                } else {
                    LOGGER.debug("No access token for authentication" + oAuth2Authentication);
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
        }

        return oAuth2AccessToken;
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientUniqueId, String username) {
        List<AccessToken> accessTokens = accessTokenRepository.findByClientUniqueIdAndUsername(clientUniqueId, username);
        return accessTokens.stream().map(a -> (OAuth2AccessToken) SerializationUtils.deserialize(a.getTokenValue())).collect(Collectors.toList());
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientId(String cilentUniqueId) {
        List<AccessToken> accessTokens = accessTokenRepository.getByCilentUniqueId(cilentUniqueId);
        return accessTokens.stream().map(a -> (OAuth2AccessToken) SerializationUtils.deserialize(a.getTokenValue())).collect(Collectors.toList());
    }

    protected String extractTokenKey(String value) {
        if (value == null) {
            return null;
        }
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("MD5 algorithm not available.  Fatal (should be in the JDK).");
        }

        try {
            byte[] bytes = digest.digest(value.getBytes("UTF-8"));
            return String.format("%032x", new BigInteger(1, bytes));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("UTF-8 encoding not available.  Fatal (should be in the JDK).");
        }
    }

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private AccessTokenRepository accessTokenRepository;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private AuthenticationKeyGenerator authenticationKeyGenerator;
    @Autowired
    private UserRepository userRepository;
}
