package org.rafandco.dao;

import org.rafandco.db.SingletonConnection;
import org.rafandco.model.Tarea;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class TareaDAO {

//    private static Connection connection;
//
//    public TareaDAO(Connection connection) {
//       connection = connection;
//    }

    public void insertar(Tarea tarea) {
        String titulo = tarea.getTitulo();
        String descripcion = tarea.getDescripcion();
        boolean completada = tarea.isCompletada();

        String sql = "INSERT INTO tareas (titulo, descripcion, completada) VALUES (?, ?, ?)";
        try (Connection con = SingletonConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) { // PreparedStatement, igual que Statement,
            // pero más seguro y recomendado para consultas
            // Ingresar valores:
            pstmt.setString(1, titulo);
            pstmt.setString(2, descripcion);
            pstmt.setBoolean(3, completada);
            pstmt.executeUpdate(); // Ejecuta la sentencia preparada INSERT, UPDATE, DELETE

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void actualizar(Tarea tarea) {
        // UPDATE tareas SET ... WHERE id = ?
        int id = tarea.getId();
        String titulo = tarea.getTitulo();
        String descripcion = tarea.getDescripcion();
        boolean completada = tarea.isCompletada();
        LocalDate fechaCreacion = tarea.getFechaCreacion();

        String sql = "UPDATE tareas SET titulo = ?, descripcion = ?, completada = ?, fechaCreacion = ? WHERE id = ?";
        try (Connection con = SingletonConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            // Modificar valores:

            pstmt.setString(1, titulo);
            pstmt.setString(2, descripcion);
            pstmt.setBoolean(3, completada);
            pstmt.setDate(4, Date.valueOf(fechaCreacion));
            pstmt.setInt(5, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }

    public void eliminar(int id) {
        String sql = "DELETE FROM tareas WHERE id = ?";
        try (Connection con = SingletonConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            // Eliminar valores:
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public Tarea buscarPorId(int id) {
        // SELECT ... FROM tareas WHERE id = ?
        Tarea tarea = null;

        int id2;
        String titulo;
        String descripcion;
        boolean completada;
        LocalDate fechaCreacion;

        String sql = "SELECT id, titulo, descripcion, completada, fechaCreacion FROM tareas WHERE id = ?";
        try (Connection con = SingletonConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) { // PreparedStatement permite usar parámetros "?"

            pstmt.setInt(1, id); // Sustituye el parámetro ? por el valor de id

            try (ResultSet rs = pstmt.executeQuery()) {
                // ResultSet es un objeto que representa los resultados de la consulta como si fueran una tabla en memoria
                // executeQuery(sql) se usa solo para sentencias SQL que devuelvan resultados

                if (rs.next()) { // Avanza a la primera fila del ResultSet (si existe)
                    // Recoger valores de las columnas:
                    id2 = rs.getInt("id");
                    titulo = rs.getString("titulo");
                    descripcion = rs.getString("descripcion");
                    completada = rs.getBoolean("completada");
                    fechaCreacion = rs.getDate("fechaCreacion").toLocalDate();

                    tarea = new Tarea(id2, titulo, descripcion, completada, fechaCreacion);
                }
                // Si no hay resultados, tarea seguirá siendo null
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return tarea; // Devuelve la tarea encontrada o null si no existe
    }

    public List<Tarea> listarTodas() {
        // SELECT ... FROM tareas
        return null;
    }
}