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
    
}

    
