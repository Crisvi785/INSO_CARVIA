package com.carvia.models.dao;

import com.carvia.controllers.BBDDController;
import com.carvia.controllers.PaymentController;
import com.carvia.models.vto.VehicleAdVto;

import javafx.scene.control.Button;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnuncioDao {

    private Connection connection;

    public AnuncioDao() {
        // Initialize the connection
        this.connection = BBDDController.getInstance().getConnection();
    }

    public List<VehicleAdVto> filtrarAnuncios(String marca, String provincia, Double precioMin, Double precioMax) throws SQLException {
        List<VehicleAdVto> anuncios = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM Advertisements INNER JOIN Vehicles ON Advertisements.idVe = Vehicles.idVe WHERE 1=1");

        if (marca != null && !marca.isEmpty()) {
            query.append(" AND Vehicles.marca = ?");
        }
        if (provincia != null && !provincia.isEmpty()) {
            query.append(" AND Advertisements.provincia = ?");
        }
        query.append(" AND Advertisements.price BETWEEN ? AND ?");

        try (PreparedStatement statement = connection.prepareStatement(query.toString())) {
            int paramIndex = 1;

            if (marca != null && !marca.isEmpty()) {
                statement.setString(paramIndex++, marca);
            }
            if (provincia != null && !provincia.isEmpty()) {
                statement.setString(paramIndex++, provincia);
            }
            statement.setDouble(paramIndex++, precioMin);
            statement.setDouble(paramIndex++, precioMax);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String resultMarca = resultSet.getString("marca");
                String modelo = resultSet.getString("model");
                int ano = resultSet.getInt("year");
                String resultProvincia = resultSet.getString("provincia");
                String descripcion = resultSet.getString("description");
                double resultPrecio = resultSet.getDouble("price");
                /*
                Button comprarButton = new Button("Comprar");
                // Add action to the button
                // Instancia del controlador de pagos
                PaymentController paymentController = new PaymentController();

                // Configurar la acción del botón
                comprarButton.setOnAction(event -> {
                    // Obtener el vehículo seleccionado (lógica específica para este botón)
                    VehicleAdVto vehiculo = obtenerVehiculoSeleccionado();

                    if (vehiculo != null) {
                        System.out.println("Vehículo seleccionado para compra: " + vehiculo.getMarca() + " " + vehiculo.getModelo());

                        // Llamar al método para realizar la compra
                        paymentController.realizarCompra(vehiculo);
                    }
                });
                */
                anuncios.add(new VehicleAdVto(resultMarca, modelo, ano, resultProvincia, descripcion, resultPrecio));
            }
        }

        return anuncios;
    }
}