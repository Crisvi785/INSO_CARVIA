package com.carvia.models.dao;

import com.carvia.controllers.BBDDController;
import com.carvia.models.vo.AdvertisementVo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdvertisementDao {

    private final Connection connection;
    private static final Logger logger = LogManager.getLogger(AdvertisementDao.class);

    public AdvertisementDao() {
        this.connection = BBDDController.getInstance().getConnection();
    }

    public boolean insertAdvertisement(AdvertisementVo ad) {
        String query = "INSERT INTO Advertisements (idUs, idVe, date, description, price, images, province) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, ad.getIdUs());
            statement.setInt(2, ad.getIdVe());
            statement.setDate(3, java.sql.Date.valueOf(ad.getDate()));
            statement.setString(4, ad.getDescription());
            statement.setDouble(5, ad.getPrice());
            statement.setString(6, ad.getImages());
            statement.setString(7, ad.getProvince());
            logger.info("Anuncio añadido al usuario con id: " + ad.getIdUs());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error al añadir el anuncio", e);
            return false;
        }
    }
}

