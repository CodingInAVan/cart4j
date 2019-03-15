package com.cart4j.auth.service.impl;

import com.cart4j.auth.entity.Role;
import com.cart4j.auth.entity.User;
import com.cart4j.auth.exception.UserAlreadyExistingException;
import com.cart4j.auth.repository.RoleRepository;
import com.cart4j.auth.repository.UserRepository;
import com.cart4j.auth.service.UserService;
import com.cart4j.model.security.dto.v1.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public Page<UserDto> getUsers(Pageable pageable, String searchKey) {
        Specification<User> spec = UserSpec.isActivate();
        if(!StringUtils.isEmpty(searchKey)) {
            spec = spec.and(UserSpec.search(searchKey));
        }
        return userRepository.findAll(spec, pageable).map(User::toDto);
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
        return userRepository.save(newUser).toDto();
    }

    @Override
    public UserDto setRole(List<Long> roleIds, Long userId) {
        User user = userRepository.getOne(userId);
        Set<Long> addingIds = new HashSet<>(roleIds);
        if(!CollectionUtils.isEmpty(user.getRoles())) {
            for(Role role : user.getRoles()) {
                if(!addingIds.contains(role.getId())) {
                    roleRepository.deleteById(role.getId());
                } else {
                    // Remove if it already exists
                    addingIds.remove(role.getId());
                }
            }
        }

        if(!CollectionUtils.isEmpty(addingIds)) {
            user.setRoles(addingIds.stream().map(roleRepository::getOne).collect(Collectors.toList()));
        }
        return userRepository.save(user).toDto();
    }

    @Override
    public UserDto editUser(Long id, UserDto user) throws UserAlreadyExistingException {
        if(userRepository.existsByEmailAndNotUsername(user.getEmail(), user.getUsername())) {
            throw new UserAlreadyExistingException("Input Email[" + user.getEmail() + "] is already in use.");
        }
        User updatingUser = userRepository.getOne(id);
        updatingUser.setEmail(user.getEmail());
        updatingUser.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(updatingUser).toDto();
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

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;
}
