package com.mikhadyuk.scholarshipcalculator.model;

import java.io.Serializable;
import java.util.List;

public class Scholarship implements Serializable{
    private static final long serialVersionUID = 1L;

    private int id;

    private String type;

    private List<ScholarshipProperty> scholarshipProperties;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ScholarshipProperty> getScholarshipProperties() {
        return scholarshipProperties;
    }

    public void setScholarshipProperties(List<ScholarshipProperty> scholarshipProperties) {
        this.scholarshipProperties = scholarshipProperties;
    }
}
