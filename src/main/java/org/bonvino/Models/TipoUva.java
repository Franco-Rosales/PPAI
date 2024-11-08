package org.bonvino.Models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tipo_uva")
public class TipoUva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    // Relación ManyToMany con Varietal (mapeo inverso)
    @ManyToMany(mappedBy = "tiposUva")
    private List<Varietal> varietales = new ArrayList<>();

    // Getters y Setters

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Varietal> getVarietales() {
        return varietales;
    }

    public void setVarietales(List<Varietal> varietales) {
        this.varietales = varietales;
    }
}

