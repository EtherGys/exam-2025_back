package com.bar_app.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bar_app.entity.Carte;
import com.bar_app.entity.Client;
import com.bar_app.entity.Cocktail;
import com.bar_app.exception.CarteAlreadyExistsException;
import com.bar_app.repository.CarteRepository;
import com.bar_app.repository.ClientRepository;
import com.bar_app.repository.CocktailRepository;

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

    public Carte createCarte(String nom, String description, String image, Long barmakerId, List<Long> cocktailIds) {
        // Vérifier si le barmaker existe
        Client barmaker = clientRepository.findById(barmakerId)
                .orElseThrow(() -> new RuntimeException("Barmaker non trouvé"));

        // Vérifier si une carte avec le même nom existe déjà pour ce barmaker
        if (carteRepository.existsByNomAndBarmakerId(nom, barmakerId)) {
            throw new CarteAlreadyExistsException(nom, barmakerId);
        }

        List<Cocktail> cocktails = cocktailRepository.findAllById(cocktailIds);
        Carte carte = new Carte(nom, description, image, LocalDateTime.now(), barmaker, cocktails);
        return carteRepository.save(carte);
    }

    public void deleteCarte(Long id) {
        carteRepository.deleteById(id);
    }

    public Carte updateCarte(Long id, String nom, List<Long> cocktailIds) {
        Carte carte = carteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Carte non trouvée"));

        // Vérifier si le nouveau nom n'est pas déjà utilisé par le même barmaker
        if (!nom.equals(carte.getNom()) &&
                carteRepository.existsByNomAndBarmakerId(nom, carte.getBarmaker().getId())) {
            throw new CarteAlreadyExistsException(nom, carte.getBarmaker().getId());
        }

        List<Cocktail> cocktails = cocktailRepository.findAllById(cocktailIds);
        carte.setNom(nom);
        carte.setCocktails(cocktails);
        return carteRepository.save(carte);
    }
}