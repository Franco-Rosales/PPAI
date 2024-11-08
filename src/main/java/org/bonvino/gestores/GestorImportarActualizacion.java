package org.bonvino.gestores;

import org.bonvino.Models.*;
import org.bonvino.interfaces.IObservadorActualizacionDeBodega;
import org.bonvino.interfaces.ISujetoActualizacionDeBodega;
import org.bonvino.pantallas.PantallaImportarActualizacion;

import javax.persistence.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GestorImportarActualizacion  implements ISujetoActualizacionDeBodega {
    private EntityManagerFactory emf;


    public GestorImportarActualizacion() {
        this.emf = Persistence.createEntityManagerFactory("wine_persistence_unit");
    }

    public void opcionActualizacionVinos(PantallaImportarActualizacion pantalla){
        this.buscarBodegasActualizacion(pantalla);
    }

    public void buscarBodegasActualizacion(PantallaImportarActualizacion pantalla){
        EntityManager em = emf.createEntityManager();
        List<String> bodegasConActualizacion = new ArrayList<>();
        Date fechaActual = this.getFechaHoraActual();
        try{
            TypedQuery<Bodega> query = em.createQuery(
                    "SELECT b FROM Bodega b WHERE b.fechaUltimaActualizacion < :fechaActual", Bodega.class);
            query.setParameter("fechaActual", fechaActual);
            List<Bodega> listaBodegas = query.getResultList();
            for (Bodega bodega : listaBodegas) {
                if (bodega.tieneActualizacion(fechaActual)) {
                    //Todo: cambiar en el diagrama de secuencia el getDatos por el getNombre
                    bodegasConActualizacion.add(bodega.getNombre());
                }
            }
        }finally {
            em.close();
        }
        pantalla.mostrarBodegasActualizables(bodegasConActualizacion);
    }

    private Date getFechaHoraActual() {
        //Todo: elmine este atributo del gestor
        return new Date(); // Devuelve la fecha actual
    }

    public void tomarSeleccionBodega(String bodegaSeleccionada, PantallaImportarActualizacion pantalla) {
        obtenerActualizacionesBodega(bodegaSeleccionada, pantalla);
    }

    public void obtenerActualizacionesBodega(String bodegaSeleccionada, PantallaImportarActualizacion pantalla){
        EntityManager em = emf.createEntityManager();
        ApiActualizaciones apiActualizaciones = new ApiActualizaciones();
        List<Vino> vinosActualizacion = apiActualizaciones.obtenerActualizaciones(bodegaSeleccionada);

        List<String> vinosActualizadosOCreados = new ArrayList<>();

        try {
            for (Vino vino : vinosActualizacion) {
                // Busca si ya existe un vino con el mismo nombre, añada y bodega
                TypedQuery<Vino> query = em.createQuery(
                        "SELECT v FROM Vino v WHERE v.nombre = :nombre AND v.aniada = :aniada AND v.bodega.nombre = :bodegaNombre",
                        Vino.class);
                query.setParameter("nombre", vino.getNombre());
                query.setParameter("aniada", vino.getAniada());
                query.setParameter("bodegaNombre", bodegaSeleccionada);

                List<Vino> resultado = query.getResultList();

                em.getTransaction().begin();
                if (!resultado.isEmpty()) {
                    // Si el vino ya existe, actualiza sus datos
                    actualizarVinoExistente(resultado.get(0), vino);
                    vinosActualizadosOCreados.add(resultado.get(0).formatoResumen());
                } else {
                    // Si el vino no existe, crea un nuevo registro
                    Varietal varietal = this.buscarVarietal(vino);
                    List<Maridaje> maridajes = this.buscarMaridaje(vino);
                    this.crearVinos(vino, varietal, maridajes);
                    vinosActualizadosOCreados.add(vino.formatoResumen());
                }
                em.getTransaction().commit();
            }
        } finally {
            em.close();
            pantalla.mostarResumenBodegasActualizadasCreadas(bodegaSeleccionada,vinosActualizadosOCreados);
        }
    }

    public void actualizarVinoExistente(Vino vinoExistente,Vino vinoActualizado){
        Bodega bodega = vinoExistente.getBodega();
        bodega.actualizarVinos(vinoExistente, vinoActualizado);
    }

    public Varietal buscarVarietal (Vino vino){
        Varietal varietalActualizado = vino.getVarietal();
        return varietalActualizado != null ? varietalActualizado.esVarietalActualizacion() : null;
    }

    public List<Maridaje> buscarMaridaje(Vino vino){
        List<Maridaje> maridajesActualizados = new ArrayList<>();
        List<Maridaje> maridajes = vino.getMaridajes();
        for(Maridaje maridaje : maridajes){
            Maridaje maridajeActualizado = maridaje.esMaridajeActualizacion();
            if(maridajeActualizado != null){

                maridajesActualizados.add(maridajeActualizado);
            }
        }
        return maridajesActualizados;
    }

    public void crearVinos(Vino vino, Varietal varietal, List<Maridaje> maridajes){
        // Todo: Sacar el segundo loop de creacion de vinos
        Bodega bodega = vino.getBodega();
        bodega.crearVino(vino.getNombre(), vino.getImagenEtiqueta(), vino.getPrecioARS(), vino.getNotaCataBodega(), vino.getAniada(), varietal, maridajes);
    }

    public void buscarEnofiloSuscriptosABodega(String bodegaSeleccionada) {
        EntityManager em = emf.createEntityManager();
        List<String> nombresEnofilosSuscriptos = new ArrayList<>();

        try {
            // 1. Buscar la bodega por nombre
            TypedQuery<Bodega> bodegaQuery = em.createQuery(
                    "SELECT b FROM Bodega b WHERE b.nombre = :nombre", Bodega.class);
            bodegaQuery.setParameter("nombre", bodegaSeleccionada);
            Bodega bodega = bodegaQuery.getSingleResult();

            if (bodega != null) {
                // 2. Buscar todos los registros de la tabla intermedia Siguiendo que tengan el mismo bodega_id
                TypedQuery<Siguiendo> siguiendoQuery = em.createQuery(
                        "SELECT s FROM Siguiendo s WHERE s.bodega.id = :bodegaId", Siguiendo.class);
                siguiendoQuery.setParameter("bodegaId", bodega.getId());
                List<Siguiendo> registrosSiguiendo = siguiendoQuery.getResultList();

                // 3. Obtener los enofilo_id de esas suscripciones y luego buscar los Enofilos
                for (Siguiendo siguiendo : registrosSiguiendo) {
                    Long enofiloId = siguiendo.getEnofilo().getId(); // Asumimos que hay un getter para el Enofilo

                    // Ahora obtenemos el nombre del enófilo utilizando su id
                    Enofilo enofilo = em.find(Enofilo.class, enofiloId);
                    if (enofilo != null) {
                        String nombreUsuario = enofilo.obtenerNombreEnofiloSuscripto();
                        nombresEnofilosSuscriptos.add(nombreUsuario);
                    }
                }
            }
        } catch (NoResultException e) {
            System.out.println("No se encontró la bodega seleccionada: " + bodegaSeleccionada);
        } finally {
            em.close();
        }

        //return nombresEnofilosSuscriptos;
    }


    @Override
    public void suscribir(IObservadorActualizacionDeBodega observador) {

    }

    @Override
    public void quitar(IObservadorActualizacionDeBodega observador) {

    }

    @Override
    public void notificarNovedades() {

    }
}
