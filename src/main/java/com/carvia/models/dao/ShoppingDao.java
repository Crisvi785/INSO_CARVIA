package com.carvia.models.dao;

import com.carvia.controllers.*;
import com.carvia.models.vo.ShoppingVo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShoppingDao {

    private final Connection connection;
    private static final Logger logger = LogManager.getLogger(ShoppingDao.class);

    public ShoppingDao() {
        this.connection = BBDDController.getInstance().getConnection();
    }

    public boolean insertShopping(ShoppingVo shopping) {
        String query = "INSERT INTO Shopping (idVe, idPay, idUs, date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, shopping.getIdVe());
            statement.setInt(2, shopping.getIdPay());
            statement.setInt(3, shopping.getIdUs());
            statement.setDate(4, java.sql.Date.valueOf(shopping.getDate()));
            logger.info("Registro de compra insertado con id de vehículo " + shopping.getIdVe());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error al insertar el vehículo de compra");
            e.printStackTrace();
            return false;
        }
    }

    /*
    public ShoppingVo getShoppingById(int idShop) {
        String query = "SELECT * FROM Shopping WHERE idShop = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idShop);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int idVe = resultSet.getInt("idVe");
                int idPay = resultSet.getInt("idPay");
                int idUs = resultSet.getInt("idUs");
                String date = resultSet.getDate("date").toString();
                logger.info("Shopping record found with ID: " + idShop);
                return new ShoppingVo(idShop, idVe, idPay, idUs, date);
            }
        } catch (SQLException e) {
            logger.error("Error retrieving shopping record with ID: " + idShop);
            e.printStackTrace();
        }
        return null;
    }

    public List<ShoppingVo> getAllShopping() {
        String query = "SELECT * FROM Shopping";
        List<ShoppingVo> shoppingList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int idShop = resultSet.getInt("idShop");
                int idVe = resultSet.getInt("idVe");
                int idPay = resultSet.getInt("idPay");
                int idUs = resultSet.getInt("idUs");
                String date = resultSet.getDate("date").toString();
                shoppingList.add(new ShoppingVo(idShop, idVe, idPay, idUs, date));
            }
            logger.info("Retrieved all shopping records");
        } catch (SQLException e) {
            logger.error("Error retrieving shopping records");
            e.printStackTrace();
        }
        return shoppingList;
    }

    public boolean deleteShopping(int idShop) {
        String query = "DELETE FROM Shopping WHERE idShop = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idShop);
            logger.info("Shopping record with ID " + idShop + " deleted");
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error deleting shopping record with ID " + idShop);
            e.printStackTrace();
            return false;
        }
    }
    */
}
