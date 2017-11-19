package com.mikhadyuk.scholarshipcalculator.model;

import com.mikhadyuk.scholarshipcalculator.model.enums.EducationalScholarshipType;

import java.io.Serializable;

public class ScholarshipProperty implements Serializable{
    private static final long serialVersionUID = 4L;

    private int id;

    private Scholarship scholarship;

    private double amount;

    private EducationalScholarshipType educationalType;

    private int maxAverageScore;

    private int minAverageScore;

    private double increaseCoefficient;

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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public EducationalScholarshipType getEducationalType() {
        return educationalType;
    }

    public void setEducationalType(EducationalScholarshipType educationalType) {
        this.educationalType = educationalType;
    }

    public int getMaxAverageScore() {
        return maxAverageScore;
    }

    public void setMaxAverageScore(int maxAverageScore) {
        this.maxAverageScore = maxAverageScore;
    }

    public int getMinAverageScore() {
        return minAverageScore;
    }

    public void setMinAverageScore(int minAverageScore) {
        this.minAverageScore = minAverageScore;
    }

    public double getIncreaseCoefficient() {
        return increaseCoefficient;
    }

    public void setIncreaseCoefficient(double increaseCoefficient) {
        this.increaseCoefficient = increaseCoefficient;
    }
}
