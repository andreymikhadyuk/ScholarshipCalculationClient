package com.mikhadyuk.scholarshipcalculator.model;

import java.io.Serializable;
import java.util.List;

public class Faculty implements Serializable{
    private static final long serialVersionUID = 1L;

    private int id;

    private String facultyName;

    private List<Speciality> specialities;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public List<Speciality> getSpecialities() {
        return specialities;
    }

    public void setSpecialities(List<Speciality> specialities) {
        this.specialities = specialities;
    }
}
