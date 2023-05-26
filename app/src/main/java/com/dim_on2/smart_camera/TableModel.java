package com.dim_on2.smart_camera;

import java.util.Date;

public class TableModel {
    private String name;
    private String id;
    private Date time;

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public Date getTime() {
        return time;
    }

    public TableModel(String name, String id, Date date) {
        this.name = name;
        this.id = id;
        this.time = date;
    }
}
