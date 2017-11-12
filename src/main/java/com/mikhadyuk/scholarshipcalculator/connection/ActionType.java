package com.mikhadyuk.scholarshipcalculator.connection;

public enum ActionType {
    LOGIN("Вход в аккаунт");

    private final String actionDescription;
    private ActionType(String actionDescription) {
        this.actionDescription = actionDescription;
    }

    public String getActionDescription() {
        return this.actionDescription;
    }
}
