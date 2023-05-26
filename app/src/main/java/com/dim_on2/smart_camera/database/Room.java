package com.dim_on2.smart_camera.database;

public class Room {
    private int id;

    public Room(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                '}';
    }

    public int getId() {return id;}
}
