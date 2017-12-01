package com.mikhadyuk.scholarshipcalculator.model.enums;

import java.io.Serializable;

public enum EducationalScholarshipType implements Serializable{
    TECHNICAL("Техническая"),
    HUMANITARIAN("Гуманитарная"),
    PEDAGOGICAL("Педагогическая"),
    ECONOMIC("Экономическая"),
    NATURAL("Естественная"),
    MEDICAL("Медицинская"),
    AGRICULTURAL("Сельскохозяйственная"),
    MILITARY("Военная");

    private String label;

    EducationalScholarshipType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return getLabel();
    }

    private static final long serialVersionUID = 3L;
}
