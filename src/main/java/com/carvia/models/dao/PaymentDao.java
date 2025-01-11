package com.carvia.models.dao;

import com.carvia.controllers.BBDDController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentDao {
    
    private final Connection connection;

    public PaymentDao() {
        this.connection = BBDDController.getInstance().getConnection();
    }

    public boolean insertPayment(String cardNumber, String cardExpiration, String cardCvc, int vehicleId, int userId){
        String getPriceQuery = "SELECT price FROM Advertisements WHERE idVe = ?";
        String paymentQuery = "INSERT INTO Payments (type, amount, card_number, card_expiration, card_cvc) VALUES (?, ?, ?, ?, ?)";
        String shoppingQuery = "INSERT INTO Shopping (idVe, idPay, idUs, date) VALUES (?, LAST_INSERT_ID(), ?, CURRENT_DATE)";
        try (PreparedStatement getPriceStmt = connection.prepareStatement(getPriceQuery);
             PreparedStatement paymentStmt = connection.prepareStatement(paymentQuery);
             PreparedStatement shoppingStmt = connection.prepareStatement(shoppingQuery)) {
            
            
            getPriceStmt.setInt(1, vehicleId);
            ResultSet priceResult = getPriceStmt.executeQuery();
            double price = 0.0;
            if(priceResult.next()){
                price = priceResult.getDouble("price");
            }
            else{
                throw new SQLException("No se encontró un anuncio con el ID de vehículo proporcionado.");
            }

            paymentStmt.setString(1, "Tarjeta de Crédito");
            paymentStmt.setDouble(2, price); 
            paymentStmt.setString(3, cardNumber);
            paymentStmt.setString(4, cardExpiration);
            paymentStmt.setString(5, cardCvc);
            paymentStmt.executeUpdate();

            shoppingStmt.setInt(1, vehicleId);
            shoppingStmt.setInt(2, userId);
            return shoppingStmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
