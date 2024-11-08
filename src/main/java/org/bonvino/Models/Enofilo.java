package org.bonvino.Models;

import javax.persistence.*;

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



    public String obtenerNombreEnofiloSuscripto(){
        EntityManager em = emf.createEntityManager();

        try {
            // Buscar el usuario asociado al enófilo y obtener el nombre
            TypedQuery<String> usuarioQuery = em.createQuery(
                    "SELECT u.nombre FROM Usuario u WHERE u.id = :usuarioId", String.class);
            usuarioQuery.setParameter("usuarioId", this.usuario.getId());
            return usuarioQuery.getSingleResult();
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

