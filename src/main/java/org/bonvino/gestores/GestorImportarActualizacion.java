package org.bonvino.gestores;

import org.bonvino.Models.*;
import org.bonvino.pantallas.PantallaImportarActualizacion;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GestorImportarActualizacion {
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

        List<String> resumenVinosActualizados = new ArrayList<>();

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
                    resumenVinosActualizados.add(resultado.get(0).formatoResumen());
                } else {
                    // Si el vino no existe, crea un nuevo registro
                    Varietal varietal = this.buscarVarietal(vino);
                    List<Maridaje> maridajes = this.buscarMaridaje(vino);
                    this.crearVinos(vino, varietal, maridajes);
                    resumenVinosActualizados.add(vino.formatoResumen());
                }
                em.getTransaction().commit();
            }
        } finally {
            em.close();
            pantalla.mostarResumenBodegasActualizadasCreadas(resumenVinosActualizados);
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


}
