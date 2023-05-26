package com.dim_on2.smart_camera;

public class TableModel {
    private String name;
    private String id;
    private String time;

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public TableModel(String name, String id, String date) {
        this.name = name;
        this.id = id;
        this.time = date;
    }
}
