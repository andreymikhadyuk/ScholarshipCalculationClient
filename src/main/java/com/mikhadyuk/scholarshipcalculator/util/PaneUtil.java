package com.mikhadyuk.scholarshipcalculator.util;

import com.mikhadyuk.scholarshipcalculator.controller.MainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class PaneUtil {
    public static Pane load(String path) {
        Pane pane = null;
        try {
            pane = FXMLLoader.load(MainController.class.getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pane;
    }
}
