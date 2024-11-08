package org.bonvino.DAO;

import org.bonvino.Models.Enofilo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

public class EnofiloDAO {

    @PersistenceContext
    private EntityManager entityManager;

    // Guardar un nuevo enofilo
    @Transactional
    public void guardarEnofilo(Enofilo enofilo) {
        entityManager.persist(enofilo);
    }

    // Obtener un enofilo por su ID
    public Enofilo obtenerEnofilo(Long id) {
        return entityManager.find(Enofilo.class, id);
    }

    // Obtener todos los enofilos
    public List<Enofilo> obtenerTodosLosEnofilos() {
        return entityManager.createQuery("SELECT e FROM Enofilo e", Enofilo.class).getResultList();
    }

    // Actualizar un enofilo existente
    @Transactional
    public void actualizarEnofilo(Enofilo enofilo) {
        entityManager.merge(enofilo);
    }

    // Eliminar un enofilo por su ID
    @Transactional
    public void eliminarEnofilo(Long id) {
        Enofilo enofilo = obtenerEnofilo(id);
        if (enofilo != null) {
            entityManager.remove(enofilo);
        }
    }
}

