package com.cart4j.auth.service.impl;

import com.cart4j.auth.dto.UserDto;
import com.cart4j.auth.entity.User;
import com.cart4j.auth.exception.UserAlreadyExistingException;
import com.cart4j.auth.repository.UserRepository;
import com.cart4j.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.Date;

@Transactional
@Service
public class UserServiceImpl implements UserService {
    @Override
    public Page<UserDto> getUsers(Pageable pageable, String searchKey) {
        Specification<User> spec = UserSpec.isActivate();
        if(!StringUtils.isEmpty(searchKey)) {
            spec = spec.and(UserSpec.search(searchKey));
        }
        return userRepository.findAll(spec, pageable).map(UserDto::from);
    }

    @Override
    public UserDto addUser(UserDto user) throws UserAlreadyExistingException {
        if(userRepository.existsByUsername(user.getUsername())) {
           throw new UserAlreadyExistingException("Input ID[" + user.getUsername() + "] is already in use.");
        }
        User newUser = User.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .activated(Boolean.TRUE)
                .createdAt(new Date())
                .password(passwordEncoder.encode(user.getPassword()))
                .build();
        return UserDto.from(userRepository.save(newUser));
    }

    @Override
    public UserDto editUser(Long id, UserDto user) throws UserAlreadyExistingException {
        if(userRepository.existsByEmailAndNotUsername(user.getEmail(), user.getUsername())) {
            throw new UserAlreadyExistingException("Input Email[" + user.getEmail() + "] is already in use.");
        }
        User updatingUser = userRepository.getOne(id);
        updatingUser.setEmail(user.getEmail());
        updatingUser.setPassword(passwordEncoder.encode(user.getPassword()));

        return UserDto.from(userRepository.save(updatingUser));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    static class UserSpec {
        public static Specification<User> isActivate() {
            return (root, query, builder) -> builder.equal(root.get("activated"), true);
        }

        public static Specification<User> search(String searchKey) {
            return (root, query, builder) -> {
                String likeSearch = "%" + searchKey + "%";
                return builder.or(builder.like(root.get("username"), likeSearch), builder.like(root.get("email"), likeSearch));
            };
        }
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;
}
