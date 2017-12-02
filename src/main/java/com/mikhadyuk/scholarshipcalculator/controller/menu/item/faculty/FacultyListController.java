package com.mikhadyuk.scholarshipcalculator.controller.menu.item.faculty;

import com.mikhadyuk.scholarshipcalculator.controller.MainController;
import com.mikhadyuk.scholarshipcalculator.controller.enums.ControllerAction;
import com.mikhadyuk.scholarshipcalculator.keeper.ControllerKeeper;
import com.mikhadyuk.scholarshipcalculator.model.Faculty;
import com.mikhadyuk.scholarshipcalculator.service.FacultyService;
import com.mikhadyuk.scholarshipcalculator.util.PaneUtil;
import com.mikhadyuk.scholarshipcalculator.util.SingletonUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

import java.util.Optional;

public class FacultyListController {
    private FacultyService facultyService;

    private ObservableList<Faculty> facultyObservableList = FXCollections.observableArrayList();

    @FXML
    private TableView tableView;
    @FXML
    private TableColumn<Faculty, String> shortFacultyNameColumn;
    @FXML
    private TableColumn<Faculty, String> facultyNameColumn;

    @FXML
    private void initialize() {
        MainController.setGoBackPane(PaneUtil.load("/view/menu/Menu.fxml"));

        facultyService = SingletonUtil.getInstance(FacultyService.class);
        facultyObservableList.addAll(facultyService.getAllFaculties());

        setUpShortFacultyNameColumn();
        setUpFacultyNameColumn();

        tableView.setItems(facultyObservableList);
    }

    private void setUpShortFacultyNameColumn() {
        shortFacultyNameColumn.setCellValueFactory(new PropertyValueFactory<>("shortFacultyName"));
        shortFacultyNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        shortFacultyNameColumn.setOnEditCommit(event -> {
            final String value = event.getNewValue() != null ?
                    event.getNewValue() : event.getOldValue();
            ((Faculty) event.getTableView().getItems()
                    .get(event.getTablePosition().getRow())).setShortFacultyName(value);
            tableView.refresh();
        });
    }

    private void setUpFacultyNameColumn() {
        facultyNameColumn.setCellValueFactory(new PropertyValueFactory<>("facultyName"));
        facultyNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        facultyNameColumn.setOnEditCommit(event -> {
            final String value = event.getNewValue() != null ?
                    event.getNewValue() : event.getOldValue();
            ((Faculty) event.getTableView().getItems()
                    .get(event.getTablePosition().getRow())).setFacultyName(value);
            tableView.refresh();
        });
    }

    @FXML
    private void add() {
        Faculty faculty = showModal("Добавление нового факультета", ControllerAction.ADD);
        if (faculty != null) {
            faculty = facultyService.save(faculty);
            facultyObservableList.add(faculty);
            tableView.refresh();
        }
    }

    @FXML
    private void update() {
        Faculty faculty = showModal("Изменение информации о факультете", ControllerAction.UPDATE);
        if (faculty != null) {
            faculty = facultyService.update(faculty);
            updateObjectInTable(faculty);
            tableView.refresh();
        }
    }

    private void updateObjectInTable(Faculty faculty) {
        if (faculty == null) {
            return;
        }
        for (int i = 0; i < facultyObservableList.size(); i++) {
            if (facultyObservableList.get(i).getId() == faculty.getId()) {
                facultyObservableList.set(i, faculty);
            }
        }
    }

    @FXML
    private void delete() {
        Optional<ButtonType> result = PaneUtil.showConfirmationModal("Подтверждение удаления"
                , "Вы уверены что хотите удалить данный элемент?", null);
        if (!(result.isPresent() && result.get().getButtonData() == ButtonBar.ButtonData.OK_DONE)) {
            return;
        }
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Faculty faculty = (Faculty) tableView.getSelectionModel().getSelectedItem();
            facultyService.delete(faculty);
            facultyObservableList.remove(selectedIndex);
            tableView.refresh();
        } else {
            showSelectErrorModal();
        }
    }

    @FXML
    private void view() {
        showModal("Просмотр информации о факультете", ControllerAction.VIEW);
    }

    private Faculty showModal(String title, ControllerAction controllerAction) {
        ControllerKeeper<FacultyInformationController> controllerKeeper = new ControllerKeeper<>();
        Stage modal = PaneUtil.createModal("/view/menu/item/faculty/FacultyInformation.fxml"
                , title, controllerKeeper);
        FacultyInformationController controller = controllerKeeper.getController();

        switch (controllerAction) {
            case ADD:
                controller.setFaculty(null);
                break;
            case VIEW:
                controller.disableElementsForView();
            case UPDATE:
                if (!setFacultyToController(controller)) {
                    return null;
                }
                break;
        }

        modal.showAndWait();
        Faculty faculty = controller.getFaculty();
        return controller.isOkClicked() ? faculty : null;
    }

    private boolean setFacultyToController(FacultyInformationController controller) {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            controller.setFaculty((Faculty) tableView.getSelectionModel().getSelectedItem());
        } else {
            showSelectErrorModal();
            return false;
        }
        return true;
    }

    private void showSelectErrorModal() {
        PaneUtil.showInformationModal("Ошибка при выборе"
                , "Не выбран факультет"
                , "Пожалуйста, выберите факультет в таблице.");
    }
}
