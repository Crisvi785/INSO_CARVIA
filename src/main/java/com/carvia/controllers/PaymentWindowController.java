package com.carvia.controllers;

import com.carvia.models.dao.PaymentDao;
import com.carvia.models.vto.VehicleAdVto;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Label;


public class PaymentWindowController {
    
    @FXML
    private TextField cardNumberField;

    @FXML
    private TextField cardExpirationField;
    
    @FXML
    private TextField cardCvcField;

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

        PaymentDao paymentDao = new PaymentDao();
        boolean success = paymentDao.insertPayment(cardNumber, cardExpiration, cardCvc, selectedVehicle.getId(), loggedUserId);
        


    }
}
