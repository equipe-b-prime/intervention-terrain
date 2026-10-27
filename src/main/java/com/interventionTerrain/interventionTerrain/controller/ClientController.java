package com.interventionTerrain.interventionTerrain.controller;

import com.interventionTerrain.interventionTerrain.dto.ClientRequestDto;
import com.interventionTerrain.interventionTerrain.dto.ResponseDto;
import com.interventionTerrain.interventionTerrain.service.ClientService;
import com.interventionTerrain.interventionTerrain.service.InterventionService;
import com.interventionTerrain.interventionTerrain.utils.response.Response;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;
    private final InterventionService interventionService;

    public ClientController(ClientService clientService, InterventionService interventionService) {
        this.clientService = clientService;
        this.interventionService = interventionService;
    }

    @GetMapping
    public Response<ResponseDto> getAll(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int taille) {
        return new Response<>(clientService.getAll(page, taille));
    }

    @GetMapping("/{id}")
    public Response<ResponseDto> getById(@PathVariable Long id) throws Throwable {
        return new Response<>(clientService.getById(id));
    }

    @GetMapping("/recherche")
    public Response<ResponseDto> getByNom(@RequestParam String nom) throws Throwable {
        return new Response<>(clientService.getByNom(nom));
    }

    @PostMapping
    public Response<ResponseDto> create(@Valid @RequestBody ClientRequestDto clientRequest) {
        return new Response<>(clientService.create(clientRequest));
    }

    @PutMapping("/{id}")
    public Response<ResponseDto> update(@PathVariable Long id,
                                        @Valid @RequestBody ClientRequestDto clientRequest) {
        return new Response<>(clientService.update(id, clientRequest));
    }

    @DeleteMapping("/{id}")
    public Response<ResponseDto> delete(@PathVariable Long id) throws Throwable {
        return new Response<>(clientService.delete(id));
    }

    @GetMapping("/{id}/interventions")
    public Response<ResponseDto> getInterventions(@PathVariable Long id) throws Throwable {
        return new Response<>(interventionService.getHistoriqueByClient(id));
    }
}