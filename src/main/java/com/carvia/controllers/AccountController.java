package com.carvia.controllers;

import java.io.IOException;

import com.carvia.App;
import com.carvia.models.UserSession;
import com.carvia.models.dao.UserDao;
import com.carvia.models.vo.UserVo;
import com.carvia.utils.AlertUtil;
import com.carvia.utils.EmailValidator;
import com.carvia.utils.PasswordValidator;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class AccountController {

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtFullName;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtPassword;

    private UserDao userDao;

    public AccountController() {
        this.userDao = new UserDao();
    }

    @FXML
    private void initialize() {
        setFields();
    }

    @FXML
    private void handleSaveChanges() {
        String username = txtUsername.getText();
        String fullName = txtFullName.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();
        int userId = UserSession.getLoggedInUserId();

        // Si algún campo está vacío, entonces no se actualiza, toma el valor
        // de null para indicar que no se actualizará
        username = username.isEmpty() ? null : username;
        fullName = fullName.isEmpty() ? null : fullName;
        email = email.isEmpty() ? null : email;
        password = password.isEmpty() ? null : password;


        // Comprobaciones para actualizar con éxito
        if (username != null && !UserSession.getUsername().equals(username)) {
            if (userDao.usernameAlreadyExists(username)) {
                return;
            }
        }

        if (email != null && !UserSession.getEmail().equals(email)) {
            if (!EmailValidator.isValid(email)) {
                AlertUtil.showAlert(
                    "Error",
                    "Correo electrónico inválido",
                    Alert.AlertType.ERROR
                );
                System.out.println("Invalid email format");
                return;
            }

            if (userDao.emailAlreadyExists(email)) {
                return;
            }

        }
        
        if (password != null) {
            if (!PasswordValidator.isValid(password)) {
                AlertUtil.showAlert(
                    "Error",
                    "Contraseña (mínimo): 8 caracteres, una letra mayúscula, una letra minúscula, un número y un carácter especial",
                    Alert.AlertType.ERROR
                );
                System.out.println("Invalid password format");
                return;
            }
        }

        // Actualizar solo los campos que no estén vacíos
        boolean updated = userDao.updateUser (userId, username, fullName, email, password);

        if (username != null && !username.equals(UserSession.getUsername())) {
            UserSession.login(username);
        }

        if (updated) {
            AlertUtil.showAlert(
                "Éxito",
                "Datos actualizados correctamente",
                Alert.AlertType.INFORMATION
            );
        } else {
            AlertUtil.showAlert(
                "Error",
                "No se pudo actualizar los datos",
                Alert.AlertType.ERROR
            );
        }

        setFields();

    }


    private void setFields() {
        int userId = UserSession.getLoggedInUserId();
        UserVo user = userDao.getUserById(userId);
        String username = user.getUsername();
        String fullName = user.getFullName();
        String email = user.getEmail();

        txtUsername.setText(username);
        txtFullName.setText(fullName);
        txtEmail.setText(email);
        txtPassword.setText("");
    }

    private void setFieldsEmpty() {
        txtUsername.setText("");
        txtFullName.setText("");
        txtEmail.setText("");
        txtPassword.setText("");
    }

    @FXML
    private void handleLogout() throws IOException {
        UserSession.logout();
        System.out.println("Sesión cerrada correctamente");

        // Redirigir a la pantalla de inicio de sesión
        App.setRoot("login");
    }

    @FXML
    private void handleBackToMain() throws IOException {
        // Regresar a la pantalla de inicio
        App.setRoot("mainpage");
    }
}
