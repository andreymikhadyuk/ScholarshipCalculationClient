package com.mikhadyuk.scholarshipcalculator.controller.menu.item.scholarship;

import com.mikhadyuk.scholarshipcalculator.controller.MainController;
import com.mikhadyuk.scholarshipcalculator.controller.enums.ControllerAction;
import com.mikhadyuk.scholarshipcalculator.converter.CellBooleanStringConverter;
import com.mikhadyuk.scholarshipcalculator.keeper.ControllerKeeper;
import com.mikhadyuk.scholarshipcalculator.model.BaseAmount;
import com.mikhadyuk.scholarshipcalculator.model.Scholarship;
import com.mikhadyuk.scholarshipcalculator.service.BaseAmountService;
import com.mikhadyuk.scholarshipcalculator.service.ReportService;
import com.mikhadyuk.scholarshipcalculator.service.ScholarshipService;
import com.mikhadyuk.scholarshipcalculator.util.PaneUtil;
import com.mikhadyuk.scholarshipcalculator.util.SingletonUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ScholarshipListController {
    private ScholarshipService scholarshipService;
    private ReportService reportService;
    private BaseAmountService baseAmountService;

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
        reportService = SingletonUtil.getInstance(ReportService.class);
        baseAmountService = SingletonUtil.getInstance(BaseAmountService.class);
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

    @FXML
    private void saveReport() {
        String report = reportService.createReport();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранение отчета");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File selectedFile = fileChooser.showSaveDialog(MainController.getMainStage());
        if (selectedFile != null) {
            try (PrintWriter fw = new PrintWriter(selectedFile)) {
                fw.print(report);
                fw.flush();
                fw.close();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Создание отчета");
                alert.setHeaderText("Отчет успешно создан");
                alert.setContentText(null);

                alert.showAndWait();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void makeGraphic() {
        List<PieChart.Data> dataList = new ArrayList<>();

        List<BaseAmount> baseAmounts = baseAmountService.getAllBaseAmounts();
        for (BaseAmount baseAmount : baseAmounts) {
            if (baseAmount.getAmount() != 0) {
                dataList.add(new PieChart.Data(
                        String.format("%s\r\nспециальность", baseAmount.getEducationalType().getLabel()),
                        baseAmount.getAmount()));
            }
        }
        for (Scholarship scholarship : scholarshipObservableList) {
            if (!scholarship.isEducational()) {
                dataList.add(new PieChart.Data(
                        String.format(scholarship.getType()),
                                scholarship.getScholarshipProperties().get(0).getAmount()));
            }
        }

        //Preparing ObservbleList object
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(dataList);

        //Creating a Pie chart
        PieChart pieChart = new PieChart(pieChartData) ;

        //Setting the title of the Pie chart
        pieChart.setTitle("Базовые размеры студенческих стипендий") ;

        //setting the direction to arrange the data
        pieChart.setClockwise(true) ;

        //Setting the length of the label line
        pieChart.setLabelLineLength(50) ;

        //Setting the labels of the pie chart visible
        pieChart.setLabelsVisible(true) ;

        //Setting the start angle of the pie chart
        pieChart.setStartAngle(180) ;

        //Creating a Group object
        Group root = new Group(pieChart) ;

        //Creating a scene object

        Stage stage = new Stage();
        stage.setTitle("График оценок студентов") ;
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(MainController.getMainStage());
        stage.setResizable(false);

//        Scene scene = new Scene(root, 600, 400) ;
        Scene scene = new Scene(root) ;
        stage.setScene(scene);

        stage.showAndWait();
    }
}
