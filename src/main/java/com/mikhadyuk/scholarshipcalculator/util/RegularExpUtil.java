package com.mikhadyuk.scholarshipcalculator.util;

import java.util.regex.Pattern;

public class RegularExpUtil {
    public static final String LOGIN_REG_EXP = "^[A-z][A-z0-9_]{4,}$";
    public static final String PASSWORD_REG_EXP = LOGIN_REG_EXP;
    public static final String NAME_REG_EXP = "^[А-я]([-]?[А-я]+)*$";
    public static final String GROUP_NUMBER_REG_EXP = "^[0-9]{6}$";

    public static boolean isCorrectString(String string, String regExp) {
        Pattern pattern = Pattern.compile(regExp);
        return pattern.matcher(string).matches();
    }
}
