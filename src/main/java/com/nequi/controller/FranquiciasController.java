package com.nequi.controller;


import com.nequi.model.FranquiciaEntity;
import com.nequi.repositories.FranquiciaRepository;
import com.nequi.repositories.SucursalRepository;
import com.nequi.services.FranquiciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("api/franquicias")
public class FranquiciasController {

    private final FranquiciaRepository franquiciaRepository;
    private final SucursalRepository sucursalRepository;
    private final FranquiciaService   franquiciaService;

    public FranquiciasController(FranquiciaRepository franquiciaRepository, SucursalRepository sucursalRepository, FranquiciaService franquiciaService) {
        this.franquiciaRepository = franquiciaRepository;
        this.sucursalRepository = sucursalRepository;
        this.franquiciaService = franquiciaService;
    }


    @PostMapping
    public Mono<ResponseEntity<String>> createFranquicia(@RequestBody FranquiciaEntity franquicia)
    {
        return franquiciaRepository.save(franquicia).map(productoGuardado -> ResponseEntity.status(HttpStatus.CREATED).body("Franquicia Creada"))
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al Crear la franquicia")));
    }

    @PutMapping("/cambio-nombre/{franquiciaId}")
    public Mono<ResponseEntity<String>> updateFranquicia(@PathVariable Long franquiciaId, @RequestBody FranquiciaEntity nombreAct)
    {
        if(nombreAct.getNombre() != null)
        return franquiciaService.actualizarNombreFranquicia(franquiciaId, nombreAct.getNombre())
                .map(actualizado -> ResponseEntity.ok().body("Nombre Actualizado"));
        else
           return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al Actualizar El nombre de la franquicia"));
    }

    @GetMapping("/all")
    public Flux<FranquiciaEntity> obtenerTodasLasFranquicias() {
        return franquiciaService.obtenerTodasFranquicias();
    }



}
