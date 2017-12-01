package com.mikhadyuk.scholarshipcalculator.service;

import com.mikhadyuk.scholarshipcalculator.connection.ActionType;
import com.mikhadyuk.scholarshipcalculator.connection.ServerConnection;
import com.mikhadyuk.scholarshipcalculator.model.BaseAmount;

import java.io.IOException;
import java.util.List;

public class BaseAmountService {
    private ServerConnection serverConnection;

    public BaseAmountService() {
        serverConnection = ServerConnection.getInstance();
    }

    public List<BaseAmount> getAllBaseAmounts() {
        List<BaseAmount> faculties = null;
        try {
            serverConnection.send(ActionType.GETTING_LIST_OF_DATA);
            serverConnection.send(BaseAmount.class);
            faculties = (List<BaseAmount>) serverConnection.receive();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return faculties;
    }
}
