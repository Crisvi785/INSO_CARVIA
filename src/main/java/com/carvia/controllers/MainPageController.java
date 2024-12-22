package com.carvia.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class MainPageController {

    @FXML
    private Button btnComprar;

    @FXML
    private Button btnVender;

    @FXML
    private Button btnVerMisAnuncios;

    @FXML
    private Button btnMensajes;

    @FXML
    private Button btnConfiguracion;

    @FXML
    private Label lblHeader;

    @FXML
    private ImageView imgCentral;

    @FXML
    private Label lblTitle;

    @FXML
    private Label lblDescription;

    @FXML
    private Label lblFooter;

    @FXML
    public void initialize() {
        // Inicialización si es necesario
        lblHeader.setText("Carvia - Venta de Coches");
       // lblTitle.setText("Bienvenido a Carvia");
        //lblDescription.setText("Tu lugar ideal para comprar y vender coches de manera segura y confiable.");
        lblFooter.setText("© 2024 Carvia. Todos los derechos reservados.");
    }

    @FXML
    private void onComprarClick() {
        System.out.println("Botón Comprar presionado");
        // Lógica adicional para el botón Comprar
    }

    @FXML
    private void onVenderClick() {
        System.out.println("Botón Vender presionado");
        // Lógica adicional para el botón Vender
    }

    @FXML
    private void onVerMisAnunciosClick() {
        System.out.println("Botón Ver Mis Anuncios presionado");
        // Lógica adicional para el botón Ver Mis Anuncios
    }

    @FXML
    private void onMensajesClick() {
        System.out.println("Botón Mensajes presionado");
        // Lógica adicional para el botón Mensajes
    }

    @FXML
    private void onConfiguracionClick() {
        System.out.println("Botón Configuración presionado");
        // Lógica adicional para el botón Configuración
    }
}
