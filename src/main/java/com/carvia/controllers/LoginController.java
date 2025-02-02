package com.carvia.controllers;

import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import com.carvia.App;
import com.carvia.utils.AlertUtil;
import com.carvia.models.UserSession;
import com.carvia.models.dao.UserDao;
import com.carvia.models.vo.UserVo;

public class LoginController {

    private static final Logger logger = LogManager.getLogger(LoginController.class);

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    private UserDao userDao = new UserDao();

    @FXML
    private void switchToRegister() throws IOException {
        App.setRoot("register");
    }

    @FXML
    private void login() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        logger.info("Intento de inicio de sesión para el usuario: " + username);

        if (isInputSuspicious(username) || isInputSuspicious(password)) {
            logger.warn("Entrada sospechosa detectada");
            AlertUtil.showAlert("Advertencia", "Entrada inválida detectada", Alert.AlertType.WARNING);
            return;
        }

        UserVo user = userDao.getUserByUsername(username);
        
        
        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            UserSession.login(username);
            if(user.getId() == 1){
                App.setRoot("admins");
            }
            else{
                logger.info("Inicio de sesión exitoso para el usuario: " + username);
                App.setRoot("mainpage");
            }
            
        } else {
            logger.warn("Inicio de sesión fallido: Usuario o contraseña incorrectos");
            AlertUtil.showAlert("Error", "Usuario o contraseña incorrectos", Alert.AlertType.ERROR);
        }

        
    }

    private boolean isInputSuspicious(String input) {
        String suspiciousPattern = ".*[;''\"<>|].*";
        return input.matches(suspiciousPattern);
    }


}
