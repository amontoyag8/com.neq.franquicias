package com.nequi.repositories;


import com.nequi.model.FranquiciaEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;


public interface FranquiciaRepository extends ReactiveCrudRepository<FranquiciaEntity, Long> {
    Mono<FranquiciaEntity> findById(Long id);
}
