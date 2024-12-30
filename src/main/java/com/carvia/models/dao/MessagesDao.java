package com.carvia.models.dao;

import com.carvia.controllers.*;
import com.carvia.models.vo.MessagesVo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MessagesDao {

    private final Connection connection;
    private static final Logger logger = LogManager.getLogger(MessagesDao.class);

    public MessagesDao() {
        this.connection = BBDDController.getInstance().getConnection();
    }

    public boolean insertMessage(MessagesVo message) {
        String query = "INSERT INTO Messages (idUs, text, date) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, message.getIdUs());
            statement.setString(2, message.getText());
            statement.setDate(3, java.sql.Date.valueOf(message.getDate()));
            logger.info("Mensaje del usuario con id " + message.getIdUs() + " insertado");
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error insertando el mensaje del usuario con id " + message.getIdUs());
            e.printStackTrace();
            return false;
        }
    }

    /* 
    public MessagesVo getMessageById(int idMes) {
        String query = "SELECT * FROM Messages WHERE idMes = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idMes);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int idUs = resultSet.getInt("idUs");
                String text = resultSet.getString("text");
                String date = resultSet.getDate("date").toString();
                logger.info("Message with ID " + idMes + " retrieved");
                return new MessagesVo(idMes, idUs, text, date);
            }
        } catch (SQLException e) {
            logger.error("Error retrieving message with ID " + idMes);
            e.printStackTrace();
        }
        return null;
    }

    public List<MessagesVo> getMessagesByUserId(int idUs) {
        String query = "SELECT * FROM Messages WHERE idUs = ?";
        List<MessagesVo> messageList = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idUs);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int idMes = resultSet.getInt("idMes");
                String text = resultSet.getString("text");
                String date = resultSet.getDate("date").toString();
                messageList.add(new MessagesVo(idMes, idUs, text, date));
            }
            logger.info("Messages for User ID " + idUs + " retrieved");
        } catch (SQLException e) {
            logger.error("Error retrieving messages for User ID " + idUs);
            e.printStackTrace();
        }
        return messageList;
    }

    public boolean deleteMessage(int idMes) {
        String query = "DELETE FROM Messages WHERE idMes = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idMes);
            logger.info("Message with ID " + idMes + " deleted");
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("Error deleting message with ID " + idMes);
            e.printStackTrace();
            return false;
        }
    }
    */
}

