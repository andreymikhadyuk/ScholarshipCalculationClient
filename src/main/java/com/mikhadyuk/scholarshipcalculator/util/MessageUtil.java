package com.mikhadyuk.scholarshipcalculator.util;

import java.util.Properties;

public class MessageUtil {
    private static Properties properties = new Properties();

    static {
        properties.put("login.error.wrongLogin", "Неверно введенен логин");
        properties.put("login.error.wrongPassword", "Неверно введенен пароль");
        properties.put("login.error.wrongLoginAndPassword", "Ошибка при входе в аккаунт. Проверьте логин и пароль");
    }

    public static String getMessage(String key) {
        if (key == null || key.isEmpty()) {
            return "";
        }
        return (String) properties.get(key);
    }
}
