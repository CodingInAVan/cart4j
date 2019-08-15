package com.cart4j.auth.service.impl;

import com.cart4j.auth.entity.Role;
import com.cart4j.auth.entity.User;
import com.cart4j.auth.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class UserDetailsServiceTest {

    @org.springframework.boot.test.context.TestConfiguration
    public static class TestConfiguration {
        private UserRepository userRepository;
        @Autowired
        public TestConfiguration(UserRepository userRepository) {
            this.userRepository = userRepository;
        }
        @Bean
        public UserDetailsService userDetailsService() {
            return new UserDetailsServiceImpl(userRepository);
        }
    }

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @Before
    public void setUp() {
        Role role = Role.builder()
                .role("basic")
                .build();
        User user = User.builder()
                .email("test@cart4j.com")
                .username("test")
                .roles(Arrays.asList(role))
                .build();
        Mockito.when(userRepository.getByUsernameOrEmail("test@cart4j.com")).thenReturn(user);
        Mockito.when(userRepository.getByUsernameOrEmail("test")).thenReturn(user);
    }
    @Test
    public void getByUsername() {
        UserDetails user = userDetailsService.loadUserByUsername("test");
        assertThat(user.getUsername()).isEqualTo("test");
    }
    @Test
    public void getByEmail() {
        UserDetails user = userDetailsService.loadUserByUsername("test@cart4j.com");
        assertThat(user.getUsername()).isEqualTo("test");
    }
}
