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
    private TextField usernameTextInput;
    @FXML
    private PasswordField passwordInput;

    @FXML
    private PasswordField confirmPasswordInput;

    private UserDao userDAO = new UserDao();

    private static final Logger logger = LogManager.getLogger(RegisterController.class);

    @FXML
    private void backToLogin() throws IOException {
        App.setRoot("login");
    }

    @FXML
    private void register() throws IOException {
        String username = usernameTextInput.getText();
        String password = passwordInput.getText();
        String confirmPassword = confirmPasswordInput.getText();
        Window actualWIndow = usernameTextInput.getScene().getWindow();
        logger.info("Username: " + username + " trying to register");
        if (!password.equals(confirmPassword)) {
            AlertUtil.showAlert("Error", "Las contraseñas no coinciden", actualWIndow);
            logger.warn("Passwords do not match");
            return;
        }

        if (!PasswordValidator.isValid(password)) {
            AlertUtil.showAlert("Error",
                    "La contraseña debe tener al menos 8 caracteres, una letra mayúscula, una letra minúscula, un número y un carácter especial",
                    actualWIndow);
            logger.warn(
                    "Invalid password, must have at least 8 characters, one uppercase letter, one lowercase letter, one number and one special character");
            return;
        }

        UserVo newUser = new UserVo(username, password);

        if (newUser != null && userDAO.insertUser(newUser)) {
            AlertUtil.showAlert("Éxito", "Usuario registrado correctamente", actualWIndow);
            logger.info(newUser.toString() + " registered");
            App.setRoot("login");
        } else {
            AlertUtil.showAlert("Error", "No se pudo registrar el usuario", actualWIndow);
            logger.error("Error registering user " + username);
        }

    }
    

}
