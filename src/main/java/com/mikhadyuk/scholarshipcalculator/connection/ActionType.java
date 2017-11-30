package com.mikhadyuk.scholarshipcalculator.connection;

import java.io.Serializable;

public enum ActionType implements Serializable {
    LOGIN("Вход в аккаунт"),
    REGISTRATION("Регистрация"),
    GETTIN_LIST_OF_DATA("Получение списка "),
    SAVE_DATA("Сохранение данных"),
    UPDATE_DATA("Изменение данных"),
    DELETE_DATA("Удаление данных"),
    REPORT_CREATING("Создаение отчета");

    private final String actionDescription;
    private ActionType(String actionDescription) {
        this.actionDescription = actionDescription;
    }

    public String getActionDescription() {
        return this.actionDescription;
    }

    private static final long serialVersionUID = 7L;
}

