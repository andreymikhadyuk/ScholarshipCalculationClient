package com.mikhadyuk.scholarshipcalculator.runner;

import com.mikhadyuk.scholarshipcalculator.controller.MainController;
import javafx.application.Application;
import javafx.stage.Stage;

public class ClientRunner extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        MainController.init(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
