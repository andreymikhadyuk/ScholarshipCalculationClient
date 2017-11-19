package com.mikhadyuk.scholarshipcalculator.connection;

import java.io.Serializable;

public enum ActionType implements Serializable {
    LOGIN("Вход в аккаунт"),
    REGISTRATION("Регистрация");

    private final String actionDescription;
    private ActionType(String actionDescription) {
        this.actionDescription = actionDescription;
    }

    public String getActionDescription() {
        return this.actionDescription;
    }

    private static final long serialVersionUID = 2L;
}

