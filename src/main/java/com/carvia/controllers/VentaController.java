package com.carvia.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;
import java.util.Date;

public class VentaController extends BorderPane {

    @FXML
    private BorderPane contenedorPBP;
    @FXML
    private TextField marcaText;
    @FXML
    private TextField modeloText;
    @FXML
    private TextField precioText;
    @FXML
    private TextField descripcionText;
    @FXML
    private Button publicarButton;
    @FXML
    private Button cancelarButton;
    @FXML
    private Label notificacionLabel;

    public VentaController(double ancho, double alto) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CrearAnuncio.fxml"));
        fxmlLoader.setController(this); // Asigna este controlador al FXML
        try {
            fxmlLoader.load(); // Carga el archivo FXML
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.setPrefSize(ancho, alto); // Configura el tamaño de este BorderPane
        initialize(); // Inicializa los componentes
    }
    

    private void initialize() {
        publicarButton.setOnAction(event -> publicarAnuncio());
        cancelarButton.setOnAction(event -> cancelarAnuncio());
    }

    private void publicarAnuncio() {
        String marca = marcaText.getText();
        String modelo = modeloText.getText();
        String precio = precioText.getText();
        String descripcion = descripcionText.getText();

        if (marca.isEmpty() || modelo.isEmpty() || precio.isEmpty() || descripcion.isEmpty()) {
            notificacionLabel.setText("Por favor, complete todos los campos.");
            return;
        }

        try {
            double precioParsed = Double.parseDouble(precio);
            // Aquí puedes añadir la lógica para guardar el anuncio en una base de datos o servicio
            notificacionLabel.setText("Anuncio publicado con éxito: " + marca + " " + modelo);
            resetFormulario();
        } catch (NumberFormatException e) {
            notificacionLabel.setText("El precio debe ser un número válido.");
        }
    }

    private void cancelarAnuncio() {
        resetFormulario();
        notificacionLabel.setText("Creación de anuncio cancelada.");
    }

    private void resetFormulario() {
        marcaText.setText("");
        modeloText.setText("");
        precioText.setText("");
        descripcionText.setText("");
    }
}
