package com.mikhadyuk.scholarshipcalculator.util;

import java.util.Properties;

public class MessageUtil {
    private static Properties properties = new Properties();

    static {
        properties.put("login.error.wrongLogin", "Неверно введенен логин");
        properties.put("login.error.wrongPassword", "Неверно введенен пароль");
        properties.put("login.error.wrongLoginAndPassword", "Ошибка при входе в аккаунт. Проверьте логин и пароль");

        properties.put("registration.error.wrongName", "ФИО может содержать только буквы и знак \'-\'");
        properties.put("registration.error.wrongConfirmPassword", "Неверно введенен пароль для подтверждения");
        properties.put("registration.error.passwordsNotEquals", "Пароли не совпадают");
        properties.put("registration.error.notUniqueUsername", "Введенный логин уже занят. Введите другой");

        properties.put("student.error.wrongGroupNumber", "Введен неверный номер группы");
    }

    public static String getMessage(String key) {
        if (key == null || key.isEmpty()) {
            return "";
        }
        return (String) properties.get(key);
    }
}
