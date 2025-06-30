package com.bar_app.exception;

public class CarteAlreadyExistsException extends RuntimeException {
    
    public CarteAlreadyExistsException(String message) {
        super(message);
    }
    
    public CarteAlreadyExistsException(String nom, Long barmakerId) {
        super("Une carte avec le nom '" + nom + "' existe déjà");
    }
} 