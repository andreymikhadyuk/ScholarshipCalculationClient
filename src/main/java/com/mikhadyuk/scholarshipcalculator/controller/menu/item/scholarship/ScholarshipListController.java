package com.mikhadyuk.scholarshipcalculator.controller.menu.item.scholarship;

import com.mikhadyuk.scholarshipcalculator.controller.MainController;
import com.mikhadyuk.scholarshipcalculator.controller.enums.ControllerAction;
import com.mikhadyuk.scholarshipcalculator.converter.CellBooleanStringConverter;
import com.mikhadyuk.scholarshipcalculator.keeper.ControllerKeeper;
import com.mikhadyuk.scholarshipcalculator.model.Scholarship;
import com.mikhadyuk.scholarshipcalculator.service.ScholarshipService;
import com.mikhadyuk.scholarshipcalculator.util.PaneUtil;
import com.mikhadyuk.scholarshipcalculator.util.SingletonUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;

import java.util.ArrayList;


public class ScholarshipListController {
    private ScholarshipService scholarshipService;

    private ObservableList<Scholarship> scholarshipObservableList = FXCollections.observableArrayList();

    @FXML
    private TableView tableView;
    @FXML
    private TableColumn<Scholarship, String> scholarshipNameColumn;
    @FXML
    private TableColumn<Scholarship, String> educationalColumn;

    @FXML
    private void initialize() {
        MainController.setGoBackPane(PaneUtil.load("/view/menu/Menu.fxml"));

        scholarshipService = SingletonUtil.getInstance(ScholarshipService.class);
        scholarshipObservableList.addAll(scholarshipService.getAllScholarships());

        setUpScholarshipNameColumn();
        setUpEducationalColumn();

        tableView.setItems(scholarshipObservableList);
    }

    private void setUpScholarshipNameColumn() {
        scholarshipNameColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
    }

    private void setUpEducationalColumn() {
        educationalColumn.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().isEducational() ? "Да" : "Нет"
        ));
    }

    @FXML
    private void add() {
        Scholarship scholarship = showModal("Добавление новой стипендии", ControllerAction.ADD);
        if (scholarship != null) {
            scholarship = scholarshipService.save(scholarship);
            scholarshipObservableList.add(scholarship);
            tableView.refresh();
        }
    }

    @FXML
    private void update() {
        Scholarship scholarship = showModal("Изменение информации о стипендии", ControllerAction.UPDATE);
        if (scholarship != null) {
            scholarship = scholarshipService.update(scholarship);
            updateObjectInTable(scholarship);
            tableView.refresh();
        }
    }

    private void updateObjectInTable(Scholarship scholarship) {
        if (scholarship == null) {
            return;
        }
        for (int i = 0; i < scholarshipObservableList.size(); i++) {
             if (scholarshipObservableList.get(i).getId() == scholarship.getId()) {
                 scholarshipObservableList.set(i, scholarship);
             }
        }
    }

    @FXML
    private void delete() {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Scholarship scholarship = (Scholarship) tableView.getSelectionModel().getSelectedItem();
            scholarshipService.delete(scholarship);
            scholarshipObservableList.remove(selectedIndex);
            tableView.refresh();
        } else {
            showSelectErrorModal();
        }
    }

    @FXML
    private void view() {
        showModal("Просмотр информации о стипендии", ControllerAction.VIEW);
    }

    private Scholarship showModal(String title, ControllerAction controllerAction) {
        ControllerKeeper<ScholarshipInformationController> controllerKeeper = new ControllerKeeper<>();
        Stage modal = PaneUtil.createModal("/view/menu/item/scholarship/ScholarshipInformation.fxml"
                , title, controllerKeeper);
        ScholarshipInformationController controller = controllerKeeper.getController();

        switch (controllerAction) {
            case ADD:
                controller.setScholarship(null);
                break;
            case VIEW:
                controller.disableElementsForView();
            case UPDATE:
            case DELETE:
                if (!setScholarshipToController(controller)) {
                    return null;
                }
                break;
        }

        modal.showAndWait();
        Scholarship scholarship = controller.getScholarship();
        scholarship.setScholarshipProperties(new ArrayList<>(scholarship.getScholarshipProperties()));
        return controller.isOkClicked() ? scholarship : null;
    }

    private boolean setScholarshipToController(ScholarshipInformationController controller) {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            controller.setScholarship((Scholarship) tableView.getSelectionModel().getSelectedItem());
        } else {
            showSelectErrorModal();
            return false;
        }
        return true;
    }

    private void showSelectErrorModal() {
        PaneUtil.showConfirmModal("Ошибка при выборе"
                , "Не выбрана стипендия"
                , "Пожалуйста, выберите стипендию в таблице.");
    }
}
