package com.carvia.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.carvia.models.vo.AnuncioVo;
import com.carvia.models.vo.VehicleVo;
import com.carvia.models.vto.VehicleAdVto;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

public class ResultsController {

    @FXML
    private ListView<VehicleVo> resultadosListView;
    @FXML
    private BorderPane resultPane; // Panel donde se mostrarán los resultados

    @FXML
    private TableView<VehicleAdVto> tablaVehiculos;

    @FXML
    private TableColumn<VehicleAdVto, String> columnaMarca;
    @FXML
    private TableColumn<VehicleAdVto, String> columnaModelo;
    @FXML
    private TableColumn<VehicleAdVto, Integer> columnaAño;
    @FXML
    private TableColumn<VehicleAdVto, Double> columnaPrecio;

    @FXML
    private TableColumn<VehicleAdVto, Void> columnaComprar;

    private ObservableList<VehicleVo> listaVehiculos = FXCollections.observableArrayList();

    private final Connection connection;

    public ResultsController() {
        this.connection = BBDDController.getInstance().getConnection();
    }

    @FXML
    public void initialize() {
        columnaMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        columnaModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        columnaAño.setCellValueFactory(new PropertyValueFactory<>("anio"));
        columnaPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

        columnaComprar.setCellFactory(param -> new TableCell<>() {
            private final Button botonComprar = new Button("Comprar");
            private final PaymentController paymentController = new PaymentController(); // Instancia del controlador de pagos


            {
                // Acción al hacer clic en el botón
                botonComprar.setOnAction(event -> {
                    // Obtener el vehículo seleccionado
                    VehicleAdVto vehiculo =  getTableView().getItems().get(getIndex());
                    
                    if (vehiculo != null) {
                        System.out.println("Vehículo seleccionado para compra: " + vehiculo.getMarca() + " "
                                + vehiculo.getModelo());

                        // Llamar a un método para realizar la compra
                        paymentController.realizarCompra(vehiculo);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(botonComprar);
                }
            }
        });

        cargarDatos();
    }

    public void cargarDatos() {
        listaVehiculos.clear();
        String query = "SELECT v.make, v.model, v.year, a.price " +
                "FROM Vehicles v " +
                "INNER JOIN Advertisements a ON v.idVE = a.idVE";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();

            ObservableList<VehicleAdVto> listaVehiculos = FXCollections.observableArrayList();

            while (resultSet.next()) {
                String marca = resultSet.getString("make");
                String modelo = resultSet.getString("model");
                int anio = resultSet.getInt("year");
                double precio = resultSet.getDouble("price");

                VehicleVo vehiculo = new VehicleVo(marca, modelo, anio);
                AnuncioVo anuncio = new AnuncioVo(precio);
                VehicleAdVto vehicleAnuncio = new VehicleAdVto(vehiculo, anuncio);

                listaVehiculos.add(vehicleAnuncio);
            }

            tablaVehiculos.setItems(listaVehiculos);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

  

    public void mostrarVehiculos(List<VehicleVo> vehiculos) {
        resultPane.getChildren().clear(); // Limpia los resultados anteriores

        for (VehicleVo vehiculo : vehiculos) {
            // Contenedor para cada vehículo
            HBox vehiculoBox = new HBox(10); // Espaciado de 10 píxeles entre elementos
            vehiculoBox.setPadding(new Insets(10)); // Espaciado interno
            vehiculoBox.setStyle("-fx-border-color: lightgray; -fx-border-width: 1px; -fx-border-radius: 5px;");

            // Imagen del vehículo
            ImageView vehiculoImage = new ImageView();
            vehiculoImage.setFitWidth(100);
            vehiculoImage.setFitHeight(75);
            /*
             * ç
             * 
             * 
             * // Si tienes una URL para la imagen del vehículo
             * if (vehiculo.getImagenUrl() != null && !vehiculo.getImagenUrl().isEmpty()) {
             * vehiculoImage.setImage(new Image(vehiculo.getImagenUrl()));
             * } else {
             * vehiculoImage.setImage(new Image("default-image-url.jpg")); // Imagen por
             * defecto
             * }
             * 
             */

            // Información del vehículo
            VBox vehiculoInfo = new VBox(5); // Espaciado entre elementos de la columna
            Label marcaModelo = new Label(vehiculo.getMarca() + " " + vehiculo.getModelo());
            marcaModelo.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

            Label anio = new Label("Año: " + vehiculo.getAnio());
            Label precio = new Label("Precio: " + vehiculo.getKilometraje() + " €");

            vehiculoInfo.getChildren().addAll(marcaModelo, anio, precio);

            // Agregar imagen e información al contenedor principal del vehículo
            vehiculoBox.getChildren().addAll(vehiculoImage, vehiculoInfo);

            // Agregar el contenedor del vehículo al panel de resultados
            resultPane.getChildren().add(vehiculoBox);
        }
    }

    public void mostrarResultados(List<String> vehicles, ListView<String> resultadosListView) {
        resultadosListView.getItems().clear();
        resultadosListView.getItems().addAll(vehicles); // Si tienes elementos String o similares
    }

    // Método para vincular el contenedor resultPane
    public void setResultPane(VBox resultPane) {
        // this.resultPane = resultPane;
    }

}
