package com.mikhadyuk.scholarshipcalculator.model;

import java.util.List;

public class Student extends Person {
    private int groupNumber;

    private Speciality speciality;

    private List<Mark> marks;

    private boolean disabilityGroup;

    private boolean socialScholarship;

    private boolean personalScholarship;

    private boolean nominalScholarship; //именная

    public int getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(int groupNumber) {
        this.groupNumber = groupNumber;
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }

    public List<Mark> getMarks() {
        return marks;
    }

    public void setMarks(List<Mark> marks) {
        this.marks = marks;
    }

    public boolean isDisabilityGroup() {
        return disabilityGroup;
    }

    public void setDisabilityGroup(boolean disabilityGroup) {
        this.disabilityGroup = disabilityGroup;
    }

    public boolean isSocialScholarship() {
        return socialScholarship;
    }

    public void setSocialScholarship(boolean socialScholarship) {
        this.socialScholarship = socialScholarship;
    }

    public boolean isPersonalScholarship() {
        return personalScholarship;
    }

    public void setPersonalScholarship(boolean personalScholarship) {
        this.personalScholarship = personalScholarship;
    }

    public boolean isNominalScholarship() {
        return nominalScholarship;
    }

    public void setNominalScholarship(boolean nominalScholarship) {
        this.nominalScholarship = nominalScholarship;
    }
}
