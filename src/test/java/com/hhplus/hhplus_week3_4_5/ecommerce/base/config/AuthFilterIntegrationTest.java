package com.hhplus.hhplus_week3_4_5.ecommerce.base.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc
@SpringBootTest
class AuthFilterIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Filter token 조회 성공")
    void authorization_success() throws Exception {
        String authToken = "Bearer your_mock_jwt_token_here";

        mockMvc.perform(MockMvcRequestBuilders.get("/url")
                        .header("Authorization", authToken))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Filter token 실패 - token이 없을 때")
    void authorization_no_token_fail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/url"))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @DisplayName("Filter token 실패 - token이 유효성 검사에서 실패했을 때")
    void authorization_valid_fail() throws Exception {
        String invalidAuthToken = "Bearer ";

        mockMvc.perform(MockMvcRequestBuilders.get("/url")
                        .header("Authorization", invalidAuthToken))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }
}
