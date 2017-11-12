package com.mikhadyuk.scholarshipcalculator.service;

import com.mikhadyuk.scholarshipcalculator.dto.UserDto;

import java.io.*;

public class UserService {
    private final static String userDataFile = "user_data.txt";

    private static UserService instance;

    private UserService() {
    }

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public void saveCurrentUserInLocal(UserDto user) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(userDataFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public UserDto loadCurrentUser() {
        UserDto user = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(userDataFile);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            user = (UserDto) objectInputStream.readObject();
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
