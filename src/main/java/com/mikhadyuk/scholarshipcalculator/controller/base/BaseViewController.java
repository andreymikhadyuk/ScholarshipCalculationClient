package com.mikhadyuk.scholarshipcalculator.controller.base;

import com.mikhadyuk.scholarshipcalculator.connection.ServerConnection;
import com.mikhadyuk.scholarshipcalculator.controller.MainController;
import com.mikhadyuk.scholarshipcalculator.service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.Optional;

public class BaseViewController {
    private ServerConnection serverConnection;
    private UserService userService;

    @FXML
    private void initialize() {
        serverConnection = ServerConnection.getInstance();
        userService = UserService.getInstance();
    }

    @FXML
    private void logOut(ActionEvent event) {
        Optional<ButtonType> result = showConfirmationAlert("Подтверждение выхода из аккаунта"
        , "Вы уверены что хотите выйти из аккаунта?", null);
        if (result.isPresent() && result.get().getButtonData() == ButtonBar.ButtonData.OK_DONE) {
            MainController.showLoginView();
            MainController.setVisibleGoBackButton(false);
        }

        userService.clearCurrentUserDataInLocal();
        serverConnection.closeConnection();
    }

    @FXML
    private void goBack(ActionEvent event) {
        MainController.setVisibleGoBackButton(false);
        MainController.getMainLayout().setCenter(MainController.getGoBackPane());
    }

    @FXML
    private void fullExit(ActionEvent event) {
        Optional<ButtonType> result = showConfirmationAlert("Подтверждение выхода"
                , "Вы уверены что хотите выйти?", null);
        if (result.isPresent() && result.get().getButtonData() == ButtonBar.ButtonData.OK_DONE) {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.hide();
            stage.close();
        }
    }

    private Optional<ButtonType> showConfirmationAlert(String title, String headerText, String contentText) {
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
