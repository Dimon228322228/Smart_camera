package com.dim_on2.smart_camera.database;

public class Person {
    private int id;
    private String fio;
    private String link_to_photo;

    public Person(String fio, int id, String link_to_photo) {
        this.fio = fio;
        this.id = id;
        this.link_to_photo = link_to_photo;
    }

    public int getId() {return id;}

    public String getFio() {return fio;}
}
