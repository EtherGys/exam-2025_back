package com.bar_app.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.bar_app.config.JwtAuthenticationInterceptor;
import com.bar_app.controller.TestController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TestController.class)
public class TestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtAuthenticationInterceptor jwtAuthenticationInterceptor;

    @Test
    public void testPingEndpoint() throws Exception {
        mockMvc.perform(get("/api/test/health"))
                .andExpect(status().isOk());
    }

    @Test
    public void testHealthEndpoint() throws Exception {
        mockMvc.perform(get("/api/test/health"))
                .andExpect(status().isOk());
    }
} 