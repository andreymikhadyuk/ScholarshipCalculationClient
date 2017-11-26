package com.mikhadyuk.scholarshipcalculator.service;

import com.mikhadyuk.scholarshipcalculator.connection.ActionType;
import com.mikhadyuk.scholarshipcalculator.connection.ServerConnection;
import com.mikhadyuk.scholarshipcalculator.model.Speciality;

import java.io.IOException;

public class SpecialityService {
    private ServerConnection serverConnection;

    public SpecialityService() {
        serverConnection = ServerConnection.getInstance();
    }

    public void delete(Speciality speciality) {
        try {
            serverConnection.send(ActionType.DELETE_DATA);
            serverConnection.send(Speciality.class);
            serverConnection.send(speciality.getId());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
