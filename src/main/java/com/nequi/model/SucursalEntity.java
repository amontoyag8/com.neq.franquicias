package com.nequi.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;


@Table("sucursales")
public class SucursalEntity {

    @Id
    private Long id;

    private String nombre;

    @Column("id_franquicia")
    private Long franquiciaId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getFranquiciaId() {
        return franquiciaId;
    }

    public void setFranquiciaId(Long franquiciaId) {
        this.franquiciaId = franquiciaId;
    }
}
