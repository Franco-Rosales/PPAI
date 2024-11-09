package org.bonvino.Models;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Bodega") // El nombre de la tabla en la base de datos
public class Bodega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "fechaUltima_actualizacion")
    @Temporal(TemporalType.DATE)
    private Date fechaUltimaActualizacion;

    @Column(name = "periodicidad_actualizacion", nullable = false)
    private int periodicidadActualizacion;

    @Column(name = "descripcion")
    private String descripcion;


    @Column(name = "coordenadas_ubicacion")
    private String coordenadasUbicacion;

    @Column(name = "historia")
    private String historia;

    // Relación OneToMany con Vino
    @OneToMany(mappedBy = "bodega", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Vino> vinos;

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("wine_persistence_unit");


    // Constructor
    public Bodega() {
    }

    public boolean tieneActualizacion(Date fechaActual){
        System.out.println("Tiene actualizacion");
        Calendar cal = Calendar.getInstance();
        cal.setTime(fechaUltimaActualizacion);
        cal.add(Calendar.DAY_OF_YEAR, periodicidadActualizacion);
        Date fechaProximaActualizacion = cal.getTime();
        return !fechaActual.before(fechaProximaActualizacion);
    }

    public void actualizarVinos(Vino vinoExistente, Vino vinoActualizado){
        vinoExistente.setImagenEtiqueta(vinoActualizado.getImagenEtiqueta());
        vinoExistente.setPrecioARS(vinoActualizado.getPrecioARS());
        vinoExistente.setNotaCataBodega(vinoActualizado.getNotaCataBodega());
    }



    public void crearVino(String nombre, String imagenEtiqueta, double precioARS, double notaCataBodega, int aniada, Varietal varietal, List<Maridaje> maridajes) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            // Crear el objeto Vino usando el constructor
            Vino vino = new Vino(nombre, imagenEtiqueta, precioARS, notaCataBodega, aniada, this, varietal, maridajes);

            // Persistir el nuevo vino
            em.persist(vino);

            // Añadir el vino a la lista de vinos de la bodega
            this.getVinos().add(vino);

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
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

    public Date getFechaUltimaActualizacion() {
        return fechaUltimaActualizacion;
    }

    public void setFechaUltimaActualizacion(Date fechaUltimaActualizacion) {
        this.fechaUltimaActualizacion = fechaUltimaActualizacion;
    }

    public int getPeriodicidadActualizacion() {
        return periodicidadActualizacion;
    }

    public void setPeriodicidadActualizacion(int periodicidadActualizacion) {
        this.periodicidadActualizacion = periodicidadActualizacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCoordenadasUbicacion() {
        return coordenadasUbicacion;
    }

    public void setCoordenadasUbicacion(String coordenadasUbicacion) {
        this.coordenadasUbicacion = coordenadasUbicacion;
    }

    public String getHistoria() {
        return historia;
    }

    public void setHistoria(String historia) {
        this.historia = historia;
    }

    public List<Vino> getVinos() {
        return vinos;
    }

    public void setVinos(List<Vino> vinos) {
        this.vinos = vinos;
    }
}
