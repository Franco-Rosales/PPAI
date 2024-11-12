package org.bonvino.gestores;

import org.bonvino.Models.*;
import org.bonvino.interfaces.IObservadorActualizacionDeBodega;
import org.bonvino.interfaces.ISujetoActualizacionDeBodega;
import org.bonvino.pantallas.InterfazNotificacion;
import org.bonvino.pantallas.PantallaImportarActualizacion;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GestorImportarActualizacion  implements ISujetoActualizacionDeBodega {
    private EntityManagerFactory emf;

    private String bodegaSeleccionada;

    private List<String> vinosActualizadosOCreados = new ArrayList<>();

    private Date fechaHoraActual;

    private List<String> usuariosEnofilos = new ArrayList<>();
    private List<IObservadorActualizacionDeBodega> observadores = new ArrayList<>();


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
                    bodegasConActualizacion.add(bodega.getNombre());
                }
            }
        }finally {
            em.close();
        }
        pantalla.mostrarBodegasActualizables(bodegasConActualizacion);
    }

    private Date getFechaHoraActual() {
        this.fechaHoraActual = new Date();
        return fechaHoraActual; // Devuelve la fecha actual
    }

    public void tomarSeleccionBodega(String bodegaSeleccionada, PantallaImportarActualizacion pantalla) {
        this.bodegaSeleccionada = bodegaSeleccionada;
        obtenerActualizacionesBodega(bodegaSeleccionada, pantalla);
    }

    public void obtenerActualizacionesBodega(String bodegaSeleccionada, PantallaImportarActualizacion pantalla){
        EntityManager em = emf.createEntityManager();
        ApiActualizaciones apiActualizaciones = new ApiActualizaciones();
        List<Vino> vinosActualizacion = apiActualizaciones.obtenerActualizaciones(bodegaSeleccionada);
        this.vinosActualizadosOCreados.clear();

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
                    this.crearVino(vino, varietal, maridajes);
                    vinosActualizadosOCreados.add(vino.formatoResumen());
                }
                em.getTransaction().commit();
            }
        } finally {
            em.close();
            boolean continuar = pantalla.mostrarResumenActualizacionesBodega(bodegaSeleccionada, vinosActualizadosOCreados);
            if (!continuar) {
                return;
            }
            buscarEnofilosSuscriptosABodega(bodegaSeleccionada);
        }
    }

    public void actualizarVinoExistente(Vino vinoExistente,Vino vinoActualizado){
        System.out.println("Actualizar Vino Existente");
        Bodega bodega = vinoExistente.getBodega();
        bodega.actualizarVino(vinoExistente, vinoActualizado);
    }

    public Varietal buscarVarietal (Vino vino){
        System.out.println("Buscar Varietal");
        Varietal varietalActualizado = vino.getVarietal();
        return varietalActualizado != null ? varietalActualizado.esVarietalActualizacion() : null;
    }

    public List<Maridaje> buscarMaridaje(Vino vino){
        System.out.println("Buscar Maridaje");
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

    public void crearVino(Vino vino, Varietal varietal, List<Maridaje> maridajes){
        System.out.println("Crear Vinos");
        Bodega bodega = vino.getBodega();
        bodega.crearVino(vino.getNombre(), vino.getImagenEtiqueta(), vino.getPrecioARS(), vino.getNotaCataBodega(), vino.getAniada(), varietal, maridajes);
    }



    public void buscarEnofilosSuscriptosABodega(String bodegaSeleccionada){
        EntityManager em = emf.createEntityManager();


        // buscar todos los enofilos de la base de datos
        try{
            TypedQuery<Enofilo> query = em.createQuery(
                    "SELECT e FROM Enofilo e", Enofilo.class);
            List<Enofilo> enofilos = query.getResultList();

            // bucle por cada enofilo encontrado

            for(Enofilo enofilo: enofilos){
                String nombreEnofilo = enofilo.obtenerNombreEnofiloSuscripto(bodegaSeleccionada);
                if (nombreEnofilo != null){
                    usuariosEnofilos.add(nombreEnofilo);
                }
            }

            //Aplicacion del patron
            IObservadorActualizacionDeBodega observador = new InterfazNotificacion();
            suscribir(observador);
            notificarNovedades();

        }catch (NoResultException e) {
            System.out.println("No se encontraron enofilos: ");
        }finally {
            em.close();
        }

    }


    @Override
    public void suscribir(IObservadorActualizacionDeBodega observador) {
        observadores.add(observador);
    }

    @Override
    public void quitar(IObservadorActualizacionDeBodega observador) {

    }

    @Override
    public void notificarNovedades() {
        for(IObservadorActualizacionDeBodega observador: observadores){
            observador.actualizarNovedades(bodegaSeleccionada, vinosActualizadosOCreados, usuariosEnofilos, fechaHoraActual);
        }

    }
}
