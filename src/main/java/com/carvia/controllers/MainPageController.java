package com.carvia.controllers;

import java.io.IO;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.sql.DataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.carvia.App;
import com.carvia.models.UserSession;
import com.carvia.models.dao.VehicleDao;
import com.carvia.models.vo.VehicleVo;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
    private ComboBox<String> precioComboBox;

    @FXML
    private Button mostrarResultadosButton;

    @FXML
    public void initialize() {
        // cargarOpcionesMarcaModelo();
        // cargarOpcionesProvincias();
        // cargarOpcionesPrecios();
        lblHeader.setText("Carvia - Venta de Coches");
        // lblTitle.setText("Bienvenido a Carvia");
        // lblDescription.setText("Tu lugar ideal para comprar y vender coches de manera
        // segura y confiable.");
        marcaModeloComboBox.setItems(FXCollections.observableArrayList("Ford", "Volkswagen", "BMW", "Mercedes-Benz", "Audi", "Toyota", "Honda", "Nissan", "Hyundai", "Kia", "Peugeot", "Renault", "Fiat", "Chevrolet", "Subaru", "Mazda", "Land Rover", "Jaguar", "Lexus", "Volvo", "Porsche", "Dodge", "Chrysler", "Mitsubishi", "Citroën", "Opel", "Seat", "Skoda", "Tesla", "Infiniti", "Acura", "Lincoln", "Buick", "GMC", "Alfa Romeo", "Aston Martin", "Bentley", "Bugatti", "Ferrari", "Lamborghini", "Maserati", "Rolls-Royce"));
        provinciasComboBox.setItems(FXCollections.observableArrayList("Ávila", "Badajoz", "Barcelona", "Burgos", "Cáceres", "Cádiz", "Castellón", "Ciudad Real", "Córdoba", "La Coruña", "Cuenca", "Gerona", "Granada", "Guadalajara", "Huelva", "Huesca", "Jaén", "La Rioja", "Las Palmas", "León", "Lérida", "Lugo", "Madrid", "Málaga", "Murcia", "Navarra", "Orense", "Palencia", "Pontevedra", "Salamanca", "Santa Cruz de Tenerife", "Segovia", "Sevilla", "Soria", "Tarragona", "Teruel", "Toledo", "Valencia", "Valladolid", "Vizcaya", "Zamora", "Zaragoza"));
        precioComboBox.setItems(FXCollections.observableArrayList("Menos de 1.000 €", "1.000 € - 2.000 €", "2.000 € - 3.000 €", "3.000 € - 4.000 €", "4.000 € - 5.000 €", "5.000 € - 6.000 €", "6.000 € - 7.000 €", "7.000 € - 8.000 €", "8.000 € - 9.000 €", "9.000 € - 10.000 €", "10.000 € - 11.000 €", "11.000 € - 12.000 €", "12.000 € - 13.000 €", "13.000 € - 14.000 €", "14.000 € - 15.000 €", "15.000 € - 16.000 €", "16.000 € - 17.000 €", "17.000 € - 18.000 €", "18.000 € - 19.000 €", "19.000 € - 20.000 €", "20.000 € - 21.000 €", "21.000 € - 22.000 €", "22.000 € - 23.000 €", "23.000 € - 24.000 €", "24.000 € - 25.000 €", "25.000 € - 30.000 €", "30.000 € - 35.000 €", "35.000 € - 40.000 €", "40.000 € - 45.000 €", "45.000 € - 50.000 €", "50.000 € - 55.000 €", "55.000 € - 60.000 €", "60.000 € - 65.000 €", "65.000 € - 70.000 €", "Más de 70.000 €"));

        lblFooter.setText("© 2024 Carvia. Todos los derechos reservados.");

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
    private void handleFiltrarCategoria() throws IOException{

    String marcaSeleccionada = marcaModeloComboBox.getValue();
    String provinciaSeleccionada = provinciasComboBox.getValue();
    String precioSeleccionado = precioComboBox.getValue();

    VehicleDao vehicleDao = new VehicleDao();

    // Filtrar los vehículos según las selecciones
    List<VehicleVo> vehiculosFiltrados = vehicleDao.filtrarVehiculos(marcaSeleccionada, provinciaSeleccionada, precioSeleccionado);

    // Procesar los resultados filtrados
    for (VehicleVo vehiculo : vehiculosFiltrados) {
        System.out.println("Vehículo encontrado: " + vehiculo.getMarca() + ", " + vehiculo.getModelo() + ", " + vehiculo.getProvincia() + ", " + vehiculo.getPrecio());
    }
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

        // Filtra los coches según la categoría seleccionada
        // filtrarVehiculosPorCategoria(categoria);
    }
    /*
     * 
     * 
     * private void filtrarVehiculosPorCategoria(String categoria) {
     * List<VehicleVo> vehiculos = DataSource.getVehicleById();
     * 
     * if (vehiculos == null || vehiculos.isEmpty()) {
     * System.out.println("No hay vehículos disponibles en el DataSource.");
     * return;
     * }
     * 
     * List<VehicleVo> vehiculosFiltrados = vehiculos.stream()
     * .filter(vehiculo -> vehiculo.getVehiclesByCategory() != null &&
     * vehiculo.getVehiclesByCategory().equalsIgnoreCase(categoria))
     * .collect(Collectors.toList());
     * 
     * if (vehiculosFiltrados.isEmpty()) {
     * System.out.println("No se encontraron vehículos para la categoría: " +
     * categoria);
     * } else {
     * mostrarVehiculos(vehiculosFiltrados);
     * }
     * }
     * 
     * 
     * private void mostrarVehiculos(List<VehicleVo> vehiculos) {
     * resultPane.getChildren().clear(); // Limpia los resultados anteriores
     * 
     * for (VehicleVo vehiculo : vehiculos) {
     * // Cambia el texto del Label para mostrar la información relevante del
     * vehículo
     * Label vehiculoLabel = new Label(vehiculo.getMarca() + " " +
     * vehiculo.getModelo() + " - " + vehiculo.getAnio());
     * resultPane.getChildren().add(vehiculoLabel);
     * }
     * }
     */

}
