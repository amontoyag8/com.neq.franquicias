package com.nequi.repositories;

import java.util.List;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.nequi.model.SucursalProductoEntity;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface SucursalProductoRepository extends ReactiveCrudRepository<SucursalProductoEntity, Long> {
    Mono<SucursalProductoEntity> findBySucursalIdAndProductoId(Long sucursalId, Long productoId);

    Mono<Void> deleteBySucursalIdAndProductoId(Long sucursalId, Long productoId);

    Flux<SucursalProductoEntity> findBySucursalIdIn(List<Long> sucursalIds);


}

