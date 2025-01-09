package com.carvia.controllers;

import com.carvia.models.vto.VehicleAdVto;
import com.carvia.utils.AlertUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.awt.Desktop;

import javafx.geometry.Insets;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;
import java.sql.Connection;
import com.carvia.App;
import com.carvia.models.dao.UserDao;
import com.carvia.models.vo.UserVo;
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

    @FXML
    private TableColumn<VehicleAdVto, Button> colContacto;

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

        colContacto.setCellFactory(param -> new TableCell<>() {
            private final Button botonContactar = new Button("Contactar");

            {
                // Acción al hacer clic en el botón
                botonContactar.setOnAction(event -> {
                    VehicleAdVto anuncio = getTableView().getItems().get(getIndex());
                    if (anuncio != null) {
                        abrirGMail(anuncio);
                    }
                });
            }

            @Override
            protected void updateItem(Button item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(botonContactar);
                }
            }

        });
        
    }

    public void setAnuncios(List<VehicleAdVto> anuncios) {
        ObservableList<VehicleAdVto> anuncioObservableList = FXCollections.observableArrayList(anuncios);
        resultadosTable.setItems(anuncioObservableList);
    }

    private void abrirGMail(VehicleAdVto anuncio) {
        try {
            // Obtener el correo del vendedor (esto depende de cómo tengas estructurado tu modelo)
            String correoVendedor = obtenerCorreoVendedor(anuncio);

            // Crear la URL de GMail con el correo del vendedor
            String gmailUrl = "https://mail.google.com/mail/?view=cm&fs=1&to=" + correoVendedor;

            // Abrir la URL en el navegador predeterminado
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI(gmailUrl));
            } else {
                System.out.println("Navegador no soportado. Abre la siguiente URL manualmente: " + gmailUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtil.showAlert("Error", "No se pudo abrir GMail", null);
        }
    }

    private String obtenerCorreoVendedor(VehicleAdVto anuncio) {
        UserDao userDao = new UserDao();
        UserVo vendedor = userDao.getUserById(anuncio.getIdUsuario());
        return vendedor.getEmail();
    }

    @FXML
    private void handleBackToMain(ActionEvent event) throws IOException {
        // Get the current stage (window) and close it
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleActualizar() {
        // Reload the data (this method can be implemented as needed)
    }

}
