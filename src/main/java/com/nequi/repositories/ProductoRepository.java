package com.nequi.repositories;

import com.nequi.model.FranquiciaEntity;
import com.nequi.model.ProductoEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ProductoRepository extends ReactiveCrudRepository<ProductoEntity, Long> {
    Mono<ProductoEntity> findById(Long id);
}
