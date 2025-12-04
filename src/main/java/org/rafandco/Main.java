package org.rafandco;

import org.rafandco.dao.TareaDAO;
import org.rafandco.db.Definition;
import org.rafandco.db.SingletonConnection;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // Crear la conexión
        Connection connection = SingletonConnection.getConnection();

        // Secuencia de acciones de la app
        // Sentencias DDL (definition)
        Definition definition = new Definition(connection);
        definition.creaTable();

        TareaDAO tareaDAO = new TareaDAO(connection);

        /*
        // Sentencias DML (Modification)
        Modification modification = new Modification(connection);
        // Inserts
        modification.insert("Lavar", "Limpiar ropa para evento", false, 1);
        modification.insert("Tender", "Secar ropa para evento", false, 2);
        modification.insert("Planchar", "Preparar ropa para evento", false, 3);

        // Consulta
        Select select = new Select(connection);
        List<Map<String, Object>> consulta1 = select.getAll();
        System.out.println(consulta1);

        // Update
        modification.update(1,"Lavar", "Limpiar ropa para evento", true, 1);

        // Consulta del update
        Map<String, Object> consulta2 = select.getById(1);
        System.out.println(consulta2);*/

        // Cerrar la conexión
        SingletonConnection.closeConnection();
    }
}
