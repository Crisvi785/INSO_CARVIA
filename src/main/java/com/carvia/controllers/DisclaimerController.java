package com.carvia.controllers;

import java.io.IOException;

import com.carvia.App;

import javafx.fxml.FXML;

public class DisclaimerController {
    
    
    @FXML
    private void handleBackToMain() throws IOException {
        App.setRoot("mainpage");
        }
    
}
