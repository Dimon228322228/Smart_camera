package com.dim_on2.smart_camera.database;

public class Teacher {
    private int id;
    private String fio;

    public Teacher(int id, String fio) {
        this.id = id;
        this.fio = fio;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "id=" + id +
                ", fio='" + fio + '\'' +
                '}';
    }

    public String getFio() {
        return fio;
    }

    public int getId() {return id;}
}
