package com.mikhadyuk.scholarshipcalculator.controller.menu.item.scholarship;

import com.jfoenix.controls.JFXRadioButton;
import com.mikhadyuk.scholarshipcalculator.converter.CellDoubleStringConverter;
import com.mikhadyuk.scholarshipcalculator.converter.CellIntegerStringConverter;
import com.mikhadyuk.scholarshipcalculator.model.BaseAmount;
import com.mikhadyuk.scholarshipcalculator.model.Scholarship;
import com.mikhadyuk.scholarshipcalculator.model.ScholarshipProperty;
import com.mikhadyuk.scholarshipcalculator.model.enums.EducationalScholarshipType;
import com.mikhadyuk.scholarshipcalculator.service.ScholarshipPropertyService;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScholarshipInformationController {
    private ScholarshipPropertyService scholarshipPropertyService;

    private Scholarship scholarship;
    private boolean okClicked = false;

    private ObservableList<BaseAmount> baseAmounts = FXCollections.observableArrayList();

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
    private TableView baseTable;
    @FXML
    private TableColumn<BaseAmount, EducationalScholarshipType> baseScholarshipTypeColumn;
    @FXML
    private TableColumn<BaseAmount, Double> baseScholarshipAmountColumn;

    @FXML
    private HBox propertyButtons;

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
        scholarshipPropertyService = SingletonUtil.getInstance(ScholarshipPropertyService.class);

        yesRadioButton.setOnAction(e -> {
            changeVisible(noRadioButton.isSelected());
        });
        noRadioButton.setOnAction(e -> {
            changeVisible(noRadioButton.isSelected());
        });

        setUpBaseScholarshipTypeColumn();
        setUpBaseScholarshipAmountColumn();

        setUpEducationalTypeColumn();
        setUpMinAverageScoreColumn();
        setUpMaxAverageScoreColumn();
        setUpIncreaseCoefficientColumn();

        propertyTable.setItems(scholarshipProperties);
        baseTable.setItems(baseAmounts);
    }

    private void setUpBaseScholarshipTypeColumn() {
        baseScholarshipTypeColumn.setCellValueFactory(new PropertyValueFactory<>("educationalType"));
    }

    private void setUpBaseScholarshipAmountColumn() {
        baseScholarshipAmountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        baseScholarshipAmountColumn.setCellFactory(TextFieldTableCell.forTableColumn(new CellDoubleStringConverter()));
        baseScholarshipAmountColumn.setOnEditCommit(event -> {
            final Double value = event.getNewValue() != null ?
                    event.getNewValue() : event.getOldValue();
            ((BaseAmount) event.getTableView().getItems()
                    .get(event.getTablePosition().getRow())).setAmount(value);
            baseTable.refresh();
        });
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
        scholarship.setScholarshipProperties(new ArrayList<>(scholarshipProperties));
        scholarship.setBaseAmounts(new ArrayList<>(baseAmounts));
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
            this.scholarship.setBaseAmounts(new ArrayList<>());
        }
        scholarshipName.setText(this.scholarship.getType());
        if (this.scholarship.isEducational()) {
            scholarshipProperties.addAll(this.scholarship.getScholarshipProperties());
            scholarshipProperty = new ScholarshipProperty();
            showEducationalElements();
        } else {
            scholarshipProperty = this.scholarship.getScholarshipProperties().get(0);
        }
        initBaseAmountTable();
    }

    private void initBaseAmountTable() {
        for (EducationalScholarshipType type : EducationalScholarshipType.values()) {
            boolean found = false;
            for (BaseAmount baseAmount : this.scholarship.getBaseAmounts()) {
                if (baseAmount.getEducationalType() == type) {
                    baseAmounts.add(baseAmount);
                    found = true;
                    break;
                }
            }
            if (!found) {
                BaseAmount baseAmount = new BaseAmount();
                baseAmount.setEducationalType(type);
                baseAmount.setScholarship(this.scholarship);
                baseAmounts.add(baseAmount);
            }
        }
    }

    private void showEducationalElements() {
        yesRadioButton.setSelected(true);
        tablePane.setVisible(true);
        amountGridPane.setVisible(false);
    }

    @FXML
    void add(ActionEvent event) {
        ScholarshipProperty sp = new ScholarshipProperty();
        scholarshipProperties.add(sp);
    }

    @FXML
    void delete(ActionEvent event) {
        int selectedIndex = propertyTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            ScholarshipProperty scholarshipProperty =
                    (ScholarshipProperty) propertyTable.getSelectionModel().getSelectedItem();
            scholarshipPropertyService.delete(scholarshipProperty);
            scholarshipProperties.remove(selectedIndex);
            propertyTable.refresh();
        } else {
            PaneUtil.showConfirmModal("Ошибка при выборе"
                    , "Не выбран тип стипендии"
                    , "Пожалуйста, выберите тип стипендии в таблице.");
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
        scholarshipName.setEditable(false);
        yesRadioButton.setDisable(true);
        noRadioButton.setDisable(true);
        amountTextField.setEditable(false);

        propertyButtons.setVisible(false);
        baseTable.setEditable(false);
        propertyTable.setEditable(false);
    }
}
