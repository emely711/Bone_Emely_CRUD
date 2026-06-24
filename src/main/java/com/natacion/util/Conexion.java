package com.natacion.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {


    private static final String URL      = "jdbc:postgresql://localhost:5432/natacion";
    private static final String USUARIO  = "postgres";
    private static final String PASSWORD = "Skyemely2004";

    private static Conexion instancia;
    private Connection conexion;


    private Conexion() {
        try {
            conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            System.out.println("✔ Conexión exitosa a la base de datos.");
        } catch (SQLException e) {
            System.err.println("✘ Error al conectar: " + e.getMessage());
        }
    }

    public static Conexion getInstancia() {
        if (instancia == null) {
            instancia = new Conexion();
        }
        return instancia;
    }

    public Connection getConexion() {
        return conexion;
    }
}
