package com.mikhadyuk.scholarshipcalculator.model;

import com.mikhadyuk.scholarshipcalculator.model.enums.EducationalScholarshipType;

import javax.persistence.*;
import java.io.Serializable;

public class BaseAmount implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;

    private Scholarship scholarship;

    private EducationalScholarshipType educationalType;

    private double amount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Scholarship getScholarship() {
        return scholarship;
    }

    public void setScholarship(Scholarship scholarship) {
        this.scholarship = scholarship;
    }

    public EducationalScholarshipType getEducationalType() {
        return educationalType;
    }

    public void setEducationalType(EducationalScholarshipType educationalType) {
        this.educationalType = educationalType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
