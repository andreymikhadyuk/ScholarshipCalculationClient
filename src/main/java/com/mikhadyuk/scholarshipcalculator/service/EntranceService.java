package com.mikhadyuk.scholarshipcalculator.service;

import com.mikhadyuk.scholarshipcalculator.connection.ActionType;
import com.mikhadyuk.scholarshipcalculator.connection.ServerConnection;
import com.mikhadyuk.scholarshipcalculator.model.User;
import com.mikhadyuk.scholarshipcalculator.model.enums.Role;
import com.mikhadyuk.scholarshipcalculator.util.SingletonUtil;

import java.io.IOException;

public class EntranceService {
    private ServerConnection serverConnection;
    private UserService userService;

    public EntranceService() {
        userService = SingletonUtil.getInstance(UserService.class);
    }

    public boolean login(String username, String password) {
        serverConnection = ServerConnection.getInstance();
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        try {
            serverConnection.send(ActionType.LOGIN);
            serverConnection.send(user);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return receiveAndSaveUserInLocal();
    }

    public boolean register(String username, String password, Role role,
                         String lastName, String firstName, String patronymic) {
        serverConnection = ServerConnection.getInstance();
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
        return receiveAndSaveUserInLocal();
    }

    private boolean receiveAndSaveUserInLocal() {
        User user = (User) serverConnection.receive();
        if (user != null) {
            userService.saveCurrentUserInLocal(user);
        }
        return user == null;
    }
}
