package com.mikhadyuk.scholarshipcalculator.model;

import java.io.Serializable;

public class Person implements Serializable{
    private static final long serialVersionUID = 1L;

    private int id;

    private String lastName;

    private String firstName;

    private String patronymic;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }
}
