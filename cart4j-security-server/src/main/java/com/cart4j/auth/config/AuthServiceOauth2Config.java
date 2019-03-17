package com.cart4j.auth.config;

import com.cart4j.auth.provider.AuthTokenStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.TokenStoreUserApprovalHandler;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthServiceOauth2Config extends AuthorizationServerConfigurerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceOauth2Config.class);

    @Autowired
    public AuthServiceOauth2Config(ClientDetailsService clientDetailsServiceImpl, AuthenticationManager authenticationManagerBean) {
        this.clientDetailsServiceImpl = clientDetailsServiceImpl;
        this.authenticationManagerBean = authenticationManagerBean;
    }

    private ClientDetailsService clientDetailsServiceImpl;

    private final AuthenticationManager authenticationManagerBean;

    @Bean
    public TokenStore authTokenStore() {
        return new AuthTokenStore();
    }


    @Bean
    public UserApprovalHandler userApprovalHandler() {
        TokenStoreUserApprovalHandler handler = new TokenStoreUserApprovalHandler();
        handler.setTokenStore(authTokenStore());
        handler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsServiceImpl));
        handler.setClientDetailsService(clientDetailsServiceImpl);
        return handler;
    }

    @Bean
    public AuthenticationKeyGenerator authenticationKeyGenerator() {
        return new DefaultAuthenticationKeyGenerator();
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(authTokenStore());
        defaultTokenServices.setClientDetailsService(clientDetailsServiceImpl);
        endpoints.tokenServices(defaultTokenServices).authenticationManager(authenticationManagerBean);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsServiceImpl);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()").allowFormAuthenticationForClients();
    }
}
