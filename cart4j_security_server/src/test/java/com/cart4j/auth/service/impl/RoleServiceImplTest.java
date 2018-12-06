package com.cart4j.auth.service.impl;

import com.cart4j.auth.Cart4jAuthApp;
import com.cart4j.auth.dto.RoleDto;
import com.cart4j.auth.exception.RoleAlreadyExistingException;
import com.cart4j.auth.repository.RoleRepository;
import com.cart4j.auth.service.RoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Cart4jAuthApp.class})
@ActiveProfiles("test")
public class RoleServiceImplTest {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleService roleService;

    @Test
    public void test_addFunction() throws RoleAlreadyExistingException {
        roleRepository.deleteAll();
        RoleDto roleDto = RoleDto.builder()
                .role("TEST")
                .description("Role for testing")
                .build();

        roleDto = roleService.addRole(roleDto);

        assertThat(roleService.getRole(roleDto.getId())).isNotNull();
    }

    @Test(expected = RoleAlreadyExistingException.class)
    public void test_roleAlreadyExistingException() throws RoleAlreadyExistingException {
        roleRepository.deleteAll();
        RoleDto roleDto = RoleDto.builder()
                .role("TEST")
                .description("Role for testing")
                .build();
        roleService.addRole(roleDto);
        roleService.addRole(roleDto);
    }

    @Test
    public void test_pageTest() throws RoleAlreadyExistingException {
        roleRepository.deleteAll();
        RoleDto roleDto = RoleDto.builder()
                .role("TEST1")
                .description("Role for testing")
                .build();
        RoleDto roleDto2 = RoleDto.builder()
                .role("TEST2")
                .description("Role for testing")
                .build();

        roleService.addRole(roleDto);
        roleService.addRole(roleDto2);

        Page<RoleDto> rolespage = roleService.getRoles(PageRequest.of(0, 20), "");
        assertThat(rolespage.getTotalElements()).isEqualTo(2);
        assertThat(rolespage.getTotalPages()).isEqualTo(1);
        assertThat(rolespage.getPageable().getOffset()).isEqualTo(0);
        assertThat(rolespage.getContent().size()).isEqualTo(2);
    }

    @Test
    public void test_updateFunction() throws RoleAlreadyExistingException {
        roleRepository.deleteAll();
        RoleDto roleDto = RoleDto.builder()
                .role("TEST")
                .description("Role for testing")
                .build();
        roleDto = roleService.addRole(roleDto);

        RoleDto updatingDto = RoleDto.builder()
                .id(roleDto.getId())
                .role("TEST UPDATED")
                .description("Role updating for testing")
                .build();

        roleDto = roleService.editRole(roleDto.getId(), updatingDto);

        assertThat(roleDto.getDescription()).isEqualTo(updatingDto.getDescription());
        assertThat(roleDto.getRole()).isEqualTo(updatingDto.getRole());
    }

    @Test
    public void test_deleteFunction() throws RoleAlreadyExistingException {
        roleRepository.deleteAll();
        RoleDto roleDto = RoleDto.builder()
                .role("TEST")
                .description("Role for testing")
                .build();
        roleDto = roleService.addRole(roleDto);

        roleService.deleteRole(roleDto.getId());

        assertThat(roleService.getRole(roleDto.getId())).isNull();
    }

}
