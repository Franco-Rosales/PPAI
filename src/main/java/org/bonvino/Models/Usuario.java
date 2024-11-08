package org.bonvino.Models;

import javax.persistence.*;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "contrasenia")
    private String contrasenia;
    @Column(name = "es_premium")
    private boolean esPremium;

    @OneToOne(mappedBy = "usuario")
    private Enofilo enofilo;

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

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public boolean isEsPremium() {
        return esPremium;
    }

    public void setEsPremium(boolean esPremium) {
        this.esPremium = esPremium;
    }

    public Enofilo getEnofilo() {
        return enofilo;
    }

    public void setEnofilo(Enofilo enofilo) {
        this.enofilo = enofilo;
    }
}

