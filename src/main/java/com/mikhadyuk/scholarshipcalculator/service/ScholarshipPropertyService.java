package com.mikhadyuk.scholarshipcalculator.service;

import com.mikhadyuk.scholarshipcalculator.connection.ActionType;
import com.mikhadyuk.scholarshipcalculator.connection.ServerConnection;
import com.mikhadyuk.scholarshipcalculator.model.ScholarshipProperty;

import java.io.IOException;

public class ScholarshipPropertyService {
    private ServerConnection serverConnection;

    public ScholarshipPropertyService() {
        serverConnection = ServerConnection.getInstance();
    }

    public void delete(ScholarshipProperty scholarshipProperty) {
        try {
            serverConnection.send(ActionType.DELETE_DATA);
            serverConnection.send(ScholarshipProperty.class);
            serverConnection.send(scholarshipProperty.getId());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
