package org.bonvino.Models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "varietal")
public class Varietal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "porcentaje_composicion")
    private int porcentajeComposicion;

    // Relación ManyToOne con Vino (cada varietal puede pertenecer a múltiples vinos, pero cada vino tiene un único varietal)
    @OneToOne(mappedBy = "varietal")
    private Vino vino;

    // Relación ManyToMany con TipoUva (cada varietal puede tener múltiples tipos de uvas y viceversa)
    @ManyToMany
    @JoinTable(
            name = "varietal_tipo_uva",
            joinColumns = @JoinColumn(name = "varietal_id"),
            inverseJoinColumns = @JoinColumn(name = "tipo_uva_id")
    )
    private List<TipoUva> tiposUva = new ArrayList<>();

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("wine_persistence_unit");



    public Varietal() {
    }

    public Varietal esVarietalActualizacion() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Varietal> query = em.createQuery(
                    "SELECT v FROM Varietal v WHERE v.nombre = :nombre", Varietal.class);
            query.setParameter("nombre", this.nombre);
            return query.getResultStream().findFirst().orElse(null);
        } finally {
            em.close();
        }
    }


    // Getters y Setters
    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPorcentajeComposicion() {
        return porcentajeComposicion;
    }

    public void setPorcentajeComposicion(int porcentajeComposicion) {
        this.porcentajeComposicion = porcentajeComposicion;
    }

    public Vino getVino() {
        return vino;
    }

    public void setVino(Vino vino) {
        this.vino = vino;
    }

    public List<TipoUva> getTiposUva() {
        return tiposUva;
    }

    public void setTiposUva(List<TipoUva> tiposUva) {
        this.tiposUva = tiposUva;
    }
}

