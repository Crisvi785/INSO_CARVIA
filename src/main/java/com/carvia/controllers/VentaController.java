package com.carvia.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import java.io.File;
import java.util.List;
import java.io.IOException;
import com.carvia.App;
import com.carvia.models.UserSession;
import com.carvia.models.dao.AnuncioDao;
import com.carvia.models.dao.UserDao;
import com.carvia.models.dao.VehicleDao;
import com.carvia.models.vo.AnuncioVo;
import com.carvia.models.vo.UserVo;
import com.carvia.models.vo.VehicleVo;

import javafx.scene.control.ComboBox;

public class VentaController {

    @FXML
    private ComboBox<String> cmbMarca;

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
    private ComboBox<String> cmbProvincia;

    @FXML
    private TextArea txtDescripcion;

    @FXML
    private TextField txtFechaCreacion;

    @FXML
    public void initialize() {
        // Opciones para los ComboBox
        cmbGasolina.setItems(FXCollections.observableArrayList("Gasolina", "Diésel", "Eléctrico", "Híbrido"));
        cmbTransmision.setItems(FXCollections.observableArrayList("Manual", "Automático"));
        cmbMarca.setItems(FXCollections.observableArrayList("Acura", "Alfa Romeo", "Aston Martin", "Audi", "Bentley", "BMW", "Bugatti", "Buick", "Chevrolet", "Chrysler", "Citroën", "Dodge", "Ferrari", "Fiat", "Ford", "GMC", "Honda", "Hyundai", "Infiniti", "Jaguar", "Kia", "Lamborghini", "Land Rover", "Lexus", "Lincoln", "Maserati", "Mazda", "Mercedes-Benz", "Mitsubishi", "Nissan", "Opel", "Peugeot", "Porsche", "Renault", "Rolls-Royce", "Seat", "Skoda", "Subaru", "Tesla", "Toyota", "Volkswagen", "Volvo"));
        cmbProvincia.setItems(FXCollections.observableArrayList("Ávila", "Badajoz", "Barcelona", "Burgos", "Cáceres", "Cádiz", "Castellón", "Ciudad Real", "Córdoba", "Cuenca", "Gerona", "Granada", "Guadalajara", "Huelva", "Huesca", "Jaén", "La Coruña", "La Rioja", "Las Palmas", "León", "Lérida", "Lugo", "Madrid", "Málaga", "Murcia", "Navarra", "Orense", "Palencia", "Pontevedra", "Salamanca", "Santa Cruz de Tenerife", "Segovia", "Sevilla", "Soria", "Tarragona", "Teruel", "Toledo", "Valencia", "Valladolid", "Vizcaya", "Zamora", "Zaragoza"));
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
        if (cmbMarca.getValue().isEmpty() || txtModelo.getText().isEmpty() || txtAnio.getText().isEmpty() ||
                txtKms.getText().isEmpty() || cmbGasolina.getValue() == null || cmbTransmision.getValue() == null ||
                txtDescripcion.getText().isEmpty() || txtPrecio.getText().isEmpty() || cmbProvincia.getValue().isEmpty() || 
                selectedImages == null || selectedImages.isEmpty()) {
            System.out.println("Por favor, completa todos los campos y sube al menos una imagen.");
            return;
        }

        // Crear un objeto VehicleVo con los datos del formulario
        VehicleVo vehiculo = new VehicleVo();
        vehiculo.setMarca(cmbMarca.getValue());
        vehiculo.setModelo(txtModelo.getText());
        vehiculo.setAnio(Integer.parseInt(txtAnio.getText()));
        vehiculo.setKilometraje(Integer.parseInt(txtKms.getText()));
        vehiculo.setTipoCombustible(cmbGasolina.getValue());
        vehiculo.setTransmision(cmbTransmision.getValue());

        // Instanciar el DAO para vehículos
        VehicleDao vehiculoDAO = new VehicleDao();

        // Insertar el vehículo y obtener el ID generado
        int vehiculoId = vehiculoDAO.insertVehiculo(vehiculo);
        if (vehiculoId <= 0) {
            System.out.println("Hubo un error al guardar el vehículo en la base de datos.");
            return;
        }

        // Crear un objeto AnuncioVo con los datos del formulario
        AnuncioVo anuncio = new AnuncioVo();
        anuncio.setDescripcion(txtDescripcion.getText());
        anuncio.setPrecio(Double.parseDouble(txtPrecio.getText()));
        anuncio.setProvincia(cmbProvincia.getValue());
        anuncio.setIdVehiculo(vehiculoId); // Asignar el ID del vehículo al anuncio

        // Instanciar el DAO para anuncios
        AnuncioDao anuncioDAO = new AnuncioDao();

         // Asignar el ID del usuario autenticado al anuncio
         int idUsuario = getAuthenticatedUserId(); 
         anuncio.setIdUsuario(idUsuario);

        // Insertar el anuncio en la base de datos
        if (vehiculoDAO.insertAnuncio(anuncio)) {
            System.out.println("El anuncio del vehículo ha sido publicado correctamente.");
        } else {
            System.out.println("Hubo un error al guardar el anuncio en la base de datos.");
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

    private int getAuthenticatedUserId() {
    if (!UserSession.isLoggedIn()) {
        System.out.println("No hay un usuario autenticado.");
        return -1; // Maneja el caso donde no hay usuario autenticado
    }

    // Obtén el nombre de usuario logueado
    String username = UserSession.getLoggedInUser().orElseThrow(() -> 
        new IllegalStateException("Usuario no encontrado en la sesión"));

    // Usa UserDao para buscar el ID del usuario
    UserDao userDao = new UserDao();
    UserVo user = userDao.getUserByUsername(username);

    if (user == null) {
        System.out.println("Usuario no encontrado en la base de datos.");
        return -1; // Maneja el caso donde el usuario no existe
    }

    return user.getId(); // Devuelve el ID del usuario autenticado
}


}