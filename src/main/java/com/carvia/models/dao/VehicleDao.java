package com.carvia.models.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.carvia.controllers.BBDDController;
import com.carvia.models.vo.AdvertisementVo;
import com.carvia.models.vo.UserVo;
import com.carvia.models.vo.VehicleVo;

public class VehicleDao {

    private final Connection connection;
    private static final Logger logger = LogManager.getLogger(UserDao.class);

    public VehicleDao() {
        this.connection = BBDDController.getInstance().getConnection();
    }

    public boolean insertVehicle(VehicleVo vehiculo) {

        String query = "INSERT INTO Vehicles (make, model, year, mileage, fuel_type, transmission) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, vehiculo.getMake()); // make
            statement.setString(2, vehiculo.getModel()); // model
            statement.setInt(3, vehiculo.getYear()); // year
            statement.setInt(4, vehiculo.getKilometers()); // mileage
            statement.setString(5, vehiculo.getCombustion()); // fuel_type
            statement.setString(6, vehiculo.getShifter()); // transmission

            logger.info("Insertando vehículo: " + vehiculo.getMake() + " " + vehiculo.getModel());
            return statement.executeUpdate() > 0; // Retorna true si se insertó al menos un registro
        } catch (SQLException e) {
            logger.error("Error al insertar el vehículo: " + vehiculo.getMake() + " " + vehiculo.getModel());
            e.printStackTrace();
            return false;
        }
    }

    /* 
    public boolean insertAnuncio(AdvertisementVo anuncio) {
        // Modificamos la consulta para que 'fecha' tome el valor de CURRENT_DATE
        String query = "INSERT INTO Anuncios (fecha, descripcion, precio, urlFoto) VALUES (CURRENT_DATE, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, anuncio.getDescripcion());
            statement.setDouble(2, anuncio.getPrecio());
            statement.setString(3, anuncio.getUrlFoto());
    
            return statement.executeUpdate() > 0; // Retorna true si se insertó al menos un registro
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    */


    public List<VehicleVo> filtrarVehiculos(String marca, String provincia, String precio) {
        List<VehicleVo> vehiculos = new ArrayList<>();

        // Ajusta la consulta SQL según tu esquema de base de datos
        String query = "SELECT * FROM Vehicles WHERE make = ? AND provincia = ? AND precio = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, marca);
            statement.setString(2, provincia);
            statement.setString(3, precio);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                // Crea un nuevo objeto VehicleVo con los datos obtenidos
                VehicleVo vehiculo = new VehicleVo();
                AdvertisementVo anuncio = new AdvertisementVo();
                vehiculo.setMake(rs.getString("make"));
                vehiculo.setModel(rs.getString("model"));
                //anuncio.setProvincia(rs.getString("provincia"));
                anuncio.getPriceBusq(rs.getString("precio"));
                // Añade el objeto a la lista
                vehiculos.add(vehiculo);
            }
        } catch (SQLException e) {
            logger.error("Error al filtrar los vehículos: ", e);
            e.printStackTrace();
        }

        return vehiculos;
    }
}

    
