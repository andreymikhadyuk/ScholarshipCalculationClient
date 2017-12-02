package com.mikhadyuk.scholarshipcalculator.controller.entrance;

import com.jfoenix.controls.JFXRadioButton;
import com.mikhadyuk.scholarshipcalculator.controller.MainController;
import com.mikhadyuk.scholarshipcalculator.model.enums.Role;
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

public class RegistrationController {
    private EntranceService entranceService;

    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField patronymicTextField;
    @FXML
    private JFXRadioButton accountantRadioButton;
    @FXML
    private JFXRadioButton secretaryRadioButton;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Label errorLabel;

    @FXML
    private void initialize() {
        entranceService = SingletonUtil.getInstance(EntranceService.class);
        MainController.setGoBackPane(PaneUtil.load("/view/entrance/Login.fxml"));
    }

    @FXML
    private void register(ActionEvent event) {
        if (!validInput()) {
            return;
        }

        if (entranceService.register(usernameTextField.getText()
                , passwordField.getText()
                , accountantRadioButton.isSelected() ? Role.ROLE_ACCOUNTANT : Role.ROLE_SECRETARY
                , lastNameTextField.getText()
                , firstNameTextField.getText()
                , patronymicTextField.getText())) {
            errorLabel.setText(MessageUtil.getMessage("registration.error.notUniqueUsername"));
            errorLabel.setVisible(true);
            return;
        }
    }

    private boolean validInput()  {
        errorLabel.setVisible(false);
        if (!(RegularExpUtil.isCorrectString(lastNameTextField.getText(), RegularExpUtil.NAME_REG_EXP)) ||
                !(RegularExpUtil.isCorrectString(firstNameTextField.getText(), RegularExpUtil.NAME_REG_EXP)) ||
                !(RegularExpUtil.isCorrectString(patronymicTextField.getText(), RegularExpUtil.NAME_REG_EXP))) {
            errorLabel.setText(MessageUtil.getMessage("registration.error.wrongName"));
            errorLabel.setVisible(true);
            return false;
        }
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
        if (!(RegularExpUtil.isCorrectString(confirmPasswordField.getText(), RegularExpUtil.PASSWORD_REG_EXP))) {
            errorLabel.setText(MessageUtil.getMessage("registration.error.wrongConfirmPassword"));
            errorLabel.setVisible(true);
            return false;
        }
        if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            errorLabel.setText(MessageUtil.getMessage("registration.error.passwordsNotEquals"));
            errorLabel.setVisible(true);
            return false;
        }
        return true;
    }
}
