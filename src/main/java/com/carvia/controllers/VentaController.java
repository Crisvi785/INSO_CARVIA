package com.carvia.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;
import java.io.IOException;
import java.util.Date;

import com.carvia.App;
import com.carvia.models.dao.AnuncioDao;
import com.carvia.models.dao.VehicleDao;
import com.carvia.models.vo.AnuncioVo;
import com.carvia.models.vo.VehicleVo;

import javafx.scene.control.ComboBox;

public class VentaController {

    @FXML
    private TextField txtMarca;

    @FXML
    private TextField txtModelo;

    @FXML
    private TextField txtAnio;

    @FXML
    private TextField txtKms;

    @FXML
    private ComboBox<String> cmbGasolina;

    @FXML
    private ComboBox<String> cmbTransmision;

    @FXML
    private TextField txtPrecio;

    @FXML
    private TextArea txtDescripcion;

    @FXML
    private TextField txtFechaCreacion;

    @FXML
    public void initialize() {
        // Opciones para los ComboBox
        cmbGasolina.setItems(FXCollections.observableArrayList("Gasolina", "Diésel", "Eléctrico", "Híbrido"));
        cmbTransmision.setItems(FXCollections.observableArrayList("Manual", "Automático"));
    }

    private List<File> selectedImages;

    @FXML
    private void handleUploadImages() {
        // Crear un FileChooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Imágenes del Coche");

        // Filtros de extensión para limitar los archivos seleccionables a imágenes
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg"));

        // Abrir el explorador de archivos y permitir selección múltiple
        Stage stage = new Stage(); // Usamos un nuevo Stage para mostrar el explorador de archivos
        selectedImages = fileChooser.showOpenMultipleDialog(stage);

        if (selectedImages != null && !selectedImages.isEmpty()) {
            // Si se seleccionaron imágenes, imprimir sus rutas
            System.out.println("Imágenes seleccionadas:");
            for (File file : selectedImages) {
                System.out.println(file.getAbsolutePath());
            }

            // Aquí puedes llamar a un método para subir las imágenes a la nube
            uploadImagesToCloud(selectedImages);
        } else {
            System.out.println("No se seleccionaron imágenes.");
        }
    }

    private void uploadImagesToCloud(List<File> images) {
        System.out.println("Subiendo imágenes a la nube...");
        for (File image : images) {
            // Aquí puedes integrar tu API de almacenamiento en la nube (e.g., AWS S3,
            // Google Cloud Storage, Firebase Storage)
            // Por ejemplo: cloudStorageService.upload(image);
            System.out.println("Subida simulada de: " + image.getName());
        }
        System.out.println("Imágenes subidas exitosamente.");
    }

    @FXML
    private void handlePublishAd() {
        try {
            // Validar los datos del formulario
            if (txtMarca.getText().isEmpty() || txtModelo.getText().isEmpty() || txtAnio.getText().isEmpty() ||
                    txtKms.getText().isEmpty() || cmbGasolina.getValue() == null || cmbTransmision.getValue() == null ||
                    txtDescripcion.getText().isEmpty() || txtPrecio.getText().isEmpty() ||
                    selectedImages == null || selectedImages.isEmpty()) {
                System.out.println("Por favor, completa todos los campos y sube al menos una imagen.");
                return;
            }

            // Crear un objeto VehicleVo con los datos del formulario
            VehicleVo vehiculo = new VehicleVo();
            vehiculo.setMarca(txtMarca.getText());
            vehiculo.setModelo(txtModelo.getText());
            vehiculo.setAnio(Integer.parseInt(txtAnio.getText()));
            vehiculo.setKilometraje(Integer.parseInt(txtKms.getText()));
            vehiculo.setTipoCombustible(cmbGasolina.getValue());
            vehiculo.setTransmision(cmbTransmision.getValue());

            // Crear un objeto AnuncioVo con los datos del formulario
            AnuncioVo anuncio = new AnuncioVo();
            anuncio.setDescripcion(txtDescripcion.getText());
            anuncio.setPrecio(Double.parseDouble(txtPrecio.getText()));

            // Obtener la URL de la primera imagen como ejemplo (puedes modificarlo según
            // tus requisitos)

            // Instanciar los DAOs y guardar los datos en la base de datos
            VehicleDao vehiculoDAO = new VehicleDao();
            AnuncioDao anuncioDAO = new AnuncioDao();

            // Guardar vehículo primero y luego el anuncio
            if (vehiculoDAO.insertVehiculo(vehiculo) && vehiculoDAO.insertAnuncio(anuncio)) {
                System.out.println("El anuncio del vehículo ha sido publicado correctamente.");
            } else {
                System.out.println("Hubo un error al publicar el anuncio o el vehículo.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Por favor, ingresa datos válidos para los campos numéricos (Año, Kilómetros, Precio).");
        } catch (Exception e) {
            System.out.println("Error al publicar el anuncio: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void handleCancel() throws IOException {
        App.setRoot("mainpage");
    }

}