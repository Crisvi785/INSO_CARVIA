package com.carvia.models.dao;

import com.carvia.controllers.BBDDController;
import com.carvia.models.vo.AnuncioVo;
import com.carvia.models.vto.VehicleAdVto;

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

    public boolean insertAnuncio(AnuncioVo anuncio) {
        // Modificamos la consulta para que 'fecha' tome el valor de CURRENT_DATE
        String query = "INSERT INTO Advertisements (idVe, idUs, date, description, price, images, provincia) VALUES (?, ?, CURRENT_DATE, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(3, anuncio.getDescripcion());
            statement.setDouble(4, anuncio.getPrecio());
            statement.setString(5, anuncio.getUrlFoto());
            statement.setInt(1, anuncio.getIdVehiculo()); // Asignar el ID del vehículo (clave foránea)
            statement.setInt(2, anuncio.getIdUsuario());
            statement.setString(6, anuncio.getProvincia());
            return statement.executeUpdate() > 0; // Retorna true si se insertó al menos un registro
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<VehicleAdVto> filtrarAnuncios(String marca, String provincia, Double precioMin, Double precioMax) throws SQLException {
        List<VehicleAdVto> anuncios = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM Advertisements INNER JOIN Vehicles ON Advertisements.idVe = Vehicles.idVe WHERE 1=1");

        if (marca != null && !marca.isEmpty() && !marca.equals("-")) {
            query.append(" AND Vehicles.marca = ?");
        }
        if (provincia != null && !provincia.isEmpty() && !provincia.equals("-")) {
            query.append(" AND Advertisements.provincia = ?");
        }
        query.append(" AND Advertisements.price BETWEEN ? AND ?");

        try (PreparedStatement statement = connection.prepareStatement(query.toString())) {
            int paramIndex = 1;

            if (marca != null && !marca.isEmpty() && !marca.equals("-")) {
                statement.setString(paramIndex++, marca);
            }
            if (provincia != null && !provincia.isEmpty() && !provincia.equals("-")) {
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
                int idAnuncio = resultSet.getInt("idAd");
                int idVehiculo = resultSet.getInt("idVe");
                int idUsuario = resultSet.getInt("idUs");
                String imagesUrl = resultSet.getString("images");
                int kilometraje = resultSet.getInt("kilometers");
                String tipoCombustible = resultSet.getString("fuel_type");
                String transmision = resultSet.getString("transmission");
                
                anuncios.add(new VehicleAdVto(resultMarca, modelo, ano, resultProvincia, descripcion, resultPrecio, idAnuncio, idVehiculo, idUsuario, imagesUrl, kilometraje, tipoCombustible, transmision));
            }
        }

        return anuncios;
    }

    public boolean deleteAnuncio(int idVe){
        String query = "DELETE FROM Advertisements WHERE idVe = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idVe);
            return statement.executeUpdate() > 0; 
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean eliminarAnunciosPorUsuario(int idUsuario) {
        String query = "DELETE FROM Advertisements WHERE idUs = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idUsuario);
            return statement.executeUpdate() > 0; 
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}