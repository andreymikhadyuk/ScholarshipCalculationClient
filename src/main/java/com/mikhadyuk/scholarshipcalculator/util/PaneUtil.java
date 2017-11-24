package com.mikhadyuk.scholarshipcalculator.util;

import com.mikhadyuk.scholarshipcalculator.controller.MainController;
import com.mikhadyuk.scholarshipcalculator.keeper.ControllerKeeper;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class PaneUtil {
    public static Pane load(String path) {
        Pane pane = null;
        try {
            pane = FXMLLoader.load(MainController.class.getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pane;
    }

    public static Stage createModal(String path, String title, ControllerKeeper controllerKeeper) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainController.class.getResource(path));
        Pane pane = null;
        try {
            pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // Create the dialog Stage.
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(MainController.getMainStage());
        stage.setResizable(false);

        Scene scene = new Scene(pane);
        stage.setScene(scene);

        controllerKeeper.setController(loader.getController());
        return stage;
    }

    public static void showConfirmModal(String title, String header, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(contentText);

        alert.showAndWait();
    }
}
