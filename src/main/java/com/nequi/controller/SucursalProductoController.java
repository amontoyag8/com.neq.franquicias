package com.nequi.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nequi.model.ProductoEntity;
import com.nequi.model.SucursalEntity;
import com.nequi.model.SucursalProductoEntity;
import com.nequi.repositories.ProductoRepository;
import com.nequi.repositories.SucursalProductoRepository;
import com.nequi.repositories.SucursalRepository;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/productos-sucursal")
public class SucursalProductoController {

    private final ProductoRepository productoRepository;
    private final SucursalRepository sucursalRepository;
    private final SucursalProductoRepository sucursalProductoRepository;

    public SucursalProductoController(ProductoRepository productoRepository, SucursalRepository sucursalRepository, SucursalProductoRepository sucursalProductoRepository) {
        this.productoRepository = productoRepository;
        this.sucursalRepository = sucursalRepository;
        this.sucursalProductoRepository = sucursalProductoRepository;
    }

    @PostMapping
    public Mono<ResponseEntity<String>>agregarProductoSucursal(@RequestBody SucursalProductoEntity sucursalProducto){

        Mono<SucursalEntity> sucursalEntityMono = sucursalRepository.findById(sucursalProducto.getSucursalId());
        Mono<ProductoEntity> productoEntityMono = productoRepository.findById(sucursalProducto.getProductoId());

        return Mono.zip(sucursalEntityMono, productoEntityMono)
                .flatMap(tuple -> {
                    SucursalEntity sucursal = tuple.getT1();
                    ProductoEntity producto = tuple.getT2();

                    if(sucursal != null && producto != null){
                        return  sucursalProductoRepository.findBySucursalIdAndProductoId(producto.getId(), sucursal.getId())
                                .flatMap(existenAmbos -> {
                                    existenAmbos.setStock(existenAmbos.getStock() + sucursalProducto.getStock());
                                    return sucursalProductoRepository.save(existenAmbos)
                                            .map(agregado -> ResponseEntity.ok("Stock agregado"));
                                })
                                .switchIfEmpty(sucursalProductoRepository.save(sucursalProducto).map(guardado -> ResponseEntity.status(HttpStatus.CREATED).body("Producto agregado")));
                    }else {
                        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sucursal o producto no encontrado"));
                    }
                });
    }

    @DeleteMapping
    public Mono<ResponseEntity<String>> eliminarProductoSucursal(@RequestParam Long productoId, @RequestParam Long sucursalId)
    {
        Mono<SucursalEntity> sucursalEntityMono = sucursalRepository.findById(sucursalId);
        Mono<ProductoEntity> productoEntityMono = productoRepository.findById(productoId);

        return Mono.zip(sucursalEntityMono, productoEntityMono)
                .flatMap(tuple -> {
                    SucursalEntity sucursal = tuple.getT1();
                    ProductoEntity producto = tuple.getT2();

                    if(sucursal != null && producto != null){
                        return  sucursalProductoRepository.deleteBySucursalIdAndProductoId(productoId, sucursalId)
                                .then(Mono.just(ResponseEntity.ok().body("Producto eliminado de la sucursal")))
                                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el producto de la sucursal.")));
                    }else {
                        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sucursal o producto no encontrado"));
                    }
                });
    }

    @PutMapping
    public Mono<ResponseEntity<String>> actualizarStock(@RequestParam Long productoId, @RequestParam Long sucursalId, @RequestParam int stock){

        Mono<SucursalEntity> sucursalEntityMono = sucursalRepository.findById(sucursalId);
        Mono<ProductoEntity> productoEntityMono = productoRepository.findById(productoId);

        return Mono.zip(sucursalEntityMono, productoEntityMono)
                .flatMap(tuple -> {
                    SucursalEntity sucursal = tuple.getT1();
                    ProductoEntity producto = tuple.getT2();

                    if(sucursal == null || producto== null){
                        return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto o sucursal no encontrado"));
                    }

                    return  sucursalProductoRepository.findBySucursalIdAndProductoId(productoId, sucursalId)
                            .flatMap(sucursalProducto -> {
                               if(sucursalProducto == null){
                                   return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe la relacion entre el producto y la sucursal"));
                               }
                               sucursalProducto.setStock(stock);
                               return sucursalProductoRepository.save(sucursalProducto)
                                       .then(Mono.just(ResponseEntity.ok().body("Stock actualizado")));
                            });
                });
    }

    @GetMapping("/prod-stock-mas/{franquiciaId}")
    public Mono<ResponseEntity<List<SucursalProductoEntity>>>obtenerProductosConMasStockPorSucursal(@PathVariable Long franquiciaId){

        return sucursalRepository.findByFranquiciaId(franquiciaId).collectList()
                .flatMap(sucursales -> {
                    List<Long> sucursalesId = sucursales.stream()
                            .map(SucursalEntity::getId)
                            .collect(Collectors.toList());

                    return sucursalProductoRepository.findBySucursalIdIn(sucursalesId).collectList()
                            .flatMap(productosSucursal -> {
                                Map<Long, SucursalProductoEntity> sucursalesMap = new HashMap<>();

                                for(SucursalProductoEntity sucursalProducto : productosSucursal){
                                    Long sucursalId = sucursalProducto.getSucursalId();
                                    if(!sucursalesMap.containsKey(sucursalId)){
                                        sucursalesMap.put(sucursalId, sucursalProducto);
                                    }
                                    else {
                                        SucursalProductoEntity productoConMasStock = sucursalesMap.get(sucursalId);
                                        if(sucursalProducto.getStock() > productoConMasStock.getStock()){
                                            sucursalesMap.put(sucursalId, sucursalProducto);
                                        }
                                    }
                                }
                                List<SucursalProductoEntity> productosConMasStock = new ArrayList<>(sucursalesMap.values());
                                return Mono.just(ResponseEntity.ok().body(productosConMasStock));

                            });

                });
    }

}
