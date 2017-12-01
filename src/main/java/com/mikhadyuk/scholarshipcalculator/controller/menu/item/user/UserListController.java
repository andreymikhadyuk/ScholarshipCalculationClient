package com.mikhadyuk.scholarshipcalculator.controller.menu.item.user;

import com.mikhadyuk.scholarshipcalculator.controller.MainController;
import com.mikhadyuk.scholarshipcalculator.controller.enums.ControllerAction;
import com.mikhadyuk.scholarshipcalculator.keeper.ControllerKeeper;
import com.mikhadyuk.scholarshipcalculator.model.User;
import com.mikhadyuk.scholarshipcalculator.service.UserService;
import com.mikhadyuk.scholarshipcalculator.util.PaneUtil;
import com.mikhadyuk.scholarshipcalculator.util.SingletonUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class UserListController {
    private UserService userService;

    private ObservableList<User> userObservableList = FXCollections.observableArrayList();

    @FXML
    private TableView tableView;
    @FXML
    private TableColumn<User, String> nameColumn;
    @FXML
    private TableColumn<User, String> loginColumn;
    @FXML
    private TableColumn<User, String> roleColumn;

    @FXML
    private void initialize() {
        MainController.setGoBackPane(PaneUtil.load("/view/menu/Menu.fxml"));

        userService = SingletonUtil.getInstance(UserService.class);
        userObservableList.addAll(userService.getAllUsers());

        setUpNameColumn();
        setUpLoginColumn();
        setUpRoleColumn();

        tableView.setItems(userObservableList);
    }

    private void setUpNameColumn() {
        nameColumn.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().getLastName() + " "
                        + c.getValue().getFirstName() + " " + c.getValue().getPatronymic()
        ));
    }

    private void setUpLoginColumn() {
        loginColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
    }

    private void setUpRoleColumn() {
        roleColumn.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getRole().getLabel()));
    }

    @FXML
    private void add() {
        User user = showModal("Добавление нового пользователя", ControllerAction.ADD);
        if (user != null) {
            user = userService.save(user);
            userObservableList.add(user);
            tableView.refresh();
        }
    }

    @FXML
    private void update() {
        User user = showModal("Изменение информации о пользователе", ControllerAction.UPDATE);
        if (user != null) {
            user = userService.update(user);
            updateObjectInTable(user);
            tableView.refresh();
        }
    }

    private void updateObjectInTable(User user) {
        if (user == null) {
            return;
        }
        for (int i = 0; i < userObservableList.size(); i++) {
            if (userObservableList.get(i).getId() == user.getId()) {
                userObservableList.set(i, user);
            }
        }
    }

    @FXML
    private void delete() {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            User user = (User) tableView.getSelectionModel().getSelectedItem();
            userService.delete(user);
            userObservableList.remove(selectedIndex);
            tableView.refresh();
        } else {
            showSelectErrorModal();
        }
    }

    @FXML
    private void view() {
        showModal("Просмотр информации о пользователе", ControllerAction.VIEW);
    }

    private User showModal(String title, ControllerAction controllerAction) {
        ControllerKeeper<UserInformationController> controllerKeeper = new ControllerKeeper<>();
        Stage modal = PaneUtil.createModal("/view/menu/item/user/UserInformation.fxml"
                , title, controllerKeeper);
        UserInformationController controller = controllerKeeper.getController();

        switch (controllerAction) {
            case ADD:
                controller.setUser(null);
                break;
            case VIEW:
                controller.disableElementsForView();
            case UPDATE:
                if (!setUserToController(controller)) {
                    return null;
                }
                break;
        }

        modal.showAndWait();
        User user = controller.getUser();
        return controller.isOkClicked() ? user : null;
    }

    private boolean setUserToController(UserInformationController controller) {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            controller.setUser((User) tableView.getSelectionModel().getSelectedItem());
        } else {
            showSelectErrorModal();
            return false;
        }
        return true;
    }

    private void showSelectErrorModal() {
        PaneUtil.showConfirmModal("Ошибка при выборе"
                , "Не выбран пользователь"
                , "Пожалуйста, выберите пользователя в таблице.");
    }
}
