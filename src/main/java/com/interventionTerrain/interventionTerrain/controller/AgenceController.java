package com.interventionTerrain.interventionTerrain.controller;

import com.interventionTerrain.interventionTerrain.dto.AgenceRequestDto;
import com.interventionTerrain.interventionTerrain.dto.ResponseDto;
import com.interventionTerrain.interventionTerrain.dto.TechnicienEffectifResponseDto;
import com.interventionTerrain.interventionTerrain.service.AgenceService;
import com.interventionTerrain.interventionTerrain.utils.response.Response;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agences")
public class AgenceController {

    private final AgenceService agenceService;

    public AgenceController(AgenceService agenceService) {
        this.agenceService = agenceService;
    }

    @GetMapping
    public Response<ResponseDto> getAll(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int taille) {
        return new Response<>(agenceService.getAll(page, taille));
    }

    @GetMapping("/{id}")
    public Response<ResponseDto> getById(@PathVariable Long id) throws Throwable {
        return new Response<>(agenceService.getById(id));
    }

    @GetMapping("/recherche")
    public Response<ResponseDto> getByNom(@RequestParam String nom) throws Throwable {
        return new Response<>(agenceService.getByNom(nom));
    }

    @PostMapping
    public Response<ResponseDto> create(@Valid @RequestBody AgenceRequestDto agenceRequest) {
        return new Response<>(agenceService.create(agenceRequest));
    }

    @PutMapping("/{id}")
    public Response<ResponseDto> update(@PathVariable Long id,
                                        @Valid @RequestBody AgenceRequestDto agenceRequest) {
        return new Response<>(agenceService.update(id, agenceRequest));
    }

    @DeleteMapping("/{id}")
    public Response<ResponseDto> delete(@PathVariable Long id) throws Throwable {
        return new Response<>(agenceService.delete(id));
    }

    @GetMapping("/{id}/techniciens")
    public Response<TechnicienEffectifResponseDto> getEffectif(@PathVariable Long id) throws Throwable {
        return new Response<>(agenceService.getEffectif(id));
    }
}