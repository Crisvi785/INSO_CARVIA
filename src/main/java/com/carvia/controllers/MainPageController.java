package com.carvia.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import com.carvia.models.vo.VehicleVo;

public class MainPageController {

    @FXML
    private TextField searchField;

    @FXML
    private TableView<VehicleVo> carAdsTable;

    @FXML
    private TableColumn<VehicleVo, String> marcaColumn;

    @FXML
    private TableColumn<VehicleVo, String> modeloColumn;

    @FXML
    private TableColumn<VehicleVo, Integer> anoColumn;

    @FXML
    private TableColumn<VehicleVo, Integer> kilometrajeColumn;

    @FXML
    private TableColumn<VehicleVo, Double> precioColumn;

    private ObservableList<VehicleVo> carAdsList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        marcaColumn.setCellValueFactory(new PropertyValueFactory<>("marca"));
        modeloColumn.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        anoColumn.setCellValueFactory(new PropertyValueFactory<>("ano"));
        kilometrajeColumn.setCellValueFactory(new PropertyValueFactory<>("kilometraje"));
        precioColumn.setCellValueFactory(new PropertyValueFactory<>("precio"));

        loadCarAds();

        carAdsTable.setItems(carAdsList);
    }

    
    @FXML
    private void handleAddAnuncio() {
        System.out.println("Abrir formulario para agregar un anuncio");
    }

    @FXML
    private void handleViewMyAds() {
        System.out.println("Mostrar anuncios del usuario actual");
    }

    @FXML
    private void handleSettings() {
        System.out.println("Abrir configuración de la aplicación");
    }

    @FXML
    private void handleSearch() {
        String query = searchField.getText().toLowerCase();

        ObservableList<VehicleVo> filteredList = FXCollections.observableArrayList();
        for (VehicleVo ad : carAdsList) {
            if (ad.getMarca().toLowerCase().contains(query) ||
                ad.getModelo().toLowerCase().contains(query) ||
                String.valueOf(ad.getAno()).contains(query)) {
                filteredList.add(ad);
            }
        }

        carAdsTable.setItems(filteredList);
    }

    private void loadCarAds() {
        carAdsList.add(new VehicleVo("Toyota", "Corolla", 2018, 45000, 15000));
        carAdsList.add(new VehicleVo("Honda", "Civic", 2020, 30000, 18000));
        carAdsList.add(new VehicleVo("Ford", "Focus", 2016, 60000, 12000));
    }
}
