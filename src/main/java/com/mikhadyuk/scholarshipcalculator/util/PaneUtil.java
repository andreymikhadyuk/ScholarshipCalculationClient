package com.mikhadyuk.scholarshipcalculator.util;

import com.mikhadyuk.scholarshipcalculator.controller.MainController;
import com.mikhadyuk.scholarshipcalculator.keeper.ControllerKeeper;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

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

    public static void showInformationModal(String title, String header, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(contentText);

        alert.showAndWait();
    }

    public static Optional<ButtonType> showConfirmationModal(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        ButtonType buttonTypeYes = new ButtonType("Да", ButtonBar.ButtonData.OK_DONE);
        ButtonType buttonTypeNo = new ButtonType("Нет", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        return alert.showAndWait();
    }
}
