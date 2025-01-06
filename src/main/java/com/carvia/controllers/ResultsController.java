package com.carvia.controllers;

import com.carvia.models.vto.VehicleAdVto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.Connection;
import com.carvia.App;
import com.carvia.models.vo.VehicleVo;

public class ResultsController {

    @FXML
    private BorderPane resultPane; // Panel donde se mostrarán los resultados

    @FXML
    private TableView<VehicleAdVto> resultadosTable;

    @FXML
    private TableColumn<VehicleAdVto, String> colMarca;

    @FXML
    private TableColumn<VehicleAdVto, String> colModelo;

    @FXML
    private TableColumn<VehicleAdVto, Integer> colAno;

    @FXML
    private TableColumn<VehicleAdVto, String> colProvincia;

    @FXML
    private TableColumn<VehicleAdVto, String> colDescripcion;

    @FXML
    private TableColumn<VehicleAdVto, Double> colPrecio;

    @FXML
    private TableColumn<VehicleAdVto, Button> colAccion;

    private final Connection connection;


    public ResultsController() {
        this.connection = BBDDController.getInstance().getConnection();
    }

    @FXML
    private void initialize() {
        
        colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        colAno.setCellValueFactory(new PropertyValueFactory<>("ano"));
        colProvincia.setCellValueFactory(new PropertyValueFactory<>("provincia"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        // colAccion.setCellValueFactory(new PropertyValueFactory<>("comprarButton"));

        
        colAccion.setCellFactory(param -> new TableCell<>() {
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
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(botonComprar);
                }
            }
        });
        
        // cargarDatos();
    }

    public void setAnuncios(List<VehicleAdVto> anuncios) {
        ObservableList<VehicleAdVto> anuncioObservableList = FXCollections.observableArrayList(anuncios);
        resultadosTable.setItems(anuncioObservableList);
    }

    /*
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
    */
  

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

    @FXML
    private void handleBackToMain() throws IOException {
        App.setRoot("mainpage");
    }

    @FXML
    private void handleActualizar() {
        // Reload the data (this method can be implemented as needed)
    }

}
