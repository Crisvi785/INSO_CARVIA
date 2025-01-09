package com.carvia.controllers;

import com.carvia.models.dao.UserDao;
import com.carvia.models.vo.UserVo;
import com.carvia.utils.AlertUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class AdminsController {

    @FXML
    private TableView<UserVo> userTable;

    @FXML
    private TableColumn<UserVo, Integer> colId;

    @FXML
    private TableColumn<UserVo, String> colUsername;

    @FXML
    private TableColumn<UserVo, String> colEmail;

    private UserDao userDao;

    public AdminsController() {
        userDao = new UserDao();
    }

    @FXML
    private void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
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
                    userDao.eliminarUsuario(selectedUser);
                    userTable.getItems().remove(selectedUser);
                    AlertUtil.showAlert("Success", "User deleted successfully", Alert.AlertType.INFORMATION);
                }
            } catch (Exception e) {
                e.printStackTrace();
                AlertUtil.showAlert("Error", "Error deleting user", Alert.AlertType.ERROR);
            }
        } else {
            AlertUtil.showAlert("Warning", "Please select a user to delete", Alert.AlertType.WARNING);
        }
    }

}

