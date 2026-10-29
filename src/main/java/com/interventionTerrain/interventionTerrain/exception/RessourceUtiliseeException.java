package com.interventionTerrain.interventionTerrain.exception;

public class RessourceUtiliseeException extends RuntimeException {

    public RessourceUtiliseeException(String ressource, Long id, String cause) {
        super("Impossible de traiter la ressource " + ressource + " avec l'ID " + id + " car elle est liée à " + cause);
    }
}