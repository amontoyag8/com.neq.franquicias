package com.nequi.repositories;


import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.nequi.model.FranquiciaEntity;

import reactor.core.publisher.Mono;


public interface FranquiciaRepository extends ReactiveCrudRepository<FranquiciaEntity, Long> {
    Mono<FranquiciaEntity> findById(Long id);
}
