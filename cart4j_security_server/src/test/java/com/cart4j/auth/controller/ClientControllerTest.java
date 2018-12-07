package com.cart4j.auth.controller;

import com.cart4j.auth.Cart4jAuthApp;
import com.cart4j.auth.dto.ClientDto;
import com.cart4j.common.dto.PageDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.awt.print.PrinterIOException;
import java.security.Principal;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Cart4jAuthApp.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ClientControllerTest {
    @LocalServerPort
    int randomServerPort;

    @Before
    public void setUp() {
        System.out.println(randomServerPort);
    }

    @Test
    public void test_crunFunctions() {

    }

}
