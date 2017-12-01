package com.mikhadyuk.scholarshipcalculator.service;

import com.mikhadyuk.scholarshipcalculator.connection.ActionType;
import com.mikhadyuk.scholarshipcalculator.connection.ServerConnection;
import com.mikhadyuk.scholarshipcalculator.model.Faculty;
import com.mikhadyuk.scholarshipcalculator.model.Speciality;

import java.io.IOException;
import java.util.List;

public class FacultyService {
    private ServerConnection serverConnection;

    public FacultyService() {
        serverConnection = ServerConnection.getInstance();
    }

    public List<Faculty> getAllFaculties() {
        List<Faculty> faculties = null;
        try {
            serverConnection.send(ActionType.GETTING_LIST_OF_DATA);
            serverConnection.send(Faculty.class);
            faculties = (List<Faculty>) serverConnection.receive();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return faculties;
    }

    public Faculty save(Faculty faculty) {
        try {
            setFacultyInFields(faculty);
            serverConnection.send(ActionType.SAVE_DATA);
            serverConnection.send(faculty);
            faculty = (Faculty) serverConnection.receive();
            return faculty;
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

    public Faculty update(Faculty faculty) {
        try {
            setFacultyInFields(faculty);
            serverConnection.send(ActionType.UPDATE_DATA);
            serverConnection.send(faculty);
            faculty = (Faculty) serverConnection.receive();
            return faculty;
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

    public void delete(Faculty faculty) {
        try {
            serverConnection.send(ActionType.DELETE_DATA);
            serverConnection.send(Faculty.class);
            serverConnection.send(faculty.getId());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void setFacultyInFields(Faculty facultyInFields) {
        saveFacultyInSpecialities(facultyInFields);
    }

    private void saveFacultyInSpecialities(Faculty faculty) {
        for (Speciality speciality : faculty.getSpecialities()) {
            speciality.setFaculty(faculty);
        }
    }
}
