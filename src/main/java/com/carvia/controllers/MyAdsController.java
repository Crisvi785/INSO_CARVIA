package com.carvia.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import com.carvia.models.vto.VehicleAdVto;
import com.carvia.models.UserSession;
import com.carvia.models.dao.AnuncioDao;

import java.util.ArrayList;
import java.util.List;

public class MyAdsController {

    @FXML
    private TableView<VehicleAdVto> adsTable;

    @FXML
    private TableColumn<VehicleAdVto, String> colBrand;

    @FXML
    private TableColumn<VehicleAdVto, String> colModel;

    @FXML
    private TableColumn<VehicleAdVto, Integer> colYear;

    @FXML
    private TableColumn<VehicleAdVto, Integer> colKilometers;

    @FXML
    private TableColumn<VehicleAdVto, String> colFuelType;

    @FXML
    private TableColumn<VehicleAdVto, String> colTransmission;

    @FXML
    private TableColumn<VehicleAdVto, String> colDescription;

    @FXML
    private TableColumn<VehicleAdVto, Double> colPrice;

    private ObservableList<VehicleAdVto> adsList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Configurar columnas
        colBrand.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getMarca()));
        colModel.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getModelo()));
        colYear.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getAno()).asObject());
        colKilometers.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getKilometraje()).asObject());
        colFuelType.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getCombustible()));
        colTransmission.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTransmision()));
        colDescription.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDescripcion()));
        colPrice.setCellValueFactory(data -> new javafx.beans.property.SimpleDoubleProperty(data.getValue().getPrecio()).asObject());

        // Cargar anuncios del usuario logueado
        loadUserAds();
    }

    private void loadUserAds() {
        int userId = UserSession.getLoggedInUserId();
        if (userId != -1) {
            AnuncioDao anuncioDao = new AnuncioDao();
    
            try {
                // Obtener todos los anuncios
                List<VehicleAdVto> allAds = anuncioDao.filtrarAnuncios(null, null, 0.0, Double.MAX_VALUE);
    
                // Filtrar anuncios del usuario logueado
                List<VehicleAdVto> userAds = new ArrayList<>();
                for (VehicleAdVto ad : allAds) {
                    if (ad.getIdUsuario() == userId) {
                        userAds.add(ad);
                    }
                }
    
                if (userAds.isEmpty()) {
                    // Si no hay anuncios del usuario, mostrar un mensaje
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "No tienes anuncios publicados.", ButtonType.OK);
                    alert.showAndWait();
                    return; // Salir del método si no hay anuncios
                }
    
                // Cargar los anuncios filtrados en la tabla
                adsList.addAll(userAds);
                adsTable.setItems(adsList);
    
            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Ocurrió un error al cargar los anuncios.", ButtonType.OK);
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Usuario no logueado.", ButtonType.OK);
            alert.showAndWait();
        }
    }
    
    

    @FXML
    private void handleDeleteAd() {
        VehicleAdVto selectedAd = adsTable.getSelectionModel().getSelectedItem();
        if (selectedAd != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "¿Estás seguro de eliminar este anuncio?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                AnuncioDao anuncioDao = new AnuncioDao();
                boolean isDeleted = anuncioDao.deleteAd(selectedAd.getId()); // Asegúrate de usar el ID del anuncio
                System.out.println("ID del anuncio seleccionado para eliminar: " + selectedAd.getId());
                
                if (isDeleted) {
                    adsList.remove(selectedAd); // Elimina el anuncio de la lista visual
                    adsTable.refresh(); // Refresca la tabla para reflejar los cambios
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "Anuncio eliminado correctamente.", ButtonType.OK);
                    successAlert.showAndWait();
                } else {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR, "No se pudo eliminar el anuncio de la base de datos.", ButtonType.OK);
                    errorAlert.showAndWait();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Selecciona un anuncio para eliminar.", ButtonType.OK);
            alert.showAndWait();
        }
    }
}
