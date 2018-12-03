package com.cart4j.auth.provider;

import com.cart4j.auth.entity.AccessToken;
import com.cart4j.auth.entity.Client;
import com.cart4j.auth.entity.Scope;
import com.cart4j.auth.repository.AccessTokenRepository;
import com.cart4j.auth.repository.ClientRepository;
import com.cart4j.auth.repository.RefreshTokenRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.util.SerializationUtils;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.Access;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class AuthTokenStoreTest {
    @TestConfiguration
    static class AuthTokenStoreConfiguration {
        @Bean
        public TokenStore tokenStore() {
            return new AuthTokenStore();
        }
    }

    @MockBean
    private ClientRepository clientRepository;
    @MockBean
    private AccessTokenRepository accessTokenRepository;
    @MockBean
    private RefreshTokenRepository refreshTokenRepository;
    @MockBean
    private AuthenticationKeyGenerator authenticationKeyGenerator;

    @Autowired
    private TokenStore tokenStore;

    @Before
    public void setUp() {
        String authentication = "test_authentication";
        OAuth2AccessToken token = new DefaultOAuth2AccessToken("test_token");

        AccessToken accessToken = AccessToken.builder()
                .tokenValue(SerializationUtils.serialize(token))
                .authentication(SerializationUtils.serialize(authentication))
                . build();
        Scope scope = Scope.builder().scope("SCOPE_1")
                .build();
        Client client = Client.builder()
                .clientUniqueId("client_1")
                .grantTypes("client_credentials")
                .scopes(Arrays.asList(scope))
                .build();
        Mockito.when(accessTokenRepository.findByAuthenticationKey("test_key")).thenReturn(accessToken);
        Mockito.when(accessTokenRepository.findByClientUniqueIdAndUsername("client_1", "user_1")).thenReturn(Arrays.asList(accessToken));
        Mockito.when(accessTokenRepository.getByCilentUniqueId("client_1")).thenReturn(Arrays.asList(accessToken));
        Mockito.when(clientRepository.findByClientUniqueId("client_1")).thenReturn(client);
    }

    @Test
    public void findTokensByClientIdTest() {
        Collection<OAuth2AccessToken> accessTokenList = tokenStore.findTokensByClientId("client_1");
        assertThat(accessTokenList.size()).isEqualTo(1);
        assertThat(accessTokenList.iterator().next().getValue()).isEqualTo("test_token");

    }

    @Test
    public void findTokensByClientIdAndUserNameTest() {
        Collection<OAuth2AccessToken> accessTokenList = tokenStore.findTokensByClientIdAndUserName("client_1", "user_1");
        assertThat(accessTokenList.size()).isEqualTo(1);
        assertThat(accessTokenList.iterator().next().getValue()).isEqualTo("test_token");
    }
}
