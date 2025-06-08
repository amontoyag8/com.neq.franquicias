package com.nequi.services;

import com.nequi.model.FranquiciaEntity;
import com.nequi.repositories.FranquiciaRepository;
import org.springframework.stereotype.Service;
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
                .flatMap(franquicia -> {
                    franquicia.setNombre(nombreAct);
                    return franquiciaRepository.save(franquicia);
                });

    }

    public Flux<FranquiciaEntity> obtenerTodasFranquicias() {
        return franquiciaRepository.findAll();
    }
}
