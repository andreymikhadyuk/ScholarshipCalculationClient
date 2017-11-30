package com.mikhadyuk.scholarshipcalculator.service;

import com.mikhadyuk.scholarshipcalculator.connection.ActionType;
import com.mikhadyuk.scholarshipcalculator.connection.ServerConnection;

import java.io.IOException;

public class ReportService {
    private ServerConnection serverConnection;

    public ReportService() {
        serverConnection = ServerConnection.getInstance();
    }

    public String createReport() {
        String report = "";
        try {
            serverConnection.send(ActionType.REPORT_CREATING);
            report = (String) serverConnection.receive();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return report;
    }
}
