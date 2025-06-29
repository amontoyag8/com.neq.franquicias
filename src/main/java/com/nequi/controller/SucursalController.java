package com.nequi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nequi.model.SucursalEntity;
import com.nequi.repositories.FranquiciaRepository;
import com.nequi.repositories.SucursalRepository;
import com.nequi.services.SucursalService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/sucursales")
public class SucursalController {

    private final FranquiciaRepository franquiciaRepository;
    private final SucursalRepository   sucursalRepository;

    private final SucursalService sucursalService;

    public SucursalController(FranquiciaRepository franquiciaRepository, SucursalRepository sucursalRepository, SucursalService sucursalService) {
        this.franquiciaRepository = franquiciaRepository;
        this.sucursalRepository = sucursalRepository;
        this.sucursalService = sucursalService;
    }

    @PostMapping
    public Mono<ResponseEntity<SucursalEntity>> agregarSucursal(@RequestBody SucursalEntity sucursal){
        return franquiciaRepository.findById(sucursal.getFranquiciaId())
                .flatMap(franquicia -> {
                    sucursal.setFranquiciaId(franquicia.getId());
                    return sucursalRepository.save(sucursal);
                }).map(sucursalSaved -> ResponseEntity.status(HttpStatus.CREATED).body(sucursalSaved))
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{sucursalId}")
    public Mono<ResponseEntity<String>>actualizarNombreSucursal(@PathVariable Long sucursalId, @RequestBody SucursalEntity nombreAct){
        if(nombreAct.getNombre() != null)
            return sucursalService.actualizarNombreSucursal(sucursalId, nombreAct.getNombre())
                    .map(actualizado -> ResponseEntity.ok().body("Nombre actualizado"));
        else
            return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el nombre de la Sucursal"));
    }

    @GetMapping("/allByFranquisiaId/{franquiciaId}")
    public Flux<SucursalEntity> obtenerSucursalesPorFranquicia(@PathVariable Long franquiciaId) {
        return sucursalService.obtenerSucursalesPorFranquicia(franquiciaId);
    }
}
