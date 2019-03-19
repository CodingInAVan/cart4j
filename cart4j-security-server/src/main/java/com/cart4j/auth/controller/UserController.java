package com.cart4j.auth.controller;

import com.cart4j.auth.exception.UserAlreadyExistingException;
import com.cart4j.auth.service.UserService;
import com.cart4j.model.common.ErrorResponse;
import com.cart4j.model.common.PageDto;
import com.cart4j.model.security.dto.v1.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/auth/users")
public class UserController {
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    PageDto<UserDto> getUsers(Pageable pageable, @RequestParam(name="searchKey", required = false) String searchKey) {
        Page<UserDto> users = userService.getUsers(pageable, searchKey);
        return PageDto.<UserDto>builder().totalRecords(users.getTotalElements()).totalPage(users.getTotalPages())
                .offset(users.getPageable().getOffset()).list(users.getContent()).limit(users.getSize()).build();
    }

    @PostMapping
    UserDto addUser(Principal principal, @RequestBody UserDto user) throws UserAlreadyExistingException {
        UserDto newUser = userService.addUser(user);
        LOGGER.info("{} added a user[{}]", principal.getName(), user.getId());
        return newUser;
    }

    @PutMapping("/{id}")
    UserDto eidtUser(Principal principal, @PathVariable Long id, @RequestBody UserDto user) throws UserAlreadyExistingException {
        UserDto updated = userService.editUser(id, user);
        LOGGER.info("{} modified the user[{}]", principal.getName(), updated.getId());
        return updated;
    }

    @DeleteMapping("/{id}")
    void deleteUser(Principal principal, @PathVariable Long id) {
        userService.deleteUser(id);
        LOGGER.info("{} deleted the user[{}]", principal.getName(), id);
    }

    @ExceptionHandler({UserAlreadyExistingException.class})
    ResponseEntity<ErrorResponse> handleUserException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.builder().errorCode(HttpStatus.PRECONDITION_FAILED.value()).message(e.getMessage()).build());
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);

    private final UserService userService;
}
