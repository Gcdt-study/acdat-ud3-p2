package org.rafandco;

import org.rafandco.db.Definition;
import org.rafandco.db.SingletonConnection;
import org.rafandco.dao.TareaDAO;
import org.rafandco.model.Tarea;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Crear la conexión
        Connection connection = SingletonConnection.getConnection();

        Definition definition = new Definition(connection);
       // definition.eliminarTabla();
        definition.creaTable();


        TareaDAO dao = new TareaDAO();
        Scanner sc = new Scanner(System.in);

        boolean salir = false;
        while (!salir) {
            System.out.println("\n===== MENÚ DE TAREAS =====");
            System.out.println("1. Insertar tarea");
            System.out.println("2. Listar todas");
            System.out.println("3. Buscar por ID");
            System.out.println("4. Actualizar tarea");
            System.out.println("5. Eliminar tarea");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");

            int opcion = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1 -> {
                    System.out.print("Título: ");
                    String titulo = sc.nextLine();
                    System.out.print("Descripción: ");
                    String descripcion = sc.nextLine();

                    Tarea nueva = new Tarea(titulo, descripcion, LocalDate.now());
                    dao.insertar(nueva);
                }
                case 2 -> {
                    List<Tarea> tareas = dao.listarTodas();
                    for (Tarea t : tareas) {
                        System.out.println(t);
                    }
                }
                case 3 -> {
                    System.out.print("ID de la tarea: ");
                    int id = sc.nextInt();
                    sc.nextLine();

                    Tarea tarea = dao.buscarPorId(id);
                    if (tarea != null) {
                        System.out.println(tarea);
                    } else {
                        System.out.println("No existe ninguna tarea con ese ID.");
                    }
                }
                case 4 -> {
                        System.out.print("ID de la tarea a marcar como completada: ");
                        int id = sc.nextInt();
                        sc.nextLine(); // limpiar buffer

                        Tarea tarea = dao.buscarPorId(id);

                        if (tarea != null) {
                            tarea.marcarComoCompletada();
                            dao.actualizar(tarea);
                            System.out.println("Tarea marcada como completada: " + tarea);
                        } else {
                            System.out.println("No existe ninguna tarea con ese ID.");
                        }
                    }
                case 5 -> {
                    System.out.print("ID de la tarea a eliminar: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    dao.eliminar(id);
                    System.out.println("Tarea eliminada.");
                }
                case 0 -> {
                    salir = true;
                    SingletonConnection.closeConnection();
                    System.out.println("Saliendo...");
                }
                default -> System.out.println("Opción inválida.");
            }
        }
        sc.close();

        SingletonConnection.closeConnection();
    }
}
