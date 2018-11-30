package com.cart4j.auth.service.impl;

import com.cart4j.auth.core.UserPrincipal;
import com.cart4j.auth.entity.User;
import com.cart4j.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.getByUsernameOrEmail(s);
        return UserPrincipal.from(user);
    }

    @Autowired private UserRepository userRepository;
}
