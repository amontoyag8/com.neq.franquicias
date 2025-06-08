package com.nequi.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Table("franquicia")
public class FranquiciaEntity {

    @Id
    private long id;

    private String nombre;
    private String direccion;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
