package com.nequi.controller;

import com.nequi.model.FranquiciaEntity;
import com.nequi.model.ProductoEntity;
import com.nequi.model.SucursalEntity;
import com.nequi.repositories.ProductoRepository;
import com.nequi.services.ProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
                .map(productoGuardado -> ResponseEntity.status(HttpStatus.CREATED).body("producto Creado"))
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al Crear el producto")));
    }

    @PutMapping("/cambio-nombre/{productoId}")
    public Mono<ResponseEntity<String>>actualizarNombreProducto(@PathVariable Long productoId, @RequestBody ProductoEntity nombreAct){
        if(nombreAct.getNombre() != null)
            return productoService.actualizarNombreSucursal(productoId, nombreAct.getNombre())
                    .map(actualizado -> ResponseEntity.ok().body("Nombre Actualizado"));
        else
            return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al Actualizar El nombre del producto"));
    }


    @GetMapping("/all")
    public Flux<ProductoEntity> obtenerTodosLosProductos() {
        return productoService.obtenerTodosProductos();
    }
}
