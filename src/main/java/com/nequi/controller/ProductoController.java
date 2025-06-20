package com.nequi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nequi.model.ProductoEntity;
import com.nequi.repositories.ProductoRepository;
import com.nequi.services.ProductoService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/productos")
public class ProductoController {

    private final ProductoRepository productoRepository;
    private final ProductoService productoService;

    public ProductoController(ProductoRepository productoRepository, ProductoService productoService) {
        this.productoRepository = productoRepository;
        this.productoService = productoService;
    }

    @PostMapping
    public Mono<ResponseEntity<String>> agregarProducto(@RequestBody ProductoEntity producto){
        return productoRepository.save(producto)
                .map(productoGuardado -> ResponseEntity.status(HttpStatus.CREATED).body("Producto creado"))
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear el producto")));
    }

    @PutMapping("/{productoId}")
    public Mono<ResponseEntity<String>>actualizarNombreProducto(@PathVariable Long productoId, @RequestBody ProductoEntity nombreAct){
        if(nombreAct.getNombre() != null)
            return productoService.actualizarNombreSucursal(productoId, nombreAct.getNombre())
                    .map(actualizado -> ResponseEntity.ok().body("Nombre Actualizado"));
        else
            return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el nombre del producto"));
    }


    @GetMapping("/all")
    public Flux<ProductoEntity> obtenerTodosLosProductos() {
        return productoService.obtenerTodosProductos();
    }
}
