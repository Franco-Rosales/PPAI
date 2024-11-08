package org.bonvino.DAO;

import org.bonvino.Models.Siguiendo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

public class SiguiendoDAO {

    @PersistenceContext
    private EntityManager entityManager;

    // Guardar un nuevo seguimiento
    @Transactional
    public void guardarSiguiendo(Siguiendo siguiendo) {
        entityManager.persist(siguiendo);
    }

    // Obtener un seguimiento por su ID
    public Siguiendo obtenerSiguiendo(Long id) {
        return entityManager.find(Siguiendo.class, id);
    }

    // Obtener todos los seguimientos
    public List<Siguiendo> obtenerTodosLosSiguiendo() {
        return entityManager.createQuery("SELECT s FROM Siguiendo s", Siguiendo.class).getResultList();
    }

    // Actualizar un seguimiento existente
    @Transactional
    public void actualizarSiguiendo(Siguiendo siguiendo) {
        entityManager.merge(siguiendo);
    }

    // Eliminar un seguimiento por su ID
    @Transactional
    public void eliminarSiguiendo(Long id) {
        Siguiendo siguiendo = obtenerSiguiendo(id);
        if (siguiendo != null) {
            entityManager.remove(siguiendo);
        }
    }
}

