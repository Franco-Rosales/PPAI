package org.bonvino.Models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "maridaje")
public class Maridaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    // Relación ManyToMany con Vino (mapeo inverso)
    @ManyToMany(mappedBy = "maridajes")
    private List<Vino> vinos = new ArrayList<>();

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("wine_persistence_unit");


    // Constructor


    public Maridaje esMaridajeActualizacion() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Maridaje> query = em.createQuery(
                    "SELECT m FROM Maridaje m WHERE m.nombre = :nombre", Maridaje.class);
            query.setParameter("nombre", this.nombre);
            // Obtener el primer maridaje encontrado, si existe
            List<Maridaje> resultados = query.getResultList();
            if (!resultados.isEmpty()) {
                return resultados.get(0); // Retorna el primer maridaje encontrado
            }
            return null; // Retorna null si no se encuentra un maridaje
        } finally {
            em.close();
        }
    }

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

    public List<Vino> getVino() {
        return vinos;
    }

    public void setVino(List<Vino> vino) {
        this.vinos = vino;
    }
}

