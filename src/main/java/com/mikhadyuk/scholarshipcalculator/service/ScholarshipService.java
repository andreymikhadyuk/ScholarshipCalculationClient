package com.mikhadyuk.scholarshipcalculator.service;

import com.mikhadyuk.scholarshipcalculator.connection.ActionType;
import com.mikhadyuk.scholarshipcalculator.connection.ServerConnection;
import com.mikhadyuk.scholarshipcalculator.model.BaseAmount;
import com.mikhadyuk.scholarshipcalculator.model.Scholarship;
import com.mikhadyuk.scholarshipcalculator.model.ScholarshipProperty;
import com.mikhadyuk.scholarshipcalculator.model.Student;
import com.mikhadyuk.scholarshipcalculator.util.SingletonUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScholarshipService {
    private ServerConnection serverConnection;
    private StudentService studentService;

    public ScholarshipService() {
        serverConnection = ServerConnection.getInstance();
        studentService = SingletonUtil.getInstance(StudentService.class);
    }

    public List<Scholarship> getAllScholarships() {
        List<Scholarship> scholarships = null;
        try {
            serverConnection.send(ActionType.GETTING_LIST_OF_DATA);
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
            setScholarshipInFields(scholarship);
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
            setScholarshipInFields(scholarship);
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

    private void setScholarshipInFields(Scholarship scholarshipInFields) {
        saveScholarshipInScholarshipProperties(scholarshipInFields);
        saveScholarshipInBaseAmount(scholarshipInFields);
    }

    private void saveScholarshipInScholarshipProperties(Scholarship scholarship) {
        for (ScholarshipProperty scholarshipProperty : scholarship.getScholarshipProperties()) {
            scholarshipProperty.setScholarship(scholarship);
        }
    }

    private void saveScholarshipInBaseAmount(Scholarship scholarship) {
        for (BaseAmount baseAmount : scholarship.getBaseAmounts()) {
            baseAmount.setScholarship(scholarship);
        }
    }

    public List<Student> recalculateScholarships() {
        List<Student> students = new ArrayList<>();

        try {
            serverConnection.send(ActionType.RECALCULATE_SCHOLARSHIPS);
            students = (List<Student>) serverConnection.receive();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        return students;
    }
}
