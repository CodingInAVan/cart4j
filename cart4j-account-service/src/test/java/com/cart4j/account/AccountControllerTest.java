package com.cart4j.account;

import com.cart4j.account.config.WithMockOAuth2Scope;
import com.cart4j.account.controller.AccountController;
import com.cart4j.account.dto.v1.AccountDtoV1;
import com.cart4j.account.entity.AccountStatus;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AccountControllerTest {
    private MockMvc mockMvc;

    @Autowired
    AccountController accountController;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
    }

    @Test
    @WithMockOAuth2Scope(scope = "ACCOUNT_API_WRITE")
    public void test_addingFunction() throws Exception {
        AccountDtoV1 account = AccountDtoV1.builder()
                .accountName("test-account")
                .accountUniqueId("account-service")
                .description("test")
                .status(AccountStatus.ACTIVATED.name())
                .build();
        ObjectMapper mapper = new ObjectMapper();
        String request = mapper.writeValueAsString(account);
        MvcResult result = mockMvc.perform(
                post("/api/accounts/v1")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        AccountDtoV1 added = mapper.readValue(content, AccountDtoV1.class);
        assertThat(added.getAccountName()).isEqualTo("test-account");
        assertThat(added.getAccountUniqueId()).isEqualTo("account-service");
        assertThat(added.getDescription()).isEqualTo("test");
        assertThat(added.getStatus()).isEqualTo(AccountStatus.ACTIVATED.name());
    }

    @Test
    @WithMockOAuth2Scope(scope = "ACCOUNT_API_WRITE")
    public void test_modifyingFunction() throws Exception {
        AccountDtoV1 account = AccountDtoV1.builder()
                .accountName("test-account")
                .accountUniqueId("account-service")
                .description("test")
                .status(AccountStatus.ACTIVATED.name())
                .build();
        ObjectMapper mapper = new ObjectMapper();
        String request = mapper.writeValueAsString(account);
        MvcResult result = mockMvc.perform(
                post("/api/accounts/v1")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        AccountDtoV1 added = mapper.readValue(content, AccountDtoV1.class);
        AccountDtoV1 mod = AccountDtoV1.builder()
                .accountName("test-account-1")
                .accountUniqueId("account-service-1")
                .description("test-1")
                .build();
        request = mapper.writeValueAsString(mod);
        result = mockMvc.perform(
                put("/api/accounts/v1/" + added.getId())
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn();

        AccountDtoV1 modified = mapper.readValue(result.getResponse().getContentAsString(), AccountDtoV1.class);

        assertThat(modified.getAccountName()).isEqualTo("test-account-1");
        assertThat(modified.getAccountUniqueId()).isEqualTo("account-service"); // Not allow to change
        assertThat(modified.getDescription()).isEqualTo("test-1");
        assertThat(modified.getStatus()).isEqualTo(AccountStatus.ACTIVATED.name());
    }
}
