package com.carvia.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

public class AdminsController {

    @FXML
    private TableView<?> userTable;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colUsername;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private void handleDeleteUser() {
        // Lógica para eliminar un usuario
        if (userTable.getSelectionModel().getSelectedItem() != null) {
            System.out.println("Usuario eliminado correctamente.");
        } else {
            // Mostrar un mensaje si no hay un usuario seleccionado
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, selecciona un usuario para eliminar.");
            alert.showAndWait();
        }
    }
}

