package com.chaussures.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chaussures.entity.Carte;
import com.chaussures.entity.Client;
import com.chaussures.entity.Cocktail;
import com.chaussures.repository.CarteRepository;
import com.chaussures.repository.ClientRepository;
import com.chaussures.repository.CocktailRepository;

@Service
public class CarteService {
    @Autowired
    private CarteRepository carteRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CocktailRepository cocktailRepository;

    public List<Carte> getAllCartes() {
        return carteRepository.findAll();
    }

    public Optional<Carte> getCarteById(Long id) {
        return carteRepository.findById(id);
    }

    public List<Carte> getCartesByBarmakerId(Long barmakerId) {
        return carteRepository.findByBarmakerId(barmakerId);
    }

    public Carte createCarte(String nom, Long barmakerId, List<Long> cocktailIds) {
        Client barmaker = clientRepository.findById(barmakerId)
                .orElseThrow(() -> new RuntimeException("Barmaker non trouvé"));
        List<Cocktail> cocktails = cocktailRepository.findAllById(cocktailIds);
        Carte carte = new Carte();
        carte.setNom(nom);
        carte.setDateCreation(LocalDateTime.now());
        carte.setBarmaker(barmaker);
        carte.setCocktails(cocktails);
        return carteRepository.save(carte);
    }

    public void deleteCarte(Long id) {
        carteRepository.deleteById(id);
    }
} 