package com.dim_on2.smart_camera.database;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DB {
    private static DB instance;
    private List<Teacher> teachers;
    private String teacher_password;
    private List<Room> rooms;
    private List<Integer> roomsId;
    private boolean flag;
    private Connection connection;
    private String URL = "jdbc:postgresql://ep-misty-fire-374016.eu-central-1.aws.neon.tech/neondb";
    private String USER = "gabyfollow";
    private String PASSWORD = "e3iJTC6bMFxq";
    private String DB_Driver =  "org.postgresql.Driver";
    private boolean status;

    private DB() throws SQLException, ClassNotFoundException {
        this.connection = getConnection();
    }

    public static DB getInstance() throws SQLException, ClassNotFoundException {
        if (instance == null) instance = new DB();
        return instance;
    }

    public Connection getConnection() throws ClassNotFoundException, SQLException {

        if (connection == null || connection.isClosed()) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        openConnection();
                    } catch (Exception e) {
                        status = false;
                        System.out.print(e.getMessage());
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
            try {
                thread.join();
            } catch (Exception e) {
                e.printStackTrace();
                this.status = false;
            }

        }

        return connection;
    }

    private void openConnection() throws ClassNotFoundException, SQLException {
        Class.forName(DB_Driver);
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    private List<Teacher> getTeachersBD() throws ClassNotFoundException {
        String sql = "SELECT teachers.id, fio FROM teachers LEFT JOIN persons ON teachers.id = persons.id;";
        Statement statement;
        ArrayList<Teacher> teachers = new ArrayList<>();
        try {
            statement = getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                teachers.add(new Teacher(resultSet.getInt(1), resultSet.getString(2)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return teachers;

    }

    private List<Room> getRoomsBD() throws ClassNotFoundException {
        String sql = "SELECT * FROM rooms;";
        Statement statement = null;
        ArrayList<Room> rooms = new ArrayList<>();
        try {
            statement = getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                rooms.add(new Room(resultSet.getInt(1)));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return rooms;
    }

    public List<Teacher> getTeachers() throws ClassNotFoundException {

        Thread thread = new Thread(() -> {
            try {
                teachers = getTeachersBD();
            } catch (Exception e) {
                status = false;
                System.out.print(e.getMessage());
                e.printStackTrace();
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
            this.status = false;
        }
        return teachers;

    }

    public List<Room> getRooms() {
        Thread thread = new Thread(() -> {
            try {
                rooms = getRoomsBD();
            } catch (Exception e) {
                status = false;
                System.out.print(e.getMessage());
                e.printStackTrace();
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
            this.status = false;
        }
        return rooms;
    }

    private List<Room> getRoomByTeacherIdBD(int id) throws SQLException, ClassNotFoundException {
        ArrayList<Room> rooms = new ArrayList<>();
        PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT id_room FROM teacher_room_manage WHERE id_teacher = ?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            rooms.add(new Room(resultSet.getInt(1)));
        }
        preparedStatement.close();
        return rooms;
    }

    public List<Room> getRoomByTeacherId(int id) throws SQLException, ClassNotFoundException {
        Thread thread = new Thread(() -> {
            try {
                rooms = getRoomByTeacherIdBD(id);
            } catch (Exception e) {
                status = false;
                System.out.print(e.getMessage());
                e.printStackTrace();
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
            this.status = false;
        }
        return rooms;
    }

    private List<Integer> getRoomIdByTeacherIdBD(int id) throws SQLException, ClassNotFoundException {
        ArrayList<Integer> rooms = new ArrayList<>();
        PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT id_room FROM teacher_room_manage WHERE id_teacher = ?");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            rooms.add(resultSet.getInt(1));
        }
        preparedStatement.close();
        return rooms;
    }

    public List<Integer> getRoomIdByTeacherId(int id) throws SQLException, ClassNotFoundException {
        Thread thread = new Thread(() -> {
            try {
                roomsId = getRoomIdByTeacherIdBD(id);
            } catch (Exception e) {
                status = false;
                System.out.print(e.getMessage());
                e.printStackTrace();
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
            this.status = false;
        }
        return roomsId;
    }

    private void addTeacherBD(TeacherForBD teacher) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO Teachers VALUES (?, ?)";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, teacher.getId());
            preparedStatement.setString(2, teacher.getPassword());
            preparedStatement.execute();
        }
    }

    public void addTeacher(TeacherForBD teacher) {
        Thread thread = new Thread(() -> {
            try {
                addTeacherBD(teacher);
            } catch (Exception e) {
                status = false;
                System.out.print(e.getMessage());
                e.printStackTrace();
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
            this.status = false;
        }
    }

    private void addRoomToTeacherBD(Teacher teacher, Room room) throws SQLException, ClassNotFoundException {
        String query = "INSERT INTO Teacher_room_manage VALUES (?, ?)";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, teacher.getId());
            preparedStatement.setInt(2, room.getId());
            preparedStatement.execute();
        }
    }

    public void addRoomToTeacher(Teacher teacher, Room room) {
        Thread thread = new Thread(() -> {
            try {
                addRoomToTeacherBD(teacher, room);
            } catch (Exception e) {
                status = false;
                System.out.print(e.getMessage());
                e.printStackTrace();
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
            this.status = false;
        }
    }

    private void deleteRoomFromTeacherBD(Teacher teacher, Room room) throws SQLException, ClassNotFoundException {
        String query = "DELETE FROM Teacher_room_manage WHERE id_teacher=? and id_room=?";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, teacher.getId());
            preparedStatement.setInt(2, room.getId());
            preparedStatement.execute();
        }
    }

    public void deleteRoomFromTeacher(Teacher teacher, Room room) {
        Thread thread = new Thread(() -> {
            try {
                deleteRoomFromTeacherBD(teacher, room);
            } catch (Exception e) {
                status = false;
                System.out.print(e.getMessage());
                e.printStackTrace();
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
            this.status = false;
        }
    }

    private boolean checkIfPersonExistsBD(Person person) throws SQLException, ClassNotFoundException {
        boolean flag_check = false;
        String query = "SELECT count(*) FROM persons WHERE id = ? and fio = ?";
        PreparedStatement preparedStatement = getConnection().prepareStatement(query);
        preparedStatement.setInt(1, person.getId());
        preparedStatement.setString(2, person.getFio());
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            if (resultSet.getInt(1) == 1) flag_check = true;
        }
        preparedStatement.close();
        return flag_check;
    }

    public boolean checkIfPersonExists(Person person) {
        Thread thread = new Thread(() -> {
            try {
                flag = checkIfPersonExistsBD(person);
            } catch (Exception e) {
                status = false;
                System.out.print(e.getMessage());
                e.printStackTrace();
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
            this.status = false;
        }
        return flag;
    }

    private String getTeacherPasswordByIDBD(int id) throws SQLException, ClassNotFoundException {
        String query = "select password from teachers where id=?";
        PreparedStatement preparedStatement = getConnection().prepareStatement(query);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        String s = resultSet.getString(1);
        preparedStatement.close();
        return s;
    }

    public String getTeacherPasswordByID(int id) {
        Thread thread = new Thread(() -> {
            try {
                teacher_password = getTeacherPasswordByIDBD(id);
            } catch (Exception e) {
                status = false;
                System.out.print(e.getMessage());
                e.printStackTrace();
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
            this.status = false;
        }
        return teacher_password;
    }

    private String encryptPassword(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] messageDigest = md.digest(input.getBytes());

        BigInteger no = new BigInteger(1, messageDigest);
        String hashtext = no.toString(16);

        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }
        return hashtext;
    }

    public boolean isAuth(int id, String password){
        try {
            return Objects.equals(getTeacherPasswordByID(id), encryptPassword(password));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
