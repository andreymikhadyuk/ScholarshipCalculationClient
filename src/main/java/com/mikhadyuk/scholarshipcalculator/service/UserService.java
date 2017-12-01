package com.mikhadyuk.scholarshipcalculator.service;

import com.mikhadyuk.scholarshipcalculator.connection.ActionType;
import com.mikhadyuk.scholarshipcalculator.connection.ServerConnection;
import com.mikhadyuk.scholarshipcalculator.model.User;

import java.io.*;
import java.util.List;

public class UserService {
    private ServerConnection serverConnection;

    private final static String userDataFile = "user_data.txt";

    public UserService() {
        serverConnection = ServerConnection.getInstance();
    }

    public List<User> getAllUsers() {
        List<User> users = null;
        try {
            serverConnection.send(ActionType.GETTING_LIST_OF_DATA);
            serverConnection.send(User.class);
            users = (List<User>) serverConnection.receive();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return users;
    }

    public User save(User user) {
        try {
            serverConnection.send(ActionType.SAVE_DATA);
            serverConnection.send(user);
            user = (User) serverConnection.receive();
            return user;
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

    public User update(User user) {
        try {
            serverConnection.send(ActionType.UPDATE_DATA);
            serverConnection.send(user);
            user = (User) serverConnection.receive();
            return user;
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

    public void delete(User user) {
        try {
            serverConnection.send(ActionType.DELETE_DATA);
            serverConnection.send(User.class);
            serverConnection.send(user.getId());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void saveCurrentUserInLocal(User user) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(userDataFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(user);
            objectOutputStream.flush();
            objectOutputStream.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User loadCurrentUser() {
        User user = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(userDataFile);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            user = (User) objectInputStream.readObject();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return user;
    }

    public void clearCurrentUserDataInLocal() {
        try(PrintWriter writer = new PrintWriter(userDataFile)) {
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
