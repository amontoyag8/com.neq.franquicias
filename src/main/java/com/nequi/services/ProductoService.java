package com.nequi.services;

import com.nequi.model.ProductoEntity;
import com.nequi.model.SucursalEntity;
import com.nequi.repositories.ProductoRepository;
import org.springframework.stereotype.Service;
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
                .flatMap(sucursal -> {
                    sucursal.setNombre(nombreAct);
                    return productoRepository.save(sucursal);
                });
    }


    public Flux<ProductoEntity> obtenerTodosProductos() {
        return productoRepository.findAll();
    }
}
