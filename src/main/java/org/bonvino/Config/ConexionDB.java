package org.bonvino.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    // Instancia única de la conexión
    private static ConexionDB instancia;
    private Connection conexion;

    // Configuración de la base de datos
    private final String url = "jdbc:postgresql://localhost:5432/bonvino";
    private final String usuario = "postgres";
    private final String contrasena = "postgres";

    // Constructor privado para evitar instanciación
    private ConexionDB() {
        try {
            // Carga del driver de PostgreSQL
            Class.forName("org.postgresql.Driver");
            // Establecer la conexión
            this.conexion = DriverManager.getConnection(url, usuario, contrasena);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al conectar a la base de datos", e);
        }
    }

    // Método público y estático para obtener la instancia única
    public static ConexionDB getInstancia() {
        if (instancia == null) {
            instancia = new ConexionDB();
        }
        return instancia;
    }

    // Método para obtener la conexión
    public Connection getConexion() {
        return conexion;
    }

    // Método para cerrar la conexión cuando ya no sea necesaria
    public void cerrarConexion() {
        if (conexion != null) {
            try {
                conexion.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

