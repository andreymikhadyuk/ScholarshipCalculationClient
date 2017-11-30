package com.mikhadyuk.scholarshipcalculator.controller.menu.item.student;

import com.mikhadyuk.scholarshipcalculator.controller.MainController;
import com.mikhadyuk.scholarshipcalculator.controller.enums.ControllerAction;
import com.mikhadyuk.scholarshipcalculator.keeper.ControllerKeeper;
import com.mikhadyuk.scholarshipcalculator.model.Student;
import com.mikhadyuk.scholarshipcalculator.service.StudentService;
import com.mikhadyuk.scholarshipcalculator.util.PaneUtil;
import com.mikhadyuk.scholarshipcalculator.util.SingletonUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class StudentListController {
    private StudentService studentService;

    ObservableList<String> filterItems = FXCollections.observableArrayList();

    private ObservableList<Student> studentObservableList = FXCollections.observableArrayList();

    @FXML
    private ComboBox filterBox;
    @FXML
    private TextField filterField;

    @FXML
    private TableView tableView;
    @FXML
    private TableColumn<Student, String> nameColumn;
    @FXML
    private TableColumn<Student, String> facultyColumn;
    @FXML
    private TableColumn<Student, String> specialityColumn;
    @FXML
    private TableColumn<Student, Integer> groupNumberColumn;
    @FXML
    private TableColumn<Student, String> averageScoreColumn;
    @FXML
    private TableColumn<Student, String> scholarshipAmountColumn;

    @FXML
    private void initialize() {
        MainController.setGoBackPane(PaneUtil.load("/view/menu/Menu.fxml"));

        studentService = SingletonUtil.getInstance(StudentService.class);
        studentObservableList.addAll(studentService.getAllStudents());

        setUpNameColumn();
        setUpFacultyColumn();
        setUpSpecialityColumn();
        setUpGroupNumberColumn();
        setUpAverageScoreColumn();
        setUpScholarshipAmountColumn();

        initFilterBox();
        filter();
    }

    private void setUpNameColumn() {
        nameColumn.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().getLastName() + " "
                        + c.getValue().getFirstName() + " " + c.getValue().getPatronymic()
        ));
    }

    private void setUpFacultyColumn() {
        facultyColumn.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().getSpeciality().getFaculty().getShortFacultyName()
        ));
    }

    private void setUpSpecialityColumn() {
        specialityColumn.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().getSpeciality().getShortSpecialityName()
        ));
    }

    private void setUpGroupNumberColumn() {
        groupNumberColumn.setCellValueFactory(new PropertyValueFactory<>("groupNumber"));
    }

    private void setUpAverageScoreColumn() {
        averageScoreColumn.setCellValueFactory(c -> new SimpleStringProperty(
                String.format("%.2f", c.getValue().getAverageScore())
        ));
    }

    private void setUpScholarshipAmountColumn() {
        scholarshipAmountColumn.setCellValueFactory(c -> new SimpleStringProperty(
                String.format("%.2f", c.getValue().getScholarshipAmount())
        ));
    }

    private void initFilterBox() {
        filterItems.clear();

        filterItems.add("Поиск по ФИО");
        filterItems.add("Поиск по факультету");
        filterItems.add("Поиск по специальности");
        filterItems.add("Поиск по № группы");

        filterBox.setItems(filterItems);
        filterBox.getSelectionModel().select(0);

        filterBox.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            filterField.setPromptText((String) newValue);
            filterField.setText("");
        });
    }

    private void filter() {
        FilteredList<Student> filteredData = new FilteredList<>(studentObservableList, p -> true);

        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(student -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (filterBox.getSelectionModel().isSelected(0)) {
                    if (student.getLastName().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches last name.
                    } else if (student.getFirstName().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches first name.
                    } else if (student.getPatronymic().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches patronymic.
                    }
                } else if (filterBox.getSelectionModel().isSelected(1)) {
                    if (student.getSpeciality().getFaculty().getFacultyName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (student.getSpeciality().getFaculty().getShortFacultyName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                } else if (filterBox.getSelectionModel().isSelected(2)) {
                    if (student.getSpeciality().getSpecialityName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (student.getSpeciality().getShortSpecialityName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                } else if (filterBox.getSelectionModel().isSelected(3)) {
                    if (String.valueOf(student.getGroupNumber()).toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                }

                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Student> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        tableView.setItems(sortedData);
    }

    @FXML
    private void add() {
        Student student = showModal("Добавление нового студента", ControllerAction.ADD);
        if (student != null) {
            student = studentService.save(student);
            studentObservableList.add(student);
            tableView.refresh();
        }
    }

    @FXML
    private void update() {
        Student student = showModal("Изменение информации о студенте", ControllerAction.UPDATE);
        if (student != null) {
            student = studentService.update(student);
            updateObjectInTable(student);
            tableView.refresh();
        }
    }

    private void updateObjectInTable(Student student) {
        if (student == null) {
            return;
        }
        for (int i = 0; i < studentObservableList.size(); i++) {
            if (studentObservableList.get(i).getId() == student.getId()) {
                studentObservableList.set(i, student);
            }
        }
    }

    @FXML
    private void delete() {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            Student student = (Student) tableView.getSelectionModel().getSelectedItem();
            studentService.delete(student);
            studentObservableList.remove(selectedIndex);
            tableView.refresh();
        } else {
            showSelectErrorModal();
        }
    }

    @FXML
    private void view() {
        showModal("Просмотр информации о студенте", ControllerAction.VIEW);
    }

    private Student showModal(String title, ControllerAction controllerAction) {
        ControllerKeeper<StudentInformationController> controllerKeeper = new ControllerKeeper<>();
        Stage modal = PaneUtil.createModal("/view/menu/item/student/StudentInformation.fxml"
                , title, controllerKeeper);
        StudentInformationController controller = controllerKeeper.getController();

        switch (controllerAction) {
            case ADD:
                controller.setStudent(null);
                break;
            case VIEW:
                controller.disableElementsForView();
            case UPDATE:
                if (!setStudentToController(controller)) {
                    return null;
                }
                break;
        }

        modal.showAndWait();
        Student student = controller.getStudent();
        return controller.isOkClicked() ? student : null;
    }

    private boolean setStudentToController(StudentInformationController controller) {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            controller.setStudent((Student) tableView.getSelectionModel().getSelectedItem());
        } else {
            showSelectErrorModal();
            return false;
        }
        return true;
    }

    private void showSelectErrorModal() {
        PaneUtil.showConfirmModal("Ошибка при выборе"
                , "Не выбран студент"
                , "Пожалуйста, выберите студента в таблице.");
    }
}
