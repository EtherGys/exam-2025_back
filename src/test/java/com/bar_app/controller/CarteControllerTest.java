package com.bar_app.controller;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.bar_app.config.JwtAuthenticationInterceptor;
import com.bar_app.controller.CarteController;
import com.bar_app.service.CarteService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarteController.class)
public class CarteControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CarteService carteService;
    @MockBean
    private JwtAuthenticationInterceptor jwtAuthenticationInterceptor;

    @Test
    public void testGetAllCartes() throws Exception {
        Mockito.when(carteService.getAllCartes()).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/api/cartes"))
                .andExpect(status().isOk());
    }
} 