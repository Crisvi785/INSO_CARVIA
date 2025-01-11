package com.carvia.controllers;

import com.carvia.models.dao.PaymentDao;
import com.carvia.models.vto.VehicleAdVto;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


public class PaymentWindowController {
    
    @FXML
    private TextField cardNumberField;

    @FXML
    private TextField cardExpirationField;
    
    @FXML
    private TextField cardCvcField;

    @FXML
    private Button payButton;

    @FXML
    private Label vehiclePriceLabel;

    private VehicleAdVto selectedVehicle;
    private int loggedUserId;

    public void setVehicleAndUser(VehicleAdVto vehicle, int userId){
        this.selectedVehicle = vehicle;
        this.loggedUserId = userId;
        
        //Precio del vehículo en la etiqueta
        if(vehicle != null){
            vehiclePriceLabel.setText("Precio del Vehículo: " + vehicle.getPrecio() + " €");
        }
        else{
            vehiclePriceLabel.setText("Precio no disponible.");
        }
    }

    @FXML
    private void handlePayment(){
        String cardNumber = cardNumberField.getText();
        String cardExpiration = cardExpirationField.getText();
        String cardCvc = cardCvcField.getText();

        //Cambiar para que salga una ventana a mayores
        if (cardNumber.isEmpty() || cardExpiration.isEmpty() || cardCvc.isEmpty()) {
            System.out.println("Por favor, completa todos los campos.");
            return;
        }

         // Validar los detalles de la tarjeta
         if (!validateCardDetails(cardNumber, cardExpiration, cardCvc)) {
            return; 
        }

        PaymentDao paymentDao = new PaymentDao();
        boolean success = paymentDao.insertPayment(cardNumber, cardExpiration, cardCvc, selectedVehicle.getId(), loggedUserId);
        
        if(success){
            //Mensaje de éxito
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Pago realizado");
            alert.setHeaderText(null);
            alert.setContentText("El pago se realizó correctamente");
            alert.showAndWait();

            //Se cierra la ventana de pago
            Stage stage = (Stage) payButton.getScene().getWindow();
            stage.close(); 
        }
        else{
            //Mensaje de error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error en el pago");
            alert.setHeaderText(null);
            alert.setContentText("Hubo un problema al realizar el pago. Por favor, inténtelo de nuevo.");
            alert.showAndWait();
        }
    }

    private boolean validateCardDetails(String cardNumber, String cardExpiration, String cardCvc){
        //Validar el num tarjeta -> 16 dígitos numéricos
        if(cardNumber.length() != 16 || !cardNumber.matches("\\d+")){
            showAlert("Error en el número de la tarjeta", "El número de tarjeta debe contener 16 dígitos numéricos");
            return false;
        }

        //Fecha de caducidad -> Formato MM/YY
        else if(!cardExpiration.matches("\\d{2}/\\d{2}")){
            showAlert("Error en la fecha de expiración", "La fecha de expiración debe tener el formato MM/YY.");
            return false;
        }

        //Validar que el mes y el año sean numéricos y válidos
        String[] parts = cardExpiration.split("/");
        int month = Integer.parseInt(parts[0]);
        int year = Integer.parseInt(parts[1]);
        if(month < 1 || month > 12){
            showAlert("Error en la fecha de expiración", "El mes debe estar entre 01 y 12.");
            return false;
        }

        else if (cardCvc.length() != 3 || !cardCvc.matches("\\d+")) {
            showAlert("Error en el CVC", "El CVC debe contener 3 dígitos numéricos.");
            return false;
        }

        //Validar q el CVC tenga 3 dígitos y estos sean numeros
        else if (cardCvc.length() != 3 || !cardCvc.matches("\\d+")) {
            showAlert("Error en el CVC", "El CVC debe contener 3 dígitos numéricos.");
            return false;
        }
        return true;
    }

    private void showAlert(String title, String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
