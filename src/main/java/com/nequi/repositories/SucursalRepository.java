package com.nequi.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.nequi.model.SucursalEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SucursalRepository extends ReactiveCrudRepository<SucursalEntity, Long> {
    Mono<SucursalEntity> findById(Long id);

    Flux<SucursalEntity> findByFranquiciaId(Long franquiciaId);

    Flux<SucursalEntity> findAllByFranquiciaId(Long franquiciaId);
}
