package com.carvia.controllers;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Window;

import com.carvia.App;
import com.carvia.models.dao.UserDao;
import com.carvia.models.vo.UserVo;
import com.carvia.utils.AlertUtil;
import com.carvia.utils.PasswordValidator;

public class RegisterController {

    @FXML
    private TextField usernameField; 

    @FXML
    private PasswordField passwordField; 

    @FXML
    private TextField fullnameField; 

    @FXML
    private TextField emailField; 

    private UserDao userDAO = new UserDao();

    private static final Logger logger = LogManager.getLogger(RegisterController.class);

    @FXML
    private void backToLogin() throws IOException {
        App.setRoot("login");
    }

    @FXML
    private void register() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String fullname = fullnameField.getText();
        String email = emailField.getText();
    
        Window actualWindow = usernameField.getScene().getWindow();
        logger.info("Username: " + username + " trying to register");
    
        if (username.isEmpty() || password.isEmpty() || fullname.isEmpty() || email.isEmpty()) {
            AlertUtil.showAlert("Error", "Por favor, complete todos los campos", actualWindow);
            logger.warn("Some fields are empty");
            return;
        }
    
        if (!PasswordValidator.isValid(password)) {
            AlertUtil.showAlert(
                "Error",
                "Contraseña (mínimo): 8 caracteres, una letra mayúscula, una letra minúscula, un número y un carácter especial",
                actualWindow
            );
            logger.warn("Invalid password format");
            return;
        }
    
        UserVo newUser = new UserVo(username, fullname, email, password);
    
        if (userDAO.insertUser(newUser)) {
            AlertUtil.showAlert("Éxito", "Usuario registrado correctamente", actualWindow);
            logger.info(newUser.toString() + " registered");
            App.setRoot("login");
        } else {
            AlertUtil.showAlert("Error", "No se pudo registrar el usuario", actualWindow);
            logger.error("Error registering user " + username);
        }
    }
    
}
