package com.cart4j.auth.service.impl;

import com.cart4j.auth.Cart4jAuthApp;
import com.cart4j.auth.dto.ResourceDto;
import com.cart4j.auth.exception.ResourceAlreadyExistingException;
import com.cart4j.auth.service.ResourceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Cart4jAuthApp.class})
@ActiveProfiles("test")
public class ResourceServiceImplTest {
    @Test
    public void test_persistFunction() throws ResourceAlreadyExistingException {
        ResourceDto resource = ResourceDto.builder()
                .resourceUniqueId("TEST_1")
                .description("TEST Resource")
                .build();

        resource = resourceService.addResource(resource);
        assertThat(resource.getId()).isNotNull();
        assertThat(resource.getResourceUniqueId()).isEqualTo("TEST_1");
        assertThat(resource.getDescription()).isEqualTo("TEST Resource");
        ResourceDto updating = ResourceDto.builder()
                .resourceUniqueId("TEST_2")
                .description("TEST Resource2")
                .build();
        updating = resourceService.editResource(resource.getId(), updating);

        assertThat(updating.getId()).isEqualTo(resource.getId());
        assertThat(updating.getResourceUniqueId()).isEqualTo("TEST_2");
        assertThat(updating.getDescription()).isEqualTo("TEST Resource2");

        resourceService.deleteResource(updating.getId());
        assertThat(resourceService.getResource(updating.getId())).isNull();

    }

    @Autowired
    private ResourceService resourceService;
}
