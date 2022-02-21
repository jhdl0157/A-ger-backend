package com.ireland.ager.account.controller;

import com.ireland.ager.account.service.AccountServiceImpl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountServiceImpl accountService;

    @Test
    @DisplayName(value = "로그인 Url 테스트")
    public void loginUrl() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/account/login-url"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}