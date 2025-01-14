package com.carvia.controllers;

import com.carvia.App;
import com.carvia.models.UserSession;
import com.carvia.models.dao.AnuncioDao;
import com.carvia.models.dao.UserDao;
import com.carvia.models.vo.UserVo;
import com.carvia.utils.AlertUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.List;

public class AdminsController {

    @FXML
    private TableView<UserVo> userTable;

    @FXML
    private TableColumn<UserVo, Integer> colId;

    @FXML
    private TableColumn<UserVo, String> colUsername;

    @FXML
    private TableColumn<UserVo, String> colFullName;

    @FXML
    private TableColumn<UserVo, String> colEmail;

    private UserDao userDao;
    private AnuncioDao anuncioDao;

    public AdminsController() {
        userDao = new UserDao();
        anuncioDao = new AnuncioDao();
    }

    @FXML
    private void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        loadUserData();
    }

    private void loadUserData() {
        try {
            List<UserVo> userList = userDao.listarUsuarios();
            ObservableList<UserVo> userObservableList = FXCollections.observableArrayList(userList);
            userTable.setItems(userObservableList);
        } catch (Exception e) {
            e.printStackTrace();
            AlertUtil.showAlert("Error", "Error loading user data", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleDeleteUser() {
        UserVo selectedUser = userTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            try {
                if (selectedUser.getId() == 1) {
                    AlertUtil.showAlert("Error", "Cannot delete admin user", Alert.AlertType.ERROR);
                } else {
                    
                    if (anuncioDao.eliminarAnunciosPorUsuario(selectedUser.getId())) {
                        System.out.println("Anuncios eliminados correctamente");
                        userDao.eliminarUsuario(selectedUser);
                        userTable.getItems().remove(selectedUser);
                        AlertUtil.showAlert("Éxito", "Usuario eliminado correctamente", AlertType.INFORMATION);
                        
                    } else {
                        AlertUtil.showAlert("Error", "Los anuncios asociados a este usuario no han podido ser eliminados.", AlertType.ERROR);
                        System.out.println("Error al eliminar anuncios");
                    }
                    
                }
            } catch (Exception e) {
                e.printStackTrace();
                AlertUtil.showAlert("Error", "Error al eliminar el usuario.", AlertType.ERROR);
            }
        } else {
            AlertUtil.showAlert("Warning", "Please select a user to delete", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void handleLogout() throws IOException {
        UserSession.logout();
        System.out.println("Sesión cerrada correctamente");

        // Redirigir a la pantalla de inicio de sesión
        App.setRoot("login");
    }

}

