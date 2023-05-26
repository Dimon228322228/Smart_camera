package com.dim_on2.smart_camera.database;

public class TeacherForBD {
    private int id;
    private String password;

    public TeacherForBD(int id, String password) {
        this.id = id;
        this.password = password;
    }

    @Override
    public String toString() {
        return "TeacherForBD{" +
                "id=" + id +
                ", password='" + password + '\'' +
                '}';
    }

    public int getId() {return id;}
    public String getPassword() {return password;}
}
