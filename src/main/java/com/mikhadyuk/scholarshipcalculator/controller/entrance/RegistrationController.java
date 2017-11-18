package com.mikhadyuk.scholarshipcalculator.controller.entrance;

import com.jfoenix.controls.JFXRadioButton;
import com.mikhadyuk.scholarshipcalculator.controller.MainController;
import com.mikhadyuk.scholarshipcalculator.model.enums.Role;
import com.mikhadyuk.scholarshipcalculator.service.EntranceService;
import com.mikhadyuk.scholarshipcalculator.service.UserService;
import com.mikhadyuk.scholarshipcalculator.util.PaneUtil;
import com.mikhadyuk.scholarshipcalculator.util.SingletonUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    private void initialize() {
        entranceService = SingletonUtil.getInstance(EntranceService.class);
        MainController.setGoBackPane(PaneUtil.load("/view/entrance/Login.fxml"));
    }

    @FXML
    private void register(ActionEvent event) {
        // validation
        entranceService.register(usernameTextField.getText(), passwordField.getText()
        , accountantRadioButton.isSelected() ? Role.ROLE_ACCOUNTANT : Role.ROLE_SECRETARY
        , lastNameTextField.getText(), firstNameTextField.getText(), patronymicTextField.getText());
    }
}
