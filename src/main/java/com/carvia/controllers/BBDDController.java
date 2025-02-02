package com.carvia.controllers;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public final class BBDDController {

    private Dotenv dotenv = Dotenv.load();
    private String mode = dotenv.get("MODE");
    private String URL = dotenv.get("DB_URL");
    private String USER = dotenv.get("USER_DB");
    private String PASSWORD = dotenv.get("PASS_DB");
    private static BBDDController bbddInstance;
    private Connection connectionBBDD;

    private BBDDController() {
        try {
            connectionBBDD = mode.equals("dev") ? DriverManager.getConnection(URL, USER, PASSWORD)
                    : DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static BBDDController getInstance() {
        if (bbddInstance == null) {
            synchronized (BBDDController.class) {
                if (bbddInstance == null) {
                    bbddInstance = new BBDDController();
                }
            }
        }
        return bbddInstance;
    }

    public Connection getConnection() {
        return connectionBBDD;
    }

    public void closeConnection() {
        if (connectionBBDD != null) {
            try {
                connectionBBDD.close();
            } catch (SQLException e) {
                e.printStackTrace();

                throw new RuntimeException("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }

}
