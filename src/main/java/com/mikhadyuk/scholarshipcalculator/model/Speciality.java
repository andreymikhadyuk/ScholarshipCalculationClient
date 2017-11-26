package com.mikhadyuk.scholarshipcalculator.model;

import com.mikhadyuk.scholarshipcalculator.model.enums.EducationalScholarshipType;

import java.io.Serializable;
import java.util.List;

public class Speciality implements Serializable{
    private static final long serialVersionUID = 2L;

    private int id;

    private String specialityName;

    private String shortSpecialityName;

    private Faculty faculty;

    private EducationalScholarshipType educationalScholarshipType;

    private List<Student> students;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSpecialityName() {
        return specialityName;
    }

    public void setSpecialityName(String specialityName) {
        this.specialityName = specialityName;
    }

    public String getShortSpecialityName() {
        return shortSpecialityName;
    }

    public void setShortSpecialityName(String shortSpecialityName) {
        this.shortSpecialityName = shortSpecialityName;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public EducationalScholarshipType getEducationalScholarshipType() {
        return educationalScholarshipType;
    }

    public void setEducationalScholarshipType(EducationalScholarshipType educationalScholarshipType) {
        this.educationalScholarshipType = educationalScholarshipType;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
