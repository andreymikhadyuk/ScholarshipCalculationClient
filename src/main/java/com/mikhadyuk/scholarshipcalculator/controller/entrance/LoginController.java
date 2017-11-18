package com.mikhadyuk.scholarshipcalculator.controller.entrance;

import com.jfoenix.controls.JFXButton;
import com.mikhadyuk.scholarshipcalculator.controller.MainController;
import com.mikhadyuk.scholarshipcalculator.service.EntranceService;
import com.mikhadyuk.scholarshipcalculator.service.UserService;
import com.mikhadyuk.scholarshipcalculator.util.PaneUtil;
import com.mikhadyuk.scholarshipcalculator.util.SingletonUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    private EntranceService entranceService;

    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;

    @FXML
    private JFXButton loginButton;

    @FXML
    private void initialize() {
        entranceService = SingletonUtil.getInstance(EntranceService.class);
    }

    @FXML
    private void onLoginButtonClick(ActionEvent event) {
        // Validation
        if (entranceService.login(usernameTextField.getText(), passwordField.getText())) {
        }
    }

    @FXML
    private void onRegistrationButtonClick(ActionEvent event) {
        MainController.setNewPane(PaneUtil.load("/view/entrance/Registration.fxml"));
    }
}
