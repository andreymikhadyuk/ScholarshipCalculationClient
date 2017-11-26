package com.mikhadyuk.scholarshipcalculator.converter;

import javafx.util.converter.IntegerStringConverter;

public class CellIntegerStringConverter extends IntegerStringConverter {
    @Override
    public Integer fromString(final String value) {
        return value.isEmpty() || !isNumber(value) ? 0 :
                super.fromString(value);
    }
    public boolean isNumber(String value) {
        int size = value.length();
        for (int i = 0; i < size; i++) {
            if (!Character.isDigit(value.charAt(i))) {
                return false;
            }
        }
        return size > 0;
    }
}
