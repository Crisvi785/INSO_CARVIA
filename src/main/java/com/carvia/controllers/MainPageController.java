package com.carvia.controllers;

import com.carvia.models.dao.AnuncioDao;
import com.carvia.models.vto.VehicleAdVto;
import com.carvia.utils.AlertUtil;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import org.controlsfx.control.RangeSlider;

import java.io.IOException;
import java.util.List;

import javafx.scene.control.Label;
import com.carvia.App;
import com.carvia.models.UserSession;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

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
    private ComboBox<String> marcaModeloComboBox;

    @FXML
    private ComboBox<String> provinciasComboBox;

    @FXML
    private RangeSlider precioRangeSlider;

    @FXML
    private Label minPriceLabel;

    @FXML
    private Label maxPriceLabel;

    @FXML
    private Button mostrarResultadosButton;

    @FXML
    private Label userGreetingLabel;

    private AnuncioDao anuncioDao;

    public MainPageController() {
        anuncioDao = new AnuncioDao();
    }

    @FXML
    public void initialize() {
        lblHeader.setText("Carvia - Venta de Coches");
        marcaModeloComboBox.setItems(FXCollections.observableArrayList("-", "Acura", "Alfa Romeo", "Aston Martin", "Audi", "Bentley", "BMW", "Bugatti", "Buick", "Chevrolet", "Chrysler", "Citroën", "Dodge", "Ferrari", "Fiat", "Ford", "GMC", "Honda", "Hyundai", "Infiniti", "Jaguar", "Kia", "Lamborghini", "Land Rover", "Lexus", "Lincoln", "Maserati", "Mazda", "Mercedes-Benz", "Mitsubishi", "Nissan", "Opel", "Peugeot", "Porsche", "Renault", "Rolls-Royce", "Seat", "Skoda", "Subaru", "Tesla", "Toyota", "Volkswagen", "Volvo"));
        provinciasComboBox.setItems(FXCollections.observableArrayList("-", "Ávila", "Badajoz", "Barcelona", "Burgos", "Cáceres", "Cádiz", "Castellón", "Ciudad Real", "Córdoba", "Cuenca", "Gerona", "Granada", "Guadalajara", "Huelva", "Huesca", "Jaén", "La Coruña", "La Rioja", "Las Palmas", "León", "Lérida", "Lugo", "Madrid", "Málaga", "Murcia", "Navarra", "Orense", "Palencia", "Pontevedra", "Salamanca", "Santa Cruz de Tenerife", "Segovia", "Sevilla", "Soria", "Tarragona", "Teruel", "Toledo", "Valencia", "Valladolid", "Vizcaya", "Zamora", "Zaragoza"));
        precioRangeSlider.setLowValue(0);
        precioRangeSlider.setHighValue(70000);
        precioRangeSlider.lowValueProperty().addListener((observable, oldValue, newValue) -> {
            minPriceLabel.setText(String.format("%.0f", newValue));
        });

        precioRangeSlider.highValueProperty().addListener((observable, oldValue, newValue) -> {
            maxPriceLabel.setText(String.format("%.0f", newValue));
        });

        lblFooter.setText("© 2024 Carvia. Todos los derechos reservados.");

         // Obtener el usuario logueado desde la sesión
        String username = UserSession.getUsername();
        if (username != null) {
            userGreetingLabel.setText("¡Bienvenido " + username + "!  ");
        } 
        else {
            userGreetingLabel.setText(""); 
        }
    }

    @FXML
    private void handleVenderClick() throws IOException {
        App.setRoot("vender");
    }

    @FXML
    private void handleAccountEvent() throws IOException {
        try {
            if (checkUserRegistration()) {
                App.setRoot("account");
            } else {
                App.setRoot("login");
            }
        } catch (IOException e) {
            throw e;
        }
    }

    private boolean checkUserRegistration() {
        return UserSession.isLoggedIn();
    }

    @FXML
    private void handleTramites() throws IOException {
        App.setRoot("tramites");
    }

    @FXML
    private void handlePruebas() throws IOException {
        App.setRoot("pruebas");

    }


    @FXML
    private VBox segundaManoBox, nuevosBox, km0Box, rentingBox, certificadosBox, caravanasBox;

    @FXML
    private VBox resultPane; // Panel donde se mostrarán los resultados

    @FXML
    public void handleCategoriaClick(MouseEvent event) {
        // Determina qué categoría fue seleccionada
        VBox clickedBox = (VBox) event.getSource();
        String categoria;

        if (clickedBox == segundaManoBox) {
            categoria = "Segunda Mano";
        } else if (clickedBox == nuevosBox) {
            categoria = "Nuevos";
        } else if (clickedBox == km0Box) {
            categoria = "Km 0";
        } else if (clickedBox == rentingBox) {
            categoria = "Renting";
        } else if (clickedBox == certificadosBox) {
            categoria = "Certificados";
        } else if (clickedBox == caravanasBox) {
            categoria = "Caravanas";
        } else {
            return; // No se reconoce la categoría
        }
    }

    @FXML
    private void handleMostrarResultados() throws IOException {
        String marca = marcaModeloComboBox.getValue();
        String provincia = provinciasComboBox.getValue();
        double precioMin = precioRangeSlider.getLowValue();
        double precioMax = precioRangeSlider.getHighValue();

        try {
            List<VehicleAdVto> anuncios = anuncioDao.filtrarAnuncios(marca, provincia, precioMin, precioMax);
            mostrarResultados(anuncios);
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtil.showAlert("Error", "Error loading results", Alert.AlertType.ERROR);
        }
    }

    private void mostrarResultados(List<VehicleAdVto> anuncios) {
        try {
            // Load the FXML file for the results page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/carvia/views/results.fxml"));
            Parent root = loader.load();
            

            // Get the controller for the results page
            ResultsController controller = loader.getController();
            controller.setAnuncios(anuncios);

            // Show the results page
            Stage stage = new Stage();
            stage.setTitle("Resultados");
            stage.setScene(new Scene(root));
            stage.show();
            // No se muestra en la misma ventana
        } catch (IOException e) {
            e.printStackTrace();
            AlertUtil.showAlert("Error", "Error showing results", Alert.AlertType.ERROR);
        }
    }
     

}
