package com.carvia.models.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.carvia.controllers.BBDDController;
import com.carvia.models.vo.AnuncioVo;
import com.carvia.models.vo.UserVo;
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

    public boolean insertAnuncio(AnuncioVo anuncio) {
        UserVo user = new UserVo();
        VehicleVo vehiculo = new VehicleVo();
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

    /* 
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
                AnuncioVo anuncio = new AnuncioVo();
                vehiculo.setMarca(rs.getString("make"));
                vehiculo.setModelo(rs.getString("model"));
                //anuncio.setProvincia(rs.getString("provincia"));
                anuncio.getPrecioBusq(rs.getString("precio"));
                // Añade el objeto a la lista
                vehiculos.add(vehiculo);
            }
        } catch (SQLException e) {
            logger.error("Error al filtrar los vehículos: ", e);
            e.printStackTrace();
        }

        return vehiculos;
    }
    */

    //Filtra vehñiculos SOLO POR MARCA
    public List<VehicleVo> filtrarVehiculos(String marca, String provincia, String precio) {
        List<VehicleVo> vehiculos = new ArrayList<>();
        String query = "SELECT * FROM Vehicles WHERE make = ?";
        
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, marca);
    
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                VehicleVo vehiculo = new VehicleVo();
                vehiculo.setMarca(rs.getString("make"));
                vehiculo.setModelo(rs.getString("model"));
                vehiculo.setAnio(rs.getInt("year"));
                vehiculo.setKilometraje(rs.getInt("kilometers"));
                vehiculo.setTipoCombustible(rs.getString("fuel_type"));
                vehiculo.setTransmision(rs.getString("transmission"));
                vehiculos.add(vehiculo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return vehiculos;
    }
    
}

    
