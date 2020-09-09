package com.file.record;

import java.util.HashMap;
import java.util.Map;

public class DataClass {
    private static DataClass dataClass = null;

    public static DataClass getInstance() {
        if (dataClass == null) {
            dataClass = new DataClass();
            data = new HashMap<>();
        }
        return dataClass;
    }

    private DataClass(){

    }

    static Map<String, String> data = null;

    public String getData(String key) {
        data.get(key);
        return data.get(key);
    }

    public void setData(String key, String value) {
        this.data = data;
        data.put(key, value);
    }
}
