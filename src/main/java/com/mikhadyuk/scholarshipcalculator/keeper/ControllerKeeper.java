package com.mikhadyuk.scholarshipcalculator.keeper;

public class ControllerKeeper<T> {
    private Object controller;

    public T getController() {
        return (T) controller;
    }

    public void setController(Object controller) {
        this.controller = controller;
    }
}
