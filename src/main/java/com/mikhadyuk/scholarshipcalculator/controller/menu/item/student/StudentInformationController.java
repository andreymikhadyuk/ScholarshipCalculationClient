package com.mikhadyuk.scholarshipcalculator.controller.menu.item.student;

import com.jfoenix.controls.JFXCheckBox;
import com.mikhadyuk.scholarshipcalculator.converter.CellIntegerStringConverter;
import com.mikhadyuk.scholarshipcalculator.model.*;
import com.mikhadyuk.scholarshipcalculator.service.FacultyService;
import com.mikhadyuk.scholarshipcalculator.service.MarkService;
import com.mikhadyuk.scholarshipcalculator.service.ScholarshipService;
import com.mikhadyuk.scholarshipcalculator.service.StudentService;
import com.mikhadyuk.scholarshipcalculator.util.MessageUtil;
import com.mikhadyuk.scholarshipcalculator.util.PaneUtil;
import com.mikhadyuk.scholarshipcalculator.util.RegularExpUtil;
import com.mikhadyuk.scholarshipcalculator.util.SingletonUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StudentInformationController {
    private StudentService studentService;
    private ScholarshipService scholarshipService;
    private FacultyService facultyService;
    private MarkService markService;

    private Student student;
    private boolean okClicked = false;

    private ObservableList<Faculty> comboBoxFaculties = FXCollections.observableArrayList();
    private ObservableList<Speciality> comboBoxSpecialities = FXCollections.observableArrayList();
    private List<Scholarship> allScholarships = new ArrayList<>();
    private ObservableList<String> comboBoxScholarships = FXCollections.observableArrayList();

    private ObservableList<Mark> markObservableList = FXCollections.observableArrayList();
    private ObservableList<Scholarship> scholarshipObservableList = FXCollections.observableArrayList();

    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField patronymicTextField;
    @FXML
    private TextField groupNumberTextField;
    @FXML
    private ComboBox facultyComboBox;
    @FXML
    private ComboBox specialityComboBox;
    @FXML
    private JFXCheckBox isHandicappedCheckBox;

    @FXML
    private HBox markButtons;
    @FXML
    private HBox scholarshipButtons;

    @FXML
    private TableView markTable;
    @FXML
    private TableColumn<Mark, String> subjectNameColumn;
    @FXML
    private TableColumn<Mark, Integer> markColumn;

    @FXML
    private TableView scholarshipTable;
    @FXML
    private TableColumn<Scholarship, String> scholarshipColumn;

    @FXML
    private Label errorLabel;

    @FXML
    private void initialize() {
        studentService = SingletonUtil.getInstance(StudentService.class);
        scholarshipService = SingletonUtil.getInstance(ScholarshipService.class);
        facultyService = SingletonUtil.getInstance(FacultyService.class);

        initFacultyComboBox();
        initSpecialityComboBox();

        setUpSubjectNameColumn();
        setUpMarkColumn();
        setUpScholarshipNameColumn();

        markTable.setItems(markObservableList);
        scholarshipTable.setItems(scholarshipObservableList);
    }

    private void initSpecialityComboBox() {
        Callback cellFactory = new Callback<ListView<Speciality>, ListCell<Speciality>>() {
            @Override
            public ListCell<Speciality> call(ListView<Speciality> param) {
                return new ListCell<Speciality>() {
                    @Override
                    protected void updateItem(Speciality item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setGraphic(null);
                        } else {
                            setText(item.getShortSpecialityName());
                        }
                    }
                };
            }
        };

        specialityComboBox.setButtonCell((ListCell) cellFactory.call(null));
        specialityComboBox.setCellFactory(cellFactory);
        specialityComboBox.setItems(comboBoxSpecialities);
    }

    private void initFacultyComboBox() {
        comboBoxFaculties.addAll(facultyService.getAllFaculties());

        Callback cellFactory = new Callback<ListView<Faculty>, ListCell<Faculty>>() {
            @Override
            public ListCell<Faculty> call(ListView<Faculty> param) {
                return new ListCell<Faculty>() {
                    @Override
                    protected void updateItem(Faculty item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setGraphic(null);
                        } else {
                            setText(item.getShortFacultyName());
                            initStudentFacultyAndSpeciality(item);
                        }
                    }
                };
            }
        };

        facultyComboBox.setButtonCell((ListCell) cellFactory.call(null));
        facultyComboBox.setCellFactory(cellFactory);
        facultyComboBox.setItems(comboBoxFaculties);
        facultyComboBox.getSelectionModel().select(0);
    }

    private void initComboBoxSpecialities(Faculty faculty, int index) {
        comboBoxSpecialities.clear();
//        comboBoxSpecialities.add(faculty.getSpecialities().get(index));
//        for (int i = 0; i < faculty.getSpecialities().size(); i++) {
//            if (i != index) {
//                comboBoxSpecialities.add(faculty.getSpecialities().get(i));
//            }
//        }
        comboBoxSpecialities.addAll(faculty.getSpecialities());
        specialityComboBox.getSelectionModel().select(index);
    }

    private void setUpSubjectNameColumn() {
        subjectNameColumn.setCellValueFactory(new PropertyValueFactory<>("subjectName"));
        subjectNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        subjectNameColumn.setOnEditCommit(event -> {
            final String value = event.getNewValue() != null ?
                    event.getNewValue() : event.getOldValue();
            ((Mark) event.getTableView().getItems()
                    .get(event.getTablePosition().getRow())).setSubjectName(value);
            markTable.refresh();
        });
    }

    private void setUpMarkColumn() {
        markColumn.setCellValueFactory(new PropertyValueFactory<>("mark"));
        markColumn.setCellFactory(TextFieldTableCell.forTableColumn(new CellIntegerStringConverter()));
        markColumn.setOnEditCommit(event -> {
            final Integer value = event.getNewValue() != null ?
                    event.getNewValue() : event.getOldValue();
            ((Mark) event.getTableView().getItems()
                    .get(event.getTablePosition().getRow())).setMark(value);
            markTable.refresh();
        });
    }

    private void setUpScholarshipNameColumn() {
        allScholarships.addAll(scholarshipService.getAllScholarships());
        comboBoxScholarships.addAll(allScholarships
                .stream()
                .map(Scholarship::getType)
                .collect(Collectors.toList()));
        scholarshipColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        scholarshipColumn.setCellFactory(ComboBoxTableCell.forTableColumn(comboBoxScholarships));
        scholarshipColumn.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Scholarship, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Scholarship, String> t) {
                        int index = -1;
                        for (int i = 0; i < scholarshipObservableList.size(); i++) {
                            if (scholarshipObservableList.get(i).getType().equals(t.getNewValue())) {
                                index = i;
                                break;
                            }
                        }
                        if (index == t.getTablePosition().getRow()) {
                            return;
                        }

                        if (index != -1) {
                            scholarshipObservableList.remove(t.getTablePosition().getRow());
                        } else {
                            scholarshipObservableList.set(t.getTablePosition().getRow()
                                    , scholarshipService.getByType(allScholarships, t.getNewValue()));
                        }
                        scholarshipTable.refresh();
                    }
                }
        );
    }

    @FXML
    private void onOkButtonClick(ActionEvent event) {
        if (!validInput()) {
            return;
        }

        student.setLastName(lastNameTextField.getText());
        student.setFirstName(firstNameTextField.getText());
        student.setPatronymic(patronymicTextField.getText());
        student.setGroupNumber(Integer.parseInt(groupNumberTextField.getText()));
        student.setSpeciality((Speciality) specialityComboBox.getSelectionModel().getSelectedItem());
        student.setMarks(new ArrayList<>(markObservableList));
        student.setScholarships(new ArrayList<>(scholarshipObservableList));
        student.setHandicapped(isHandicappedCheckBox.isSelected());
        okClicked = true;
        cancel(event);
    }

    private boolean validInput() {
        errorLabel.setVisible(false);
        if (!(RegularExpUtil.isCorrectString(lastNameTextField.getText(), RegularExpUtil.NAME_REG_EXP)) ||
                !(RegularExpUtil.isCorrectString(firstNameTextField.getText(), RegularExpUtil.NAME_REG_EXP)) ||
                !(RegularExpUtil.isCorrectString(patronymicTextField.getText(), RegularExpUtil.NAME_REG_EXP))) {
            errorLabel.setText(MessageUtil.getMessage("registration.error.wrongName"));
            errorLabel.setVisible(true);
            return false;
        }
        if (!(RegularExpUtil.isCorrectString(groupNumberTextField.getText(), RegularExpUtil.GROUP_NUMBER_REG_EXP))) {
            errorLabel.setText(MessageUtil.getMessage("student.error.wrongGroupNumber"));
            errorLabel.setVisible(true);
            return false;
        }
        return true;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
        if (this.student == null) {
            this.student = createEmptyStudent();
        } else {
            lastNameTextField.setText(this.student.getLastName());
            firstNameTextField.setText(this.student.getFirstName());
            patronymicTextField.setText(this.student.getPatronymic());
            groupNumberTextField.setText(String.valueOf(this.student.getGroupNumber()));
            isHandicappedCheckBox.setSelected(this.student.isHandicapped());
            initTables();
            initStudentFacultyAndSpeciality(this.student.getSpeciality().getFaculty());
        }
    }

    private void initTables() {
        markObservableList.addAll(this.student.getMarks());
        markTable.refresh();
        scholarshipObservableList.addAll(this.student.getScholarships());
        scholarshipTable.refresh();
    }

    private void initStudentFacultyAndSpeciality(Faculty faculty) {
        Speciality speciality;
        if (faculty.getId() == this.student.getSpeciality().getFaculty().getId()) {
            speciality = this.student.getSpeciality();
        } else {
            speciality = new Speciality();
            speciality.setId(-1);
        }
        for (int i = 0; i < comboBoxFaculties.size(); i++) {
            if (comboBoxFaculties.get(i).getId() == faculty.getId()) {
                facultyComboBox.getSelectionModel().select(i);
                boolean found = false;
                for (int j = 0; j < faculty.getSpecialities().size(); j++) {
                    if (faculty.getSpecialities().get(j).getId() == speciality.getId()) {
                        initComboBoxSpecialities(faculty, j);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    initComboBoxSpecialities(faculty, 0);
                }
                break;
            }
        }
    }

    private Student createEmptyStudent() {
        Student student = new Student();
        student.setMarks(new ArrayList<>());
        student.setScholarships(new ArrayList<>());
        return student;
    }

    @FXML
    void addMark(ActionEvent event) {
        Mark mark = new Mark();
        markObservableList.add(mark);
    }

    @FXML
    void addScholarship(ActionEvent event) {
        Scholarship scholarship = new Scholarship();
        scholarship.setType("");
        scholarshipObservableList.add(scholarship);
    }

    @FXML
    void deleteMark(ActionEvent event) {
        int selectedIndex = markTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Mark mark = (Mark) markTable.getSelectionModel().getSelectedItem();
            markService.delete(mark);
            markObservableList.remove(selectedIndex);
            markTable.refresh();
        } else {
            PaneUtil.showInformationModal("Ошибка при выборе"
                    , "Не выбран предмет"
                    , "Пожалуйста, выберите предмет.");
        }
    }

    @FXML
    void deleteScholarship(ActionEvent event) {
        int selectedIndex = scholarshipTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            scholarshipObservableList.remove(selectedIndex);
            scholarshipTable.refresh();
        } else {
            PaneUtil.showInformationModal("Ошибка при выборе"
                    , "Не выбрана стипендия"
                    , "Пожалуйста, выберите стипендию.");
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
        lastNameTextField.setEditable(false);
        firstNameTextField.setEditable(false);
        patronymicTextField.setEditable(false);
        groupNumberTextField.setEditable(false);

        facultyComboBox.setDisable(true);
        specialityComboBox.setDisable(true);

        markButtons.setVisible(false);
        scholarshipButtons.setVisible(false);

        markTable.setEditable(false);
        scholarshipTable.setEditable(false);
    }
}
