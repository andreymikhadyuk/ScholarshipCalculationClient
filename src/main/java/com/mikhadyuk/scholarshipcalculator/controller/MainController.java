package com.mikhadyuk.scholarshipcalculator.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    private static Stage mainStage;
    private static BorderPane mainLayout;

    private static Pane goBackPane;

    private static final int minWidth = 800;
    private static final int minHeight = 600;

    public static void init(Stage primaryStage) {
        mainStage = primaryStage;
        mainStage.setTitle("Рассчет студенческих стипендий");
        mainStage.setMinWidth(minWidth);
        mainStage.setMinHeight(minHeight);

        showBaseView();
        showLoginView();
    }

    private static void showBaseView() {
        try {
            mainLayout = FXMLLoader.load(MainController.class.getResource("/view/base/BaseView.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(mainLayout, minWidth, minHeight);
        mainStage.setScene(scene);
        mainStage.show();
    }

    public static void showLoginView() {
        Pane loginPane = null;
        try {
            loginPane = FXMLLoader.load(MainController.class.getResource("/view/entrance/Login.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        mainLayout.setCenter(loginPane);
    }

    public static void setVisibleGoBackButton(boolean show) {
        AnchorPane anchorPane = (AnchorPane) mainLayout.getTop();
        ObservableList<Node> anchorPaneObservableList = anchorPane.getChildren();
        ToolBar toolBar = (ToolBar) anchorPaneObservableList.get(0);
        ObservableList<Node> toolBarObservableList = toolBar.getItems();
        Button goBackButton = (Button) toolBarObservableList.get(2);
        goBackButton.setVisible(show);
    }

    public static Pane getGoBackPane() {
        return goBackPane;
    }

    public static void setGoBackPane(Pane goBackPane) {
        if (goBackPane != null) {
            MainController.goBackPane = goBackPane;
            setVisibleGoBackButton(true);
        }
    }

    public static void setNewPane(Pane pane) {
        mainLayout.setCenter(pane);
    }
}
