package com.nequi.services;

import com.nequi.model.SucursalEntity;
import com.nequi.repositories.SucursalRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class SucursalService {
    private final SucursalRepository sucursalRepository;

    public SucursalService(SucursalRepository sucursalRepository) {
        this.sucursalRepository = sucursalRepository;
    }

    public Mono<SucursalEntity> actualizarNombreSucursal(Long sucursalId, String nombreAct){
        return sucursalRepository.findById(sucursalId)
                .flatMap(sucursal -> {
                    sucursal.setNombre(nombreAct);
                    return sucursalRepository.save(sucursal);
                });
    }

    public Flux<SucursalEntity> obtenerSucursalesPorFranquicia(Long franquiciaId) {
        return sucursalRepository.findAllByFranquiciaId(franquiciaId);
    }
}
