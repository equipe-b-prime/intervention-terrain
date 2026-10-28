package com.interventionTerrain.interventionTerrain.controller;

import com.interventionTerrain.interventionTerrain.dto.CompetenceRequestDto;
import com.interventionTerrain.interventionTerrain.dto.DisponiblesResponseDto;
import com.interventionTerrain.interventionTerrain.dto.ResponseDto;
import com.interventionTerrain.interventionTerrain.dto.TechnicienCompetenceResponseDto;
import com.interventionTerrain.interventionTerrain.dto.TechnicienRequestDto;
import com.interventionTerrain.interventionTerrain.service.InterventionService;
import com.interventionTerrain.interventionTerrain.service.TechnicienService;
import com.interventionTerrain.interventionTerrain.utils.response.Response;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/techniciens")
public class TechnicienController {

    private final TechnicienService technicienService;
    private final InterventionService interventionService;

    public TechnicienController(TechnicienService technicienService,
                                InterventionService interventionService) {
        this.technicienService = technicienService;
        this.interventionService = interventionService;
    }

    @GetMapping
    public Response<ResponseDto> getAll(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int taille) {
        return new Response<>(technicienService.getAll(page, taille));
    }

    @GetMapping("/{id}")
    public Response<ResponseDto> getById(@PathVariable Long id) throws Throwable {
        return new Response<>(technicienService.getById(id));
    }

    @GetMapping("/recherche")
    public Response<ResponseDto> getByNom(@RequestParam String nom) throws Throwable {
        return new Response<>(technicienService.getByNom(nom));
    }

    @PostMapping
    public Response<ResponseDto> create(@Valid @RequestBody TechnicienRequestDto technicienRequest) {
        return new Response<>(technicienService.create(technicienRequest));
    }

    @PutMapping("/{id}")
    public Response<ResponseDto> update(@PathVariable Long id,
                                        @Valid @RequestBody TechnicienRequestDto technicienRequest) {
        return new Response<>(technicienService.update(id, technicienRequest));
    }

    @DeleteMapping("/{id}")
    public Response<ResponseDto> delete(@PathVariable Long id) throws Throwable {
        return new Response<>(technicienService.delete(id));
    }

    @GetMapping("/{id}/competences")
    public Response<TechnicienCompetenceResponseDto> getCompetences(@PathVariable Long id) throws Throwable {
        return new Response<>(technicienService.getCompetencesByTechnicien(id));
    }

    @PostMapping("/{id}/competences")
    public Response<TechnicienCompetenceResponseDto> associerCompetence(
            @PathVariable Long id,
            @Valid @RequestBody CompetenceRequestDto competenceRequest) {
        return new Response<>(technicienService.associerCompetence(id, competenceRequest));
    }

    @GetMapping("/{id}/planning")
    public Response<ResponseDto> getPlanning(@PathVariable Long id,
                                             @RequestParam(required = false) LocalDate du,
                                             @RequestParam(required = false) LocalDate au) throws Throwable {
        return new Response<>(interventionService.getPlanning(id, du, au));
    }

    @GetMapping("/disponibles")
    public Response<DisponiblesResponseDto> getDisponibles(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime debut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin,
            @RequestParam String type) throws Throwable {
        return new Response<>(technicienService.getDisponibles(debut, fin, type));
    }
}