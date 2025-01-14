
 package com.carvia.models.dao;
 
 import com.carvia.controllers.*;
import com.carvia.models.UserSession;
import com.carvia.models.vo.UserVo;
import com.carvia.utils.AlertUtil;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.sql.Connection;
 import java.sql.PreparedStatement;
 import java.sql.ResultSet;
 import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
                UserVo user = new UserVo();
                user.setId(resultSet.getInt("idUs"));
                user.setUsername(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setFullName(resultSet.getString("full_name"));
                user.setPassword(resultSet.getString("password"));
                logger.info("User " + username + " found");
                return user;
            }
         } catch (SQLException e) {
             logger.error("Error getting user " + username);
             e.printStackTrace();
         }
         return null;
     }

     public UserVo getSuperUserbyUsername(String username){
        String query = "SELECT * FROM Users WHERE idUs = 1";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                UserVo user = new UserVo();
                user.setId(resultSet.getInt("idUs"));
                user.setUsername(resultSet.getString("username"));
                user.setEmail(resultSet.getString("email"));
                user.setFullName(resultSet.getString("full_name"));
                user.setPassword(resultSet.getString("password"));
                logger.info("Admin " + username + " found");
                return user;
            }
        } catch (SQLException e) {
            logger.error("Error getting Admin " + username);
            e.printStackTrace();
        }
        return null;
     }
    
    public List<UserVo> listarUsuarios() throws Exception {
        List<UserVo> usuarios = new ArrayList<>();
        String query = "SELECT * FROM Users";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("idUs");
                String username = resultSet.getString("username");
                String fullName = resultSet.getString("full_name");
                String password = resultSet.getString("password");
                String email = resultSet.getString("email");
                usuarios.add(new UserVo(id, username, fullName, email, password));                
            }
            System.out.println("Usuarios listados correctamente.");

        } catch (SQLException e) {
            logger.error("Error listing users");
            System.out.println("Error listando los usuarios.");
            e.printStackTrace();
            throw new Exception("Error listing users", e);
        }
        return usuarios;
    }

    public void eliminarUsuario(UserVo user) throws Exception {
        String query = "DELETE FROM Users WHERE idUs = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, user.getId());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("User " + user.getUsername() + " deleted successfully");
                System.out.println("User " + user.getUsername() + " deleted successfully");
            } else {
                logger.warn("No user found with id " + user.getId());
                AlertUtil.showAlert("Error", "No se han encontrado usuarios con ese ID.", AlertType.ERROR);
                System.out.println("No user found with id " + user.getId());
            }
        } catch (SQLException e) {
            logger.error("Error deleting user " + user.getUsername());
            System.out.println("Error deleting user " + user.getUsername());
            e.printStackTrace();
            throw new Exception("Error deleting user", e);
        }
    }

    public UserVo getUserById(int idUsuario) {
        String query = "SELECT * FROM Users WHERE idUs = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, idUsuario);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    UserVo user = new UserVo();
                    user.setId(resultSet.getInt("idUs"));
                    user.setUsername(resultSet.getString("username"));
                    user.setEmail(resultSet.getString("email"));
                    user.setFullName(resultSet.getString("full_name"));
                    // Set other fields as needed
                    return user;
                } else {
                    logger.warn("No user found with id " + idUsuario);
                    System.out.println("No user found with id " + idUsuario);
                    return null;
                }
            }
        } catch (SQLException e) {
            logger.error("Error retrieving user with id " + idUsuario);
            System.out.println("Error retrieving user with id " + idUsuario);
            e.printStackTrace();
            throw new RuntimeException("Error retrieving user", e);
        }
    }

    public boolean usernameAlreadyExists(String username) {
        UserVo user = getUserByUsername(username);

        boolean exists = user != null;

        if (UserSession.isLoggedIn()) {
            exists = exists && !UserSession.getUsername().equals(username);
        }

        if (exists) {
            AlertUtil.showAlert("Error", "Este nombre de usuario ya está registrado", Alert.AlertType.ERROR);
        }
        return exists;
    }

    public UserVo getUserByEmail(String email) {
        String query = "SELECT * FROM Users WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    UserVo user = new UserVo();
                    user.setId(resultSet.getInt("idUs"));
                    user.setUsername(resultSet.getString("username"));
                    user.setEmail(resultSet.getString("email"));
                    user.setFullName(resultSet.getString("full_name"));
                    // Set other fields as needed
                    return user;
                } else {
                    logger.warn("No user found with email " + email);
                    System.out.println("No user found with email " + email);
                    return null;
                }
            }
        } catch (SQLException e) {
            logger.error("Error retrieving user with email " + email);
            System.out.println("Error retrieving user with email " + email);
            e.printStackTrace();
            throw new RuntimeException("Error retrieving user", e);
        }
    }

    public boolean emailAlreadyExists(String email) {
        UserVo user = getUserByEmail(email);
        boolean exists = user != null;
        if (UserSession.isLoggedIn()) {
            exists = exists && !UserSession.getEmail().equals(email);
        }
        if (exists) {
            AlertUtil.showAlert("Error", "Este email ya está registrado", Alert.AlertType.ERROR);
        }
        return exists;
    }

    public boolean updateUser(int userId, String username, String fullName, String email, String password) {
        StringBuilder query = new StringBuilder("UPDATE Users SET ");
        boolean first = true;

        if (username != null) {
            query.append("username = ?");
            first = false;
        }
        if (fullName != null) {
            if (!first) query.append(", ");
            query.append("full_name = ?");
            first = false;
        }
        if (email != null) {
            if (!first) query.append(", ");
            query.append("email = ?");
            first = false;
        }
        if (password != null) {
            if (!first) query.append(", ");
            query.append("password = ?");
        }
        query.append(" WHERE idUs = ?");

        try (PreparedStatement statement = connection.prepareStatement(query.toString())) {
            int index = 1;
            if (username != null) {
                statement.setString(index++, username);
            }
            if (fullName != null) {
                statement.setString(index++, fullName);
            }
            if (email != null) {
                statement.setString(index++, email);
            }
            if (password != null) {
                String hashedPass = BCrypt.hashpw(password, BCrypt.gensalt());
                statement.setString(index++, hashedPass);
            }
            statement.setInt(index, userId);

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            logger.error("Error updating user with id " + userId);
            System.out.println("Error updating user with id " + userId);
            e.printStackTrace();
            throw new RuntimeException("Error updating user", e);
        }
    }
    
}


