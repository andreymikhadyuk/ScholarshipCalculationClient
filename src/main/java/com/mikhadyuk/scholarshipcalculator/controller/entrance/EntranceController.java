package com.mikhadyuk.scholarshipcalculator.controller.entrance;

import com.mikhadyuk.scholarshipcalculator.service.EntranceService;
import com.mikhadyuk.scholarshipcalculator.service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class EntranceController {
    private EntranceService entranceService;
    private UserService userService;

    @FXML
    private TextField loginTextField;
    @FXML
    private PasswordField passwordTextField;

    @FXML
    private void initialize() {
        entranceService = EntranceService.getInstance();
        userService = UserService.getInstance();
    }

    @FXML
    private void register(ActionEvent event) {
    }
}
