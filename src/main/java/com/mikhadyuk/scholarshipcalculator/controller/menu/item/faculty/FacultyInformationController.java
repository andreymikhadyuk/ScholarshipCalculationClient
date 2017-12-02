package com.mikhadyuk.scholarshipcalculator.controller.menu.item.faculty;

import com.mikhadyuk.scholarshipcalculator.model.Faculty;
import com.mikhadyuk.scholarshipcalculator.model.Speciality;
import com.mikhadyuk.scholarshipcalculator.model.enums.EducationalScholarshipType;
import com.mikhadyuk.scholarshipcalculator.service.SpecialityService;
import com.mikhadyuk.scholarshipcalculator.util.PaneUtil;
import com.mikhadyuk.scholarshipcalculator.util.SingletonUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class FacultyInformationController {
    private SpecialityService specialityService;

    private Faculty faculty;
    private boolean okClicked = false;

    private ObservableList<Speciality> specialities = FXCollections.observableArrayList();

    private ObservableList<EducationalScholarshipType> educationalTypes = FXCollections.observableArrayList();

    @FXML
    private TextField facultyNameTextField;
    @FXML
    private TextField shortFacultyNameTextField;

    @FXML
    private HBox specialityButtons;

    @FXML
    private TableView specialityTable;
    @FXML
    private TableColumn<Speciality, String> specialityColumn;
    @FXML
    private TableColumn<Speciality, String> shortSpecialityColumn;
    @FXML
    private TableColumn<Speciality, EducationalScholarshipType> educationalTypeColumn;

    @FXML
    private void initialize() {
        specialityService = SingletonUtil.getInstance(SpecialityService.class);

        setUpSpecialityNameColumn();
        setUpShortSpecialityNameColumn();
        setUpEducationalTypeColumn();

        specialityTable.setItems(specialities);
    }

    private void setUpSpecialityNameColumn() {
        specialityColumn.setCellValueFactory(new PropertyValueFactory<>("specialityName"));
        specialityColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        specialityColumn.setOnEditCommit(event -> {
            final String value = event.getNewValue() != null ?
                    event.getNewValue() : event.getOldValue();
            ((Speciality) event.getTableView().getItems()
                    .get(event.getTablePosition().getRow())).setSpecialityName(value);
            specialityTable.refresh();
        });
    }

    private void setUpShortSpecialityNameColumn() {
        shortSpecialityColumn.setCellValueFactory(new PropertyValueFactory<>("shortSpecialityName"));
        shortSpecialityColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        shortSpecialityColumn.setOnEditCommit(event -> {
            final String value = event.getNewValue() != null ?
                    event.getNewValue() : event.getOldValue();
            ((Speciality) event.getTableView().getItems()
                    .get(event.getTablePosition().getRow())).setShortSpecialityName(value);
            specialityTable.refresh();
        });
    }

    private void setUpEducationalTypeColumn() {
        educationalTypes.addAll(EducationalScholarshipType.values());
        educationalTypeColumn.setCellValueFactory(new PropertyValueFactory<>("educationalScholarshipType"));
        educationalTypeColumn.setCellFactory(ComboBoxTableCell.forTableColumn(educationalTypes));
        educationalTypeColumn.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Speciality, EducationalScholarshipType>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Speciality, EducationalScholarshipType> t) {
                        ((Speciality) t.getTableView().getItems().get(t.getTablePosition().getRow())).setEducationalScholarshipType(t.getNewValue());
                    };
                }
        );
    }

    @FXML
    private void onOkButtonClick(ActionEvent event) {
        faculty.setFacultyName(facultyNameTextField.getText());
        faculty.setShortFacultyName(shortFacultyNameTextField.getText());
        faculty.setSpecialities(new ArrayList<>(specialities));
        okClicked = true;
        cancel(event);
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
        if (this.faculty == null) {
            this.faculty = new Faculty();
            this.faculty.setSpecialities(new ArrayList<>());
        }
        facultyNameTextField.setText(this.faculty.getFacultyName());
        shortFacultyNameTextField.setText(this.faculty.getShortFacultyName());
        specialities.addAll(this.faculty.getSpecialities());
    }

    @FXML
    void add(ActionEvent event) {
        Speciality speciality = new Speciality();
        specialities.add(speciality);
    }

    @FXML
    void delete(ActionEvent event) {
        int selectedIndex = specialityTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Speciality speciality =
                    (Speciality) specialityTable.getSelectionModel().getSelectedItem();
            specialityService.delete(speciality);
            specialities.remove(selectedIndex);
            specialityTable.refresh();
        } else {
            PaneUtil.showInformationModal("Ошибка при выборе"
                    , "Не выбрана специальность"
                    , "Пожалуйста, выберите специальность.");
        }
    }

    @FXML
    void cancel(ActionEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.hide();
        stage.close();
    }

    public void disableElementsForView() {
        facultyNameTextField.setEditable(false);
        shortFacultyNameTextField.setEditable(false);

        specialityButtons.setVisible(false);
        specialityTable.setEditable(false);
    }
}
