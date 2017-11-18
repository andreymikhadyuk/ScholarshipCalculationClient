package com.mikhadyuk.scholarshipcalculator.controller.entrance;

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
    private UserService userService;

    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;

    @FXML
    private void initialize() {
        entranceService = SingletonUtil.getInstance(EntranceService.class);
        userService = SingletonUtil.getInstance(UserService.class);
    }

    @FXML
    private void onRegistrationButtonClick(ActionEvent event) {
        MainController.setNewPane(PaneUtil.load("/view/entrance/Registration.fxml"));
    }
}
