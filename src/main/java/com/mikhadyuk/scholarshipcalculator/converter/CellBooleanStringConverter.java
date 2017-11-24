package com.mikhadyuk.scholarshipcalculator.converter;

import javafx.util.converter.BooleanStringConverter;

public class CellBooleanStringConverter extends BooleanStringConverter {
    @Override
    public Boolean fromString(final String value) {
        return value.startsWith("Д");
    }
}
