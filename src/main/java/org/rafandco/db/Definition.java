package org.rafandco.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Clase encargada de realizar operaciones de definición de la base de datos.
 * Incluye métodos relacionados con la creación de estructuras como tablas.
 * Utiliza una conexión JDBC proporcionada desde el exterior.
 */
public class Definition {
    /**
     * Conexión activa utilizada para ejecutar sentencias de definición de datos.
     */
    private final Connection connection;

    /**
     * Constructor que recibe una conexión ya establecida.
     *
     * @param connection conexión JDBC para ejecutar sentencias DDL.
     */
    public Definition(Connection connection) {
        this.connection = connection;
    }


    /**
     * Crea la tabla tareas en la base de datos si esta no existe.
     * Utiliza una sentencia DDL y un Statement estándar.
     * En caso de error se captura la excepción y se imprime el stack trace.
     */
    public void creaTable() {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS tareas(\n" +
                    "    id SERIAL PRIMARY KEY,\n" +
                    "    titulo VARCHAR(150) NOT NULL,\n" +
                    "    descripcion TEXT,\n" +
                    "    completada BOOLEAN NOT NULL DEFAULT FALSE,\n" +
                    "    fechaCreacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP\n" +
                    ");");
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void eliminarTabla() {
        try (Statement stmt = connection.createStatement())  {
            stmt.executeUpdate("DROP TABLE IF EXISTS tareas");
            System.out.println("Goma de borrar tareas");
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}