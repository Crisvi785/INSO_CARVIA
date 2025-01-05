
 package com.carvia.models.dao;
 
 import com.carvia.controllers.*;
 import com.carvia.models.vo.UserVo;

import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.SQLException;
 
 import org.apache.logging.log4j.LogManager;
 import org.apache.logging.log4j.Logger;
 import org.mindrot.jbcrypt.BCrypt;
 
 public class UserDao {
 
     private final Connection connection;
     private static final Logger logger = LogManager.getLogger(UserDao.class);
 
     public UserDao() {
         this.connection = BBDDController.getInstance().getConnection();
     }
 
     public boolean insertUser(UserVo user) {
         String query = "INSERT INTO Users (username, full_name, email, password) VALUES (?, ?, ?, ?)";
         try (PreparedStatement statement = connection.prepareStatement(query)) {
 
             String hashedPass = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
             user.setPassword(hashedPass);
             statement.setString(1, user.getUsername());
             statement.setString(2, user.getFullName()); 
             statement.setString(3, user.getEmail());   
             statement.setString(4, hashedPass);        
             logger.info("User " + user.getUsername() + " registered");
             return statement.executeUpdate() > 0;
         } catch (SQLException e) {
             logger.error("Error registering user " + user.getUsername());
             e.printStackTrace();
             return false;
         }
     }
 
     public UserVo getUserByUsername(String username) {
         String query = "SELECT * FROM Users WHERE username = ?";
         try (PreparedStatement statement = connection.prepareStatement(query)) {
             statement.setString(1, username);
             ResultSet resultSet = statement.executeQuery();
             if (resultSet.next()) {
                 int id = resultSet.getInt("idUs");
                 String password = resultSet.getString("password");
                 logger.info("User " + username + " found");
                 return new UserVo(id, username, password);
             }
         } catch (SQLException e) {
             logger.error("Error getting user " + username);
             e.printStackTrace();
         }
         return null;
     }

     public UserVo getSuperUserbyUsername(String username){
        String query = "SELECT * FROM Users WHERE idUs = 6";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            ResultSet resultset = statement.executeQuery();
            if (resultset.next()) {
                int id = resultset.getInt("idUs");
                String password = resultset.getString("password");
                logger.info("Admin " + username + " found");
                return new UserVo(id, username, password);
            }
        } catch (SQLException e) {
            logger.error("Error getting Admin " + username);
            e.printStackTrace();
        }
        return null;
     }
 }


