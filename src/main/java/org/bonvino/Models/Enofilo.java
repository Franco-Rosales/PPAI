package org.bonvino.Models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "enofilo")
public class Enofilo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "apellido")
    private String apellido;
    @Column(name = "imagenPerfil")
    private String imagenPerfil;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("wine_persistence_unit");



    public String obtenerNombreEnofiloSuscripto(String bodegaSeleccionada){
        if (this.estaSuscripto(bodegaSeleccionada)){
            return this.buscarNomreUsuario();
        }else {
            return null;
        }
    }

    public boolean estaSuscripto(String bodegaSeleccionada){
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Siguiendo> siguiendoQuery = em.createQuery(
                    "SELECT s FROM Siguiendo s WHERE s.enofilo.id = :enofiloId", Siguiendo.class);
            siguiendoQuery.setParameter("enofiloId", this.id);
            List<Siguiendo> siguiendos = siguiendoQuery.getResultList();
            if (siguiendos.size() > 0){
                for (Siguiendo siguiendo : siguiendos) {
                    if (siguiendo.sosDeBodega(bodegaSeleccionada)){
                        return true;
                    }
                }
            }
            return false;
        } finally {
            em.close();
        }
    }


    public String buscarNomreUsuario(){
        return this.usuario.getNombre();
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getImagenPerfil() {
        return imagenPerfil;
    }

    public void setImagenPerfil(String imagenPerfil) {
        this.imagenPerfil = imagenPerfil;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}

