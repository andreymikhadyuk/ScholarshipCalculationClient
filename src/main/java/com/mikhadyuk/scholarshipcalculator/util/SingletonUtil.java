package com.mikhadyuk.scholarshipcalculator.util;

import java.util.HashMap;
import java.util.Map;

public class SingletonUtil {
    private static Map<String, Object> instancesMap = new HashMap<>();

    public static <T> T getInstance(Class<T> classType) {
        if(instancesMap.containsKey(classType.getName())) {
            return (T) instancesMap.get(classType.getName());
        }
        T instance = null;
        try {
            instance = classType.newInstance();
            instancesMap.put(classType.getName(), instance);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return instance;
    }
}
