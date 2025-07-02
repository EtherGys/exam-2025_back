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
    void deleteCarte_deletes() {
        doNothing().when(carteRepository).deleteById(1L);
        assertDoesNotThrow(() -> carteService.deleteCarte(1L));
    }

    @Test
    void getCarteById_found() {
        Carte carte = new Carte();
        when(carteRepository.findById(1L)).thenReturn(Optional.of(carte));
        assertEquals(Optional.of(carte), carteService.getCarteById(1L));
    }

    @Test
    void getCarteById_notFound() {
        when(carteRepository.findById(2L)).thenReturn(Optional.empty());
        assertEquals(Optional.empty(), carteService.getCarteById(2L));
    }

    @Test
    void getCartesByBarmakerId_returnsList() {
        List<Carte> cartes = List.of(new Carte());
        when(carteRepository.findByBarmakerId(1L)).thenReturn(cartes);
        assertEquals(cartes, carteService.getCartesByBarmakerId(1L));
    }

    @Test
    void createCarte_success() {
        Client barmaker = new Client();
        when(clientRepository.findById(1L)).thenReturn(Optional.of(barmaker));
        when(carteRepository.existsByNomAndBarmakerId("nom", 1L)).thenReturn(false);
        when(cocktailRepository.findAllById(List.of(1L))).thenReturn(List.of(new Cocktail()));
        when(carteRepository.save(any())).thenReturn(new Carte());
        Carte carte = carteService.createCarte("nom", "desc", "img", 1L, List.of(1L));
        assertNotNull(carte);
    }

    @Test
    void createCarte_alreadyExists() {
        Client barmaker = new Client();
        when(clientRepository.findById(1L)).thenReturn(Optional.of(barmaker));
        when(carteRepository.existsByNomAndBarmakerId("nom", 1L)).thenReturn(true);
        assertThrows(CarteAlreadyExistsException.class, () ->
            carteService.createCarte("nom", "desc", "img", 1L, List.of(1L)));
    }

    @Test
    void updateCarte_success() {
        Carte carte = new Carte();
        carte.setNom("old");
        Client barmaker = new Client();
        barmaker.setId(1L);
        carte.setBarmaker(barmaker);
        when(carteRepository.findById(1L)).thenReturn(Optional.of(carte));
        when(carteRepository.existsByNomAndBarmakerId("new", 1L)).thenReturn(false);
        when(cocktailRepository.findAllById(List.of(1L))).thenReturn(List.of(new Cocktail()));
        when(carteRepository.save(any())).thenReturn(carte);
        Carte result = carteService.updateCarte(1L, "new", List.of(1L));
        assertEquals("new", result.getNom());
    }

    @Test
    void updateCarte_alreadyExists() {
        Carte carte = new Carte();
        carte.setNom("old");
        Client barmaker = new Client();
        barmaker.setId(1L);
        carte.setBarmaker(barmaker);
        when(carteRepository.findById(1L)).thenReturn(Optional.of(carte));
        when(carteRepository.existsByNomAndBarmakerId("new", 1L)).thenReturn(true);
        assertThrows(CarteAlreadyExistsException.class, () ->
            carteService.updateCarte(1L, "new", List.of(1L)));
    }
} 