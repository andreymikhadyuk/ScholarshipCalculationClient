package com.mikhadyuk.scholarshipcalculator.controller.menu;

import com.jfoenix.controls.JFXButton;
import com.mikhadyuk.scholarshipcalculator.controller.MainController;
import com.mikhadyuk.scholarshipcalculator.model.User;
import com.mikhadyuk.scholarshipcalculator.model.enums.Role;
import com.mikhadyuk.scholarshipcalculator.service.UserService;
import com.mikhadyuk.scholarshipcalculator.util.PaneUtil;
import com.mikhadyuk.scholarshipcalculator.util.SingletonUtil;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class MenuController {
    private UserService userService;

    @FXML
    private VBox menuItemsBox;

    @FXML
    private void initialize() {
        userService = SingletonUtil.getInstance(UserService.class);
        User user = userService.loadCurrentUser();
        if (user != null) {
            List<JFXButton> buttons = createButtons(user.getRole());
            addButtonsToVBox(buttons);
        }
        MainController.setVisibleLogoutButton(true);
        MainController.setVisibleGoBackButton(false);
    }

    private void addButtonsToVBox(List<JFXButton> buttons) {
        menuItemsBox.getChildren().addAll(buttons);
    }

    private List<JFXButton> createButtons(Role role) {
        List<JFXButton> buttons = new ArrayList<>();
        Pane studentInformationPane = null;
        Pane scholarshipInformationPane = null;
        Pane usersPane = null;
        switch (role) {
            case ROLE_SECRETARY:
                buttons.add(createMenuButton("Информация о студентах", studentInformationPane));
                // MainController.setNewPane(PaneUtil.load("/view//Registration.fxml"));
                break;
            case ROLE_ACCOUNTANT:
                buttons.add(createMenuButton("Информация о стипендиях", scholarshipInformationPane));
                break;
            case ROLE_ADMIN:
                buttons.add(createMenuButton("Информация о студентах", studentInformationPane));
                buttons.add(createMenuButton("Информация о стипендиях", scholarshipInformationPane));
                buttons.add(createMenuButton("Работа с пользователями", usersPane));
                break;
        }
        return buttons;
    }

    // Информация о студентах
    // Информация о стипендиях
    // Работа с пользователями
    private JFXButton createMenuButton(String buttonName, Pane pane) {
        JFXButton button = new JFXButton(buttonName);
        button.setStyle("-fx-font: 16px System; " +
                "-fx-background-color: #d2224e; " +
                "-fx-text-fill: #fff");
        button.setPrefWidth(300.);
        button.setCursor(Cursor.HAND);
        addListener(button, pane);
        return button;
    }

    private JFXButton addListener(JFXButton button, Pane pane) {
        button.setOnAction(e -> {
            MainController.setNewPane(pane);
        });
        return button;
    }
}
