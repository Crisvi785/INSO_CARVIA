package com.carvia.controllers;

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
    private void handleLogout() {
        System.out.println("Cerrar Sesión");
        // Aquí puedes redirigir al usuario a la pantalla de login o cerrar la sesión
    }
}
