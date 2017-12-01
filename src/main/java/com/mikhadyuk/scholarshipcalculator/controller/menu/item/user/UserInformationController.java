package com.mikhadyuk.scholarshipcalculator.controller.menu.item.user;

import com.mikhadyuk.scholarshipcalculator.model.User;
import com.mikhadyuk.scholarshipcalculator.model.enums.Role;
import com.mikhadyuk.scholarshipcalculator.service.UserService;
import com.mikhadyuk.scholarshipcalculator.util.SingletonUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;

public class UserInformationController {
    private UserService userService;

    private User user;
    private boolean okClicked = false;

    private ObservableList<Role> roles = FXCollections.observableArrayList();

    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField patronymicTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordField;
    @FXML
    private ComboBox roleComboBox;

    @FXML
    private void initialize() {
        userService = SingletonUtil.getInstance(UserService.class);

        roles.addAll(Role.values());
        roleComboBox.setItems(roles);
        roleComboBox.getSelectionModel().select(0);
    }

    @FXML
    private void onOkButtonClick(ActionEvent event) {
        user.setLastName(lastNameTextField.getText());
        user.setFirstName(firstNameTextField.getText());
        user.setPatronymic(patronymicTextField.getText());
        user.setUsername(usernameTextField.getText());
        if (!passwordField.getText().isEmpty()) {
            user.setPassword(passwordField.getText());
        }
        user.setRole((Role) roleComboBox.getSelectionModel().getSelectedItem());
        okClicked = true;
        cancel(event);
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        if (this.user == null) {
            this.user = new User();
        }
        lastNameTextField.setText(this.user.getLastName());
        firstNameTextField.setText(this.user.getFirstName());
        patronymicTextField.setText(this.user.getPatronymic());
        usernameTextField.setText(this.user.getUsername());
        roleComboBox.getSelectionModel().select(this.user.getRole());
    }

    @FXML
    void cancel(ActionEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.hide();
        stage.close();
    }

    public void disableElementsForView() {
        lastNameTextField.setEditable(false);
        firstNameTextField.setEditable(false);
        patronymicTextField.setEditable(false);
        usernameTextField.setEditable(false);
        passwordField.setEditable(false);

        roleComboBox.setDisable(true);
    }
}
