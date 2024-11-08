package org.bonvino.DAO;

import org.bonvino.Models.Vino;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

public class VinoDAO {

    @PersistenceContext
    private EntityManager entityManager;

    // Guardar un nuevo vino
    @Transactional
    public void guardarVino(Vino vino) {
        entityManager.persist(vino);
    }

    // Buscar un vino por su ID
    public Vino obtenerVino(Long id) {
        return entityManager.find(Vino.class, id);
    }

    // Obtener todos los vinos
    public List<Vino> obtenerTodosLosVinos() {
        return entityManager.createQuery("SELECT v FROM Vino v", Vino.class).getResultList();
    }

    // Obtener vinos por bodega
    public List<Vino> obtenerVinosPorBodega(Long bodegaId) {
        return entityManager.createQuery("SELECT v FROM Vino v WHERE v.bodega.id = :bodegaId", Vino.class)
                .setParameter("bodegaId", bodegaId)
                .getResultList();
    }

    // Actualizar un vino existente
    @Transactional
    public void actualizarVino(Vino vino) {
        entityManager.merge(vino);
    }

    // Eliminar un vino por su ID
    @Transactional
    public void eliminarVino(Long id) {
        Vino vino = obtenerVino(id);
        if (vino != null) {
            entityManager.remove(vino);
        }
    }
}

