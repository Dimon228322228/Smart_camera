package com.dim_on2.smart_camera;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dim_on2.smart_camera.database.DB;
import com.dim_on2.smart_camera.database.Room;

import java.sql.SQLException;
import java.util.List;

public class RoomsManageActivity extends CommonActivity {
    public static int teacher_id;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);

        listView = findViewById(R.id.list_rooms);
        RoomAdapter adapter = new RoomAdapter(this, R.layout.room_item, getRooms());
        listView.setAdapter(adapter);
    }

    private List<Room> getRooms() {
        try {
            return DB.getInstance().getRoomByTeacherId(teacher_id);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
