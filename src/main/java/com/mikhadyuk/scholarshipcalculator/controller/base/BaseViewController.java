package com.mikhadyuk.scholarshipcalculator.controller.base;

import com.mikhadyuk.scholarshipcalculator.connection.ServerConnection;
import com.mikhadyuk.scholarshipcalculator.controller.MainController;
import com.mikhadyuk.scholarshipcalculator.service.UserService;
import com.mikhadyuk.scholarshipcalculator.util.PaneUtil;
import com.mikhadyuk.scholarshipcalculator.util.SingletonUtil;
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
        userService = SingletonUtil.getInstance(UserService.class);
    }

    @FXML
    private void logOut(ActionEvent event) {
        Optional<ButtonType> result = PaneUtil.showConfirmationModal("Подтверждение выхода из аккаунта"
        , "Вы уверены что хотите выйти из аккаунта?", null);
        if (result.isPresent() && result.get().getButtonData() == ButtonBar.ButtonData.OK_DONE) {
            MainController.setVisibleLogoutButton(false);
            MainController.setVisibleGoBackButton(false);
            MainController.showLoginView();
        }

        userService.clearCurrentUserDataInLocal();
        serverConnection.closeConnection();
    }

    @FXML
    private void goBack(ActionEvent event) {
        MainController.setVisibleGoBackButton(false);
        MainController.setNewPane(MainController.getGoBackPane());
    }

    @FXML
    private void fullExit(ActionEvent event) {
        Optional<ButtonType> result = PaneUtil.showConfirmationModal("Подтверждение выхода"
                , "Вы уверены что хотите выйти?", null);
        if (result.isPresent() && result.get().getButtonData() == ButtonBar.ButtonData.OK_DONE) {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.hide();
            stage.close();
        }
    }
}
