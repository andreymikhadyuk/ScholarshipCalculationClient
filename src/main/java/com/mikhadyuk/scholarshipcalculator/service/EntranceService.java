package com.mikhadyuk.scholarshipcalculator.service;

import com.mikhadyuk.scholarshipcalculator.connection.ActionType;
import com.mikhadyuk.scholarshipcalculator.connection.ServerConnection;
import com.mikhadyuk.scholarshipcalculator.model.User;
import com.mikhadyuk.scholarshipcalculator.model.enums.Role;

import java.io.IOException;

public class EntranceService {
    private ServerConnection serverConnection;

    public EntranceService() {
        serverConnection = ServerConnection.getInstance();
    }

    public void register(String username, String password, Role role,
                         String lastName, String firstName, String patronymic) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);
        user.setLastName(lastName);
        user.setFirstName(firstName);
        user.setPatronymic(patronymic);
        try {
            serverConnection.send(ActionType.REGISTRATION);
            serverConnection.send(user);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
