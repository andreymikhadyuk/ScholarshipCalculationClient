package com.mikhadyuk.scholarshipcalculator.service;

import com.mikhadyuk.scholarshipcalculator.connection.ActionType;
import com.mikhadyuk.scholarshipcalculator.connection.ServerConnection;
import com.mikhadyuk.scholarshipcalculator.model.Scholarship;
import com.mikhadyuk.scholarshipcalculator.model.ScholarshipProperty;

import java.io.IOException;
import java.util.List;

public class ScholarshipService {
    private ServerConnection serverConnection;

    public ScholarshipService() {
        serverConnection = ServerConnection.getInstance();
    }

    public List<Scholarship> getAllScholarships() {
        List<Scholarship> scholarships = null;
        try {
            serverConnection.send(ActionType.GETTIN_LIST_OF_DATA);
            serverConnection.send(Scholarship.class);
            scholarships = (List<Scholarship>) serverConnection.receive();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return scholarships;
    }

    public Scholarship save(Scholarship scholarship) {
        try {
            saveScholarshipInScholarshipPropertis(scholarship);
            serverConnection.send(ActionType.SAVE_DATA);
            serverConnection.send(scholarship);
            scholarship = (Scholarship) serverConnection.receive();
            return scholarship;
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

    public Scholarship update(Scholarship scholarship) {
        try {
            saveScholarshipInScholarshipPropertis(scholarship);
            serverConnection.send(ActionType.UPDATE_DATA);
            serverConnection.send(scholarship);
            scholarship = (Scholarship) serverConnection.receive();
            return scholarship;
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

    public void delete(Scholarship scholarship) {
        try {
            serverConnection.send(ActionType.DELETE_DATA);
            serverConnection.send(Scholarship.class);
            serverConnection.send(scholarship.getId());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void saveScholarshipInScholarshipPropertis(Scholarship scholarship) {
        for (ScholarshipProperty scholarshipProperty : scholarship.getScholarshipProperties()) {
            scholarshipProperty.setScholarship(scholarship);
        }
    }
}
