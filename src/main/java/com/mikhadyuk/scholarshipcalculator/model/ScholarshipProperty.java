package com.mikhadyuk.scholarshipcalculator.model;

import java.io.Serializable;

public class ScholarshipProperty implements Serializable{
    private static final long serialVersionUID = 1L;

    private int id;

    private Scholarship scholarship;

    private double maxAverageScore;

    private double minAverageScore;

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

    public double getMaxAverageScore() {
        return maxAverageScore;
    }

    public void setMaxAverageScore(double maxAverageScore) {
        this.maxAverageScore = maxAverageScore;
    }

    public double getMinAverageScore() {
        return minAverageScore;
    }

    public void setMinAverageScore(double minAverageScore) {
        this.minAverageScore = minAverageScore;
    }

    public double getIncreaseCoefficient() {
        return increaseCoefficient;
    }

    public void setIncreaseCoefficient(double increaseCoefficient) {
        this.increaseCoefficient = increaseCoefficient;
    }
}
