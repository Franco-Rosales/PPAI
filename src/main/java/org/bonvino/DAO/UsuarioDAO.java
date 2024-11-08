package org.bonvino.DAO;

import org.bonvino.Models.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

public class UsuarioDAO {

    @PersistenceContext
    private EntityManager entityManager;

    // Guardar un nuevo usuario
    @Transactional
    public void guardarUsuario(Usuario usuario) {
        entityManager.persist(usuario);
    }

    // Obtener un usuario por su ID
    public Usuario obtenerUsuario(Long id) {
        return entityManager.find(Usuario.class, id);
    }

    // Obtener todos los usuarios
    public List<Usuario> obtenerTodosLosUsuarios() {
        return entityManager.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();
    }

    // Actualizar un usuario existente
    @Transactional
    public void actualizarUsuario(Usuario usuario) {
        entityManager.merge(usuario);
    }

    // Eliminar un usuario por su ID
    @Transactional
    public void eliminarUsuario(Long id) {
        Usuario usuario = obtenerUsuario(id);
        if (usuario != null) {
            entityManager.remove(usuario);
        }
    }
}

