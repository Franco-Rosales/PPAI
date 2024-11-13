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
        System.out.println("Es Maridaje Actualizacion");
        EntityManager em = emf.createEntityManager();
        Maridaje maridaje = null; // Variable para el maridaje encontrado o creado
        try {
            // Buscar el maridaje por nombre
            TypedQuery<Maridaje> query = em.createQuery(
                    "SELECT m FROM Maridaje m WHERE m.nombre = :nombre", Maridaje.class);
            query.setParameter("nombre", this.nombre);

            List<Maridaje> resultados = query.getResultList();

            if (!resultados.isEmpty()) {
                // Si el maridaje existe, lo retornamos
                maridaje = resultados.get(0);
            } else {
                // Si no se encuentra el maridaje, lo creamos
                maridaje = new Maridaje();
                maridaje.setNombre(this.nombre); // Asignamos el nombre
                maridaje.setDescripcion(this.descripcion); // Asignamos la descripción
                em.getTransaction().begin();
                em.persist(maridaje); // Persistimos el nuevo maridaje
                em.getTransaction().commit(); // Confirmamos la transacción
            }

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }

        return maridaje;
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

