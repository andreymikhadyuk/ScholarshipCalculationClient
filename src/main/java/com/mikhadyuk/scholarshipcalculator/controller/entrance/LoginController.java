package com.mikhadyuk.scholarshipcalculator.controller.entrance;

import com.jfoenix.controls.JFXButton;
import com.mikhadyuk.scholarshipcalculator.controller.MainController;
import com.mikhadyuk.scholarshipcalculator.service.EntranceService;
import com.mikhadyuk.scholarshipcalculator.util.MessageUtil;
import com.mikhadyuk.scholarshipcalculator.util.PaneUtil;
import com.mikhadyuk.scholarshipcalculator.util.RegularExpUtil;
import com.mikhadyuk.scholarshipcalculator.util.SingletonUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    private EntranceService entranceService;

    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorLabel;

    @FXML
    private JFXButton loginButton;

    @FXML
    private void initialize() {
        entranceService = SingletonUtil.getInstance(EntranceService.class);
    }

    @FXML
    private void onLoginButtonClick(ActionEvent event) {
        if (!validInput()) {
            return;
        }

        if (entranceService.login(usernameTextField.getText(), passwordField.getText())) {
            errorLabel.setText(MessageUtil.getMessage("login.error.wrongLoginAndPassword"));
            errorLabel.setVisible(true);
            return;
        }
        MainController.setNewPane(PaneUtil.load("/view/menu/Menu.fxml"));
    }

    private boolean validInput()  {
        errorLabel.setVisible(false);
        if (!(RegularExpUtil.isCorrectString(usernameTextField.getText(), RegularExpUtil.LOGIN_REG_EXP))) {
            errorLabel.setText(MessageUtil.getMessage("login.error.wrongLogin"));
            errorLabel.setVisible(true);
            return false;
        }
        if (!(RegularExpUtil.isCorrectString(passwordField.getText(), RegularExpUtil.PASSWORD_REG_EXP))) {
            errorLabel.setText(MessageUtil.getMessage("login.error.wrongPassword"));
            errorLabel.setVisible(true);
            return false;
        }
        return true;
    }

    @FXML
    private void onRegistrationButtonClick(ActionEvent event) {
        MainController.setNewPane(PaneUtil.load("/view/entrance/Registration.fxml"));
    }
}
