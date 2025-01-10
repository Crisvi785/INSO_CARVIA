package com.carvia.controllers;

import com.carvia.models.vto.VehicleAdVto;
import com.carvia.utils.AlertUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URI;
import java.sql.Connection;
import com.carvia.App;
import com.carvia.models.UserSession;
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
        // colAccion.setCellValueFactory(new PropertyValueFactory<>("comprar"));
        // colContacto.setCellValueFactory(new PropertyValueFactory<>("contacto"));
        
        // Set curstom format for colPrecio
        colPrecio.setCellFactory(new Callback<TableColumn<VehicleAdVto, Double>, TableCell<VehicleAdVto, Double>>() {
            @Override
            public TableCell<VehicleAdVto, Double> call(TableColumn<VehicleAdVto, Double> param) {
                return new TableCell<VehicleAdVto, Double>() {
                    private final Text text = new Text();
        
                    @Override
                    protected void updateItem(Double item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            text.setText(String.format("%.2f", item)); // Formatea el precio con dos decimales
                            setGraphic(text);
                        } else {
                            setGraphic(null);
                        }
                    }
                };
            }
        });

        // Set custom cell factory for colDescripcion
        colDescripcion.setCellFactory(new Callback<TableColumn<VehicleAdVto, String>, TableCell<VehicleAdVto, String>>() {
            @Override
            public TableCell<VehicleAdVto, String> call(TableColumn<VehicleAdVto, String> param) {
                return new TableCell<VehicleAdVto, String>() {
                    private final Text text = new Text();

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            text.setText(item);
                            text.wrappingWidthProperty().bind(colDescripcion.widthProperty());
                            setGraphic(text);
                        } else {
                            setGraphic(null);
                        }
                    }
                };
            }
        });
        
        colAccion.setCellFactory(param -> new TableCell<>() {
            /*  CÓDIGO PARA PASARELA WEB
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
            */

            private final Button botonComprar = new Button("Comprar");
            private final PaymentWindowController paymentWindowController = new PaymentWindowController();
            {
                botonComprar.setOnAction(event -> {
                    VehicleAdVto vehiculo = getTableView().getItems().get(getIndex());
                    int userId = UserSession.getLoggedInUserId(); // Método para obtener el ID del usuario logueado

                    if (vehiculo != null && userId > 0) {
                        try {
                            FXMLLoader loader = new FXMLLoader(App.class.getResource("/com/carvia/views/payment.fxml"));
                            Parent root = loader.load();
                            PaymentWindowController paymentWindowController = loader.getController();
                            paymentWindowController.setVehicleAndUser(vehiculo, userId);
                            Stage stage = new Stage();
                            stage.setScene(new Scene(root));
                            stage.show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
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
