package com.interventionTerrain.interventionTerrain.service;

import com.interventionTerrain.interventionTerrain.dto.ResponseDto;
import com.interventionTerrain.interventionTerrain.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class AbstractCrudService implements CrudService {

    protected static final String NOT_FOUND_MESSAGE = "Ressource non trouvée";
    protected static final int TAILLE_DEFAUT = 10;
    protected static final int TAILLE_MAX = 100;

    private final JpaRepository repository;

    public AbstractCrudService(JpaRepository repository) {
        this.repository = repository;
    }

    public Page<ResponseDto> getAll(int page, int taille) {
        Pageable pageable = PageRequest.of(pageAutorisee(page), tailleAutorisee(taille));
        return repository.findAll(pageable).map(this::toDto);
    }

    public ResponseDto getById(Long id) throws Throwable {
        Object entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(messageIntrouvable()));
        return toDto(entity);
    }

    public ResponseDto delete(Long id) throws Throwable {
        Object entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(messageIntrouvable()));
        repository.deleteById(id);
        return toDto(entity);
    }

    protected int tailleAutorisee(int taille) {
        if (taille < 1) return TAILLE_DEFAUT;
        return Math.min(taille, TAILLE_MAX);
    }

    protected int pageAutorisee(int page) {
        return Math.max(page, 0);
    }

    protected String messageIntrouvable() {
        return NOT_FOUND_MESSAGE;
    }


}