package com.mikhadyuk.scholarshipcalculator.service;

import com.mikhadyuk.scholarshipcalculator.connection.ActionType;
import com.mikhadyuk.scholarshipcalculator.connection.ServerConnection;
import com.mikhadyuk.scholarshipcalculator.model.Mark;

import java.io.IOException;
import java.util.List;

public class MarkService {
    private ServerConnection serverConnection;

    public MarkService() {
        serverConnection = ServerConnection.getInstance();
    }

    public void delete(Mark mark) {
        try {
            serverConnection.send(ActionType.DELETE_DATA);
            serverConnection.send(Mark.class);
            serverConnection.send(mark.getId());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

//    public List<Mark> findAllByStudentId() {
//        try {
//            serverConnection.send(ActionType.DELETE_DATA);
//            serverConnection.send(Mark.class);
//            serverConnection.send(mark.getId());
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.exit(1);
//        }
//    }
}
