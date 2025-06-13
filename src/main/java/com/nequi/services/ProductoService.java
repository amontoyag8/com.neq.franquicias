package com.nequi.services;

import org.springframework.stereotype.Service;

import com.nequi.model.ProductoEntity;
import com.nequi.repositories.ProductoRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public Mono<ProductoEntity> actualizarNombreSucursal(Long productoId, String nombreAct){
        return productoRepository.findById(productoId)
                .flatMap(sucursales -> {
                    sucursales.setNombre(nombreAct);
                    return productoRepository.save(sucursales);
                });
    }


    public Flux<ProductoEntity> obtenerTodosProductos() {
        return productoRepository.findAll();
    }
}
