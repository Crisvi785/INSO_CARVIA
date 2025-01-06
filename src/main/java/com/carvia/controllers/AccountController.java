package com.carvia.controllers;

import java.io.IOException;

import com.carvia.App;
import com.carvia.models.UserSession;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AccountController {

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtPhone;

    @FXML
    private void handleSaveChanges() {
        String name = txtName.getText();
        String email = txtEmail.getText();
        String phone = txtPhone.getText();

        // Aquí puedes guardar los datos en una base de datos o mostrarlos en consola
        System.out.println("Datos Guardados:");
        System.out.println("Nombre: " + name);
        System.out.println("Email: " + email);
        System.out.println("Teléfono: " + phone);

        // Agrega lógica adicional para validar y persistir los datos
    }

     @FXML
    private void handleLogout() throws IOException {
        UserSession.logout();
        System.out.println("Sesión cerrada correctamente");

        // Redirigir a la pantalla de inicio de sesión
        App.setRoot("login");
    }
}
