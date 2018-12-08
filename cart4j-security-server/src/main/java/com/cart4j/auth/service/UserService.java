package com.cart4j.auth.service;

import com.cart4j.auth.dto.UserDto;
import com.cart4j.auth.exception.UserAlreadyExistingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    Page<UserDto> getUsers(Pageable pageable, String searchKey);
    UserDto addUser(UserDto user) throws UserAlreadyExistingException;
    UserDto setRole(List<Long> roleIds, Long userId);
    UserDto editUser(Long id, UserDto user) throws UserAlreadyExistingException;
    void deleteUser(Long id);
}
