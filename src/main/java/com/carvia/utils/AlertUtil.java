package com.carvia.utils;

//import com.stripe.model.billing.Alert;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import javafx.stage.Window;

import javafx.scene.control.Alert;

public class AlertUtil {

    /*
    
    public static void showAlert(String title, String message, Window owner) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle(title);
        dialog.setContentText(message);
        dialog.initOwner(owner);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.showAndWait();
    }
    
    */

    public static void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /*
    public static void showAlert(String title, String message, Window owner) {
        Dialog<String> alert = new Dialog<>();
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.initOwner(owner);
        alert.getDialogPane().getButtonTypes().add(ButtonType.OK);

        // Usar TextArea si el mensaje es largo
        if (message.length() > 100) { // Ejemplo de umbral
            TextArea textArea = new TextArea(message);
            textArea.setWrapText(true);
            textArea.setEditable(false);
            textArea.setPrefSize(400, 100);
            alert.getDialogPane().setContent(textArea);
        } else {
            alert.setContentText(message);
        }

        alert.setResizable(true);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }
    */
}
