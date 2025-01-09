package com.carvia;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class App extends Application {

    private static final Logger logger = LogManager.getLogger(App.class);

    private static Stage primaryStage;
    private static final double SMALL_WIDTH = 1050;
    private static final double SMALL_HEIGHT = 750;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        setRoot("mainpage");
        primaryStage.setTitle("Carvia");

        primaryStage.setResizable(false);
        primaryStage.setWidth(SMALL_WIDTH);
        primaryStage.setHeight(SMALL_HEIGHT);
        primaryStage.fullScreenProperty().addListener((obs, wasFullScreen, isNowFullScreen) -> {
            if (!isNowFullScreen) {
                primaryStage.setWidth(SMALL_WIDTH);
                primaryStage.setHeight(SMALL_HEIGHT);
            }
        });

        primaryStage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        logger.info("Loading page: " + fxml);
        FXMLLoader fxmlLoader = new FXMLLoader(
                App.class.getResource("/com/carvia/views/" + fxml + ".fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setScene(new Scene(root));
    }

    public static void main(String[] args) {
        logger.info("Starting Carvia instance");
        launch(args);
    }

    public static Object getController() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getController'");
    }
}
