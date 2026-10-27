package com.interventionTerrain.interventionTerrain.exception;

import com.interventionTerrain.interventionTerrain.utils.response.Response;
import com.interventionTerrain.interventionTerrain.utils.response.Status;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<Map<String, String>> listeDerreurs = new ArrayList<>();
        for (FieldError error : ex.getFieldErrors()) {
            Map<String, String> detailErreur = new HashMap<>();
            detailErreur.put("champ", error.getField());
            detailErreur.put("message", error.getDefaultMessage());
            listeDerreurs.add(detailErreur);
        }
        Response<Map<String, String>> response = new Response<>();
        response.setData(listeDerreurs);
        response.setStatus(new Status(HttpStatus.BAD_REQUEST.value(), "Erreur de validation"));
        response.setHasError(true);
        response.setNombre((long) listeDerreurs.size());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Response<String>> notFoundException(NotFoundException ex) {
        Response<String> reponse = new Response<>(Collections.emptyList());
        reponse.setNombre(0L);
        reponse.setHasError(true);
        reponse.setStatus(new Status(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(reponse);
    }

    @ExceptionHandler(DoublonRessourceException.class)
    public ResponseEntity<Response<String>> doublonRessourceException(DoublonRessourceException ex) {
        Response<String> reponse = new Response<>(Collections.emptyList());
        reponse.setNombre(0L);
        reponse.setHasError(true);
        reponse.setStatus(new Status(HttpStatus.CONFLICT.value(), "Doublon de ressource : " + ex.getMessage()));
        return ResponseEntity.status(HttpStatus.CONFLICT).body(reponse);
    }

    @ExceptionHandler(RessourceUtiliseeException.class)
    public ResponseEntity<Response<String>> ressourceUtiliseeException(RessourceUtiliseeException ex) {
        Response<String> reponse = new Response<>(Collections.emptyList());
        reponse.setNombre(0L);
        reponse.setHasError(true);
        reponse.setStatus(new Status(HttpStatus.CONFLICT.value(), ex.getMessage()));
        return ResponseEntity.status(HttpStatus.CONFLICT).body(reponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Response<String>> illegalArgumentException(IllegalArgumentException ex) {
        Response<String> reponse = new Response<>(Collections.emptyList());
        reponse.setNombre(0L);
        reponse.setHasError(true);
        reponse.setStatus(new Status(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(reponse);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Response<String>> handleIllegalStateException(IllegalStateException ex) {
        Response<String> reponse = new Response<>(Collections.emptyList());
        reponse.setNombre(0L);
        reponse.setHasError(true);
        reponse.setStatus(new Status(HttpStatus.CONFLICT.value(), ex.getMessage()));
        return ResponseEntity.status(HttpStatus.CONFLICT).body(reponse);
    }

    @ExceptionHandler(ParametresInvalidesException.class)
    public ResponseEntity<Response<String>> parametresInvalidesException(ParametresInvalidesException ex) {
        Response<String> reponse = new Response<>(Collections.emptyList());
        reponse.setNombre(0L);
        reponse.setHasError(true);
        reponse.setStatus(new Status(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(reponse);
    }
}