package org.bonvino.DAO;

import org.bonvino.Models.Maridaje;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

public class MaridajeDAO {

    @PersistenceContext
    private EntityManager entityManager;

    // Guardar un nuevo maridaje
    @Transactional
    public void guardarMaridaje(Maridaje maridaje) {
        entityManager.persist(maridaje);
    }

    // Obtener un maridaje por su ID
    public Maridaje obtenerMaridaje(Long id) {
        return entityManager.find(Maridaje.class, id);
    }

    // Obtener todos los maridajes
    public List<Maridaje> obtenerTodosLosMaridajes() {
        return entityManager.createQuery("SELECT m FROM Maridaje m", Maridaje.class).getResultList();
    }

    // Obtener maridajes por vino
    public List<Maridaje> obtenerMaridajesPorVino(Long vinoId) {
        return entityManager.createQuery("SELECT m FROM Maridaje m WHERE m.vino.id = :vinoId", Maridaje.class)
                .setParameter("vinoId", vinoId)
                .getResultList();
    }

    // Actualizar un maridaje existente
    @Transactional
    public void actualizarMaridaje(Maridaje maridaje) {
        entityManager.merge(maridaje);
    }

    // Eliminar un maridaje por su ID
    @Transactional
    public void eliminarMaridaje(Long id) {
        Maridaje maridaje = obtenerMaridaje(id);
        if (maridaje != null) {
            entityManager.remove(maridaje);
        }
    }
}

