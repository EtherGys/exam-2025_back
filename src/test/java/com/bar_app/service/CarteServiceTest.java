package com.bar_app.service;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.bar_app.entity.Carte;
import com.bar_app.entity.Client;
import com.bar_app.entity.Cocktail;
import com.bar_app.exception.CarteAlreadyExistsException;
import com.bar_app.repository.CarteRepository;
import com.bar_app.repository.ClientRepository;
import com.bar_app.repository.CocktailRepository;
import com.bar_app.service.CarteService;

class CarteServiceTest {
    @Mock
    private CarteRepository carteRepository;
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private CocktailRepository cocktailRepository;
    @InjectMocks
    private CarteService carteService;

    @BeforeEach
    void setUp() { MockitoAnnotations.openMocks(this); }

    @Test
    void getAllCartes_returnsList() {
        List<Carte> cartes = List.of(new Carte());
        when(carteRepository.findAll()).thenReturn(cartes);
        assertEquals(cartes, carteService.getAllCartes());
    }

    @Test
    void createCarte_throwsIfExists() {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(new Client()));
        when(carteRepository.existsByNomAndBarmakerId("nom", 1L)).thenReturn(true);
        assertThrows(CarteAlreadyExistsException.class, () -> carteService.createCarte("nom", 1L, List.of()));
    }

    @Test
    void createCarte_success() {
        Client barmaker = new Client();
        when(clientRepository.findById(1L)).thenReturn(Optional.of(barmaker));
        when(carteRepository.existsByNomAndBarmakerId("nom", 1L)).thenReturn(false);
        when(cocktailRepository.findAllById(any())).thenReturn(List.of(new Cocktail()));
        when(carteRepository.save(any())).thenReturn(new Carte());
        assertNotNull(carteService.createCarte("nom", 1L, List.of(1L)));
    }

    @Test
    void deleteCarte_deletes() {
        doNothing().when(carteRepository).deleteById(1L);
        assertDoesNotThrow(() -> carteService.deleteCarte(1L));
    }
} 