package org.bonvino.DAO;

import org.bonvino.Models.Bodega;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

public class BodegaDAO {

    @PersistenceContext
    private EntityManager entityManager;

    // Guardar una nueva bodega
    @Transactional
    public void guardarBodega(Bodega bodega) {
        entityManager.persist(bodega);
    }

    // Buscar una bodega por su ID
    public Bodega obtenerBodega(Long id) {
        return entityManager.find(Bodega.class, id);
    }

    // Obtener todas las bodegas
    public List<Bodega> obtenerTodasLasBodegas() {
        return entityManager.createQuery("SELECT b FROM Bodega b", Bodega.class).getResultList();
    }

    // Actualizar una bodega existente
    @Transactional
    public void actualizarBodega(Bodega bodega) {
        entityManager.merge(bodega);
    }

    // Eliminar una bodega por su ID
    @Transactional
    public void eliminarBodega(Long id) {
        Bodega bodega = obtenerBodega(id);
        if (bodega != null) {
            entityManager.remove(bodega);
        }
    }
}

