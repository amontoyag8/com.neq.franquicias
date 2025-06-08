package com.nequi.services;

import org.springframework.stereotype.Service;

import com.nequi.model.FranquiciaEntity;
import com.nequi.repositories.FranquiciaRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FranquiciaService {
    private final FranquiciaRepository franquiciaRepository;

    public FranquiciaService(FranquiciaRepository franquiciaRepository) {
        this.franquiciaRepository = franquiciaRepository;
    }

    public Mono<FranquiciaEntity> actualizarNombreFranquicia(Long franquiciaId, String nombreAct)
    {
        return franquiciaRepository.findById(franquiciaId)
                .flatMap(franquicias -> {
                    franquicias.setNombre(nombreAct);
                    return franquiciaRepository.save(franquicias);
                });
    }

    public Flux<FranquiciaEntity> obtenerTodasFranquicias() {
        return franquiciaRepository.findAll();
    }
}
