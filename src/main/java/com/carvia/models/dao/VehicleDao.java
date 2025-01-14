package com.carvia.models.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.carvia.controllers.BBDDController;
import com.carvia.models.vo.VehicleVo;

public class VehicleDao {

    private final Connection connection;
    private static final Logger logger = LogManager.getLogger(UserDao.class);

    public VehicleDao() {
        this.connection = BBDDController.getInstance().getConnection();
    }

    public int insertVehiculo(VehicleVo vehiculo) {
        String query = "INSERT INTO Vehicles (marca, model, year, kilometers, fuel_type, transmission) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, vehiculo.getMarca());
            statement.setString(2, vehiculo.getModelo());
            statement.setInt(3, vehiculo.getAnio());
            statement.setInt(4, vehiculo.getKilometraje());
            statement.setString(5, vehiculo.getTipoCombustible());
            statement.setString(6, vehiculo.getTransmision());
    
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1); // Retorna el ID del vehículo recién creado
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Indica un error si no se pudo insertar
    }

    public VehicleVo getVehicleById(int vehicleId) {
        String query = "SELECT * FROM Vehicles WHERE idVe = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, vehicleId);
            ResultSet resultSet = statement.executeQuery();
    
            if (resultSet.next()) {
                VehicleVo vehicle = new VehicleVo();
                vehicle.setId(resultSet.getInt("idVe"));
                vehicle.setMarca(resultSet.getString("marca")); // Marca del vehículo
                vehicle.setModelo(resultSet.getString("model"));
                vehicle.setAnio(resultSet.getInt("year"));
                vehicle.setKilometraje(resultSet.getInt("kilometers"));
                vehicle.setTipoCombustible(resultSet.getString("fuel_type"));
                vehicle.setTransmision(resultSet.getString("transmission"));
                return vehicle;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Retorna null si no se encuentra el vehículo
    }
    
    
}

    
