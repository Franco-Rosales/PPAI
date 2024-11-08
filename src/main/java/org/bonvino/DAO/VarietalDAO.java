package org.bonvino.DAO;
import org.bonvino.Models.Varietal;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

public class VarietalDAO {

    @PersistenceContext
    private EntityManager entityManager;

    // Guardar un nuevo varietal
    @Transactional
    public void guardarVarietal(Varietal varietal) {
        entityManager.persist(varietal);
    }

    // Obtener un varietal por su ID
    public Varietal obtenerVarietal(Long id) {
        return entityManager.find(Varietal.class, id);
    }

    // Obtener todos los varietales
    public List<Varietal> obtenerTodosLosVarietales() {
        return entityManager.createQuery("SELECT v FROM Varietal v", Varietal.class).getResultList();
    }

    // Obtener varietales por vino
    public List<Varietal> obtenerVarietalesPorVino(Long vinoId) {
        return entityManager.createQuery("SELECT v FROM Varietal v WHERE v.vino.id = :vinoId", Varietal.class)
                .setParameter("vinoId", vinoId)
                .getResultList();
    }

    // Actualizar un varietal existente
    @Transactional
    public void actualizarVarietal(Varietal varietal) {
        entityManager.merge(varietal);
    }

    // Eliminar un varietal por su ID
    @Transactional
    public void eliminarVarietal(Long id) {
        Varietal varietal = obtenerVarietal(id);
        if (varietal != null) {
            entityManager.remove(varietal);
        }
    }
}

