package com.mikhadyuk.scholarshipcalculator.service;

import com.mikhadyuk.scholarshipcalculator.model.User;

import java.io.*;

public class UserService {
    private final static String userDataFile = "user_data.txt";

    public void saveCurrentUserInLocal(User user) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(userDataFile);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(user);
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
