package org.bonvino.DAO;

import org.bonvino.Models.TipoUva;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

public class TipoUvaDAO {

    @PersistenceContext
    private EntityManager entityManager;

    // Guardar un nuevo tipo de uva
    @Transactional
    public void guardarTipoUva(TipoUva tipoUva) {
        entityManager.persist(tipoUva);
    }

    // Obtener un tipo de uva por su ID
    public TipoUva obtenerTipoUva(Long id) {
        return entityManager.find(TipoUva.class, id);
    }

    // Obtener todos los tipos de uvas
    public List<TipoUva> obtenerTodosLosTiposDeUvas() {
        return entityManager.createQuery("SELECT t FROM TipoUva t", TipoUva.class).getResultList();
    }

    // Actualizar un tipo de uva existente
    @Transactional
    public void actualizarTipoUva(TipoUva tipoUva) {
        entityManager.merge(tipoUva);
    }

    // Eliminar un tipo de uva por su ID
    @Transactional
    public void eliminarTipoUva(Long id) {
        TipoUva tipoUva = obtenerTipoUva(id);
        if (tipoUva != null) {
            entityManager.remove(tipoUva);
        }
    }
}

