package com.dim_on2.smart_camera.database;

import java.util.ArrayList;

public class TeacherRoomManage {
    private int teacher_id;
    private ArrayList<Integer> rooms_id = new ArrayList<>();

    public TeacherRoomManage(int teacher_id) {
        this.teacher_id = teacher_id;
    }

    public void addRoom(int id) {
        rooms_id.add(id);
    }

    public void addRooms(ArrayList<Integer> rooms_id) {
        for (int room_id: rooms_id) {
            this.rooms_id.add(room_id);
        }
    }

    public void updateRooms(ArrayList<Integer> rooms_id) {
        this.rooms_id.clear();
        for (int room_id: rooms_id) {
            this.rooms_id.add(room_id);
        }
    }

    public void deleteRoom(int room_id) {
        rooms_id.remove(room_id);
    }

    public int getTeacher_id() {
        return teacher_id;
    }

    public ArrayList<Integer> getRooms_id() {
        return rooms_id;
    }
}
