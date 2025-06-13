package com.nequi.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.nequi.model.ProductoEntity;

import reactor.core.publisher.Mono;

public interface ProductoRepository extends ReactiveCrudRepository<ProductoEntity, Long> {
    Mono<ProductoEntity> findById(Long id);
}
