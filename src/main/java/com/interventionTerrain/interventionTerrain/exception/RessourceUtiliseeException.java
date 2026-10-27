package com.interventionTerrain.interventionTerrain.exception;

public class RessourceUtiliseeException extends RuntimeException {
    public RessourceUtiliseeException(String ressource, Long id, String cause) {
        super("La ressource " + ressource + " (id=" + id + ") est utilisée par " + cause);
    }
}