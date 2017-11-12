package com.mikhadyuk.scholarshipcalculator.service;

import com.mikhadyuk.scholarshipcalculator.connection.ServerConnection;

public class ConnectionService {
    private static ConnectionService instance;

    private ConnectionService() {
    }

    public static ConnectionService getInstance() {
        if (instance == null) {
            instance = new ConnectionService();
        }
        return instance;
    }

    public void closeServerConnection() {
        ServerConnection serverConnection = ServerConnection.getInstance();
        serverConnection.closeConnection();
    }
}
