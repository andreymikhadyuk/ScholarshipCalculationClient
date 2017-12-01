package com.mikhadyuk.scholarshipcalculator.service;

import com.mikhadyuk.scholarshipcalculator.connection.ActionType;
import com.mikhadyuk.scholarshipcalculator.connection.ServerConnection;
import com.mikhadyuk.scholarshipcalculator.model.Student;

import java.io.IOException;
import java.util.List;

public class StudentService {
    private ServerConnection serverConnection;

    public StudentService() {
        serverConnection = ServerConnection.getInstance();
    }

    public List<Student> getAllStudents() {
        List<Student> students = null;
        try {
            serverConnection.send(ActionType.GETTING_LIST_OF_DATA);
            serverConnection.send(Student.class);
            students = (List<Student>) serverConnection.receive();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return students;
    }

    public Student save(Student student) {
        try {
            serverConnection.send(ActionType.SAVE_DATA);
            serverConnection.send(student);
            student = (Student) serverConnection.receive();
            return student;
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

    public Student update(Student student) {
        try {
            serverConnection.send(ActionType.UPDATE_DATA);
            serverConnection.send(student);
            student = (Student) serverConnection.receive();
            return student;
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

    public void delete(Student student) {
        try {
            serverConnection.send(ActionType.DELETE_DATA);
            serverConnection.send(Student.class);
            serverConnection.send(student.getId());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
