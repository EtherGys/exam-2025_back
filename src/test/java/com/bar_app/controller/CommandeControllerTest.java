package com.bar_app.controller;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.bar_app.config.JwtAuthenticationInterceptor;
import com.bar_app.controller.CommandeController;
import com.bar_app.service.CommandeService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommandeController.class)
public class CommandeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CommandeService commandeService;
    @MockBean
    private JwtAuthenticationInterceptor jwtAuthenticationInterceptor;

    @Test
    public void testGetAllCommandes() throws Exception {
        Mockito.when(commandeService.getAllCommandes()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/commandes"))
                .andExpect(status().isOk());
    }
} 