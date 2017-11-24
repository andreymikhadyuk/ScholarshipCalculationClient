package com.mikhadyuk.scholarshipcalculator.controller.menu.item.scholarship;

import com.jfoenix.controls.JFXRadioButton;
import com.mikhadyuk.scholarshipcalculator.converter.CellDoubleStringConverter;
import com.mikhadyuk.scholarshipcalculator.converter.CellIntegerStringConverter;
import com.mikhadyuk.scholarshipcalculator.model.Scholarship;
import com.mikhadyuk.scholarshipcalculator.model.ScholarshipProperty;
import com.mikhadyuk.scholarshipcalculator.model.enums.EducationalScholarshipType;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScholarshipInformationController {
    private Scholarship scholarship;
    private boolean okClicked = false;

    private ScholarshipProperty scholarshipProperty;
    private ObservableList<ScholarshipProperty> scholarshipProperties = FXCollections.observableArrayList();

    private ObservableList<EducationalScholarshipType> educationalTypes = FXCollections.observableArrayList();

    @FXML
    private TextField scholarshipName;
    @FXML
    private JFXRadioButton yesRadioButton;
    @FXML
    private JFXRadioButton noRadioButton;

    @FXML
    private GridPane amountGridPane;
    @FXML
    private TextField amountTextField;

    @FXML
    private AnchorPane tablePane;
    @FXML
    private TableView propertyTable;
    @FXML
    private TableColumn<ScholarshipProperty, EducationalScholarshipType> educationalTypeColumn;
    @FXML
    private TableColumn<ScholarshipProperty, Integer> minAverageScoreColumn;
    @FXML
    private TableColumn<ScholarshipProperty, Integer> maxAverageScoreColumn;
    @FXML
    private TableColumn<ScholarshipProperty, Double> increaseCoefficientColumn;

    @FXML
    private void initialize() {
        yesRadioButton.setOnAction(e -> {
            changeVisible(noRadioButton.isSelected());
        });
        noRadioButton.setOnAction(e -> {
            changeVisible(noRadioButton.isSelected());
        });

        setUpEducationalTypeColumn();
        setUpMinAverageScoreColumn();
        setUpMaxAverageScoreColumn();
        setUpIncreaseCoefficientColumn();

        propertyTable.setItems(scholarshipProperties);
    }

    private void setUpEducationalTypeColumn() {
        educationalTypes.addAll(EducationalScholarshipType.values());
        educationalTypeColumn.setCellValueFactory(new PropertyValueFactory<>("educationalType"));
        educationalTypeColumn.setCellFactory(ComboBoxTableCell.forTableColumn(educationalTypes));
        educationalTypeColumn.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<ScholarshipProperty, EducationalScholarshipType>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<ScholarshipProperty, EducationalScholarshipType> t) {
                        ((ScholarshipProperty) t.getTableView().getItems().get(t.getTablePosition().getRow())).setEducationalType(t.getNewValue());
                    };
                }
        );
    }

    private void setUpMinAverageScoreColumn() {
        minAverageScoreColumn.setCellValueFactory(new PropertyValueFactory<>("minAverageScore"));
        minAverageScoreColumn.setCellFactory(TextFieldTableCell.forTableColumn(new CellIntegerStringConverter()));
        minAverageScoreColumn.setOnEditCommit(event -> {
            final Integer value = event.getNewValue() != null ?
                    event.getNewValue() : event.getOldValue();
            ((ScholarshipProperty) event.getTableView().getItems()
                    .get(event.getTablePosition().getRow())).setMinAverageScore(value);
            propertyTable.refresh();
        });
    }

    private void setUpMaxAverageScoreColumn() {
        maxAverageScoreColumn.setCellValueFactory(new PropertyValueFactory<>("maxAverageScore"));
        maxAverageScoreColumn.setCellFactory(TextFieldTableCell.forTableColumn(new CellIntegerStringConverter()));
        maxAverageScoreColumn.setOnEditCommit(event -> {
            final Integer value = event.getNewValue() != null ?
                    event.getNewValue() : event.getOldValue();
            ((ScholarshipProperty) event.getTableView().getItems()
                    .get(event.getTablePosition().getRow())).setMaxAverageScore(value);
            propertyTable.refresh();
        });
    }

    private void setUpIncreaseCoefficientColumn() {
        increaseCoefficientColumn.setCellValueFactory(new PropertyValueFactory<>("increaseCoefficient"));
        increaseCoefficientColumn.setCellFactory(TextFieldTableCell.forTableColumn(new CellDoubleStringConverter()));
        increaseCoefficientColumn.setOnEditCommit(event -> {
            final Double value = event.getNewValue() != null ?
                    event.getNewValue() : event.getOldValue();
            ((ScholarshipProperty) event.getTableView().getItems()
                    .get(event.getTablePosition().getRow())).setIncreaseCoefficient(value);
            propertyTable.refresh();
        });
    }

    @FXML
    private void onOkButtonClick(ActionEvent event) {
        scholarship.setType(scholarshipName.getText());
        scholarship.setEducational(yesRadioButton.isSelected());
        if (scholarship.isEducational()) {
            setEducationalData();
        } else {
            setNonEducationalData();
        }
        okClicked = true;
        cancel(event);
    }

    private void setEducationalData() {
        scholarship.getScholarshipProperties().clear();
        scholarship.setScholarshipProperties(scholarshipProperties);
    }

    private void setNonEducationalData() {
        scholarshipProperty.setAmount(Double.parseDouble(amountTextField.getText()));
        scholarship.setScholarshipProperties(Arrays.asList(scholarshipProperty));
    }

    private void changeVisible(boolean amountVisible) {
        amountGridPane.setVisible(amountVisible);
        tablePane.setVisible(!amountVisible);
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public Scholarship getScholarship() {
        return scholarship;
    }

    public void setScholarship(Scholarship scholarship) {
        this.scholarship = scholarship;
        if (this.scholarship == null) {
            this.scholarship = new Scholarship();
            List<ScholarshipProperty> list = new ArrayList<>();
            list.add(new ScholarshipProperty());
            this.scholarship.setScholarshipProperties(list);
        }
        scholarshipName.setText(scholarship.getType());
        if (this.scholarship.isEducational()) {
            scholarshipProperties.addAll(this.scholarship.getScholarshipProperties());
            scholarshipProperty = new ScholarshipProperty();
        } else {
            scholarshipProperty = this.scholarship.getScholarshipProperties().get(0);
        }
    }

    @FXML
    void add(ActionEvent event) {
        ScholarshipProperty sp = new ScholarshipProperty();
        scholarshipProperties.add(sp);
    }


    @FXML
    void cancel(ActionEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.hide();
        stage.close();
    }
}
