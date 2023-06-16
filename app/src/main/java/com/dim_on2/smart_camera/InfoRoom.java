package com.dim_on2.smart_camera;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.dim_on2.smart_camera.database.DB;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class InfoRoom extends CommonActivity {
    public static int room_id;
    private boolean is_work;
    private RecyclerView recyclerView;
    private TableAdapter adapter;
    private Thread thread;
    private DatePickerDialog datePickerDialog;
    private TextView number_the_room;

    private Button date_picker, back_room_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_room);
        recyclerView = findViewById(R.id.recycle_view);
        date_picker = findViewById(R.id.date_picker);
        date_picker.setText(getToday());
        back_room_activity = findViewById(R.id.back_room_activity);
        back_room_activity.setOnClickListener( (View view) -> updateUI(InfoRoom.this, RoomsManageActivity.class));
        intitDatePicker();
        setRecycleView();
        date_picker.setOnClickListener( (View v) -> openDataPicker() );
        number_the_room = findViewById(R.id.number_the_room);
        number_the_room.setText(""+room_id);
        thread = new Thread(this::updateList);
    }

    @Override
    protected void onStart() {
        super.onStart();
        is_work = true;
        thread.start();
    }

    @Override
    protected void onStop() {
        is_work = false;
        thread.interrupt();
        super.onStop();
    }

    @Override
    protected void onPause() {
        is_work = false;
        thread.interrupt();
        super.onPause();
    }

    private String getToday() {
        Calendar calendar = Calendar.getInstance();
        return makeDateString(calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.YEAR));
    }

    private void intitDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                date_picker.setText(makeDateString(dayOfMonth, month + 1, year));
                setRecycleView();
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(InfoRoom.this, AlertDialog.THEME_HOLO_LIGHT, dateSetListener, year, month, day);
    }

    private String makeDateString(int dayOfMonth, int month, int year) {
        return dayOfMonth + "." + month + "." + year;
    }

    private Date getDateFromString(String str){
        String[] nums = str.split("\\.");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(nums[0]));
        cal.set(Calendar.MONTH, Integer.parseInt(nums[1]) - 1);
        cal.set(Calendar.YEAR, Integer.parseInt(nums[2]));
        return cal.getTime();
    }

    private void openDataPicker() {
        datePickerDialog.show();
    }

    private void setRecycleView() {
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TableAdapter(this, this.getList());
        recyclerView.setAdapter(adapter);
    }

    private void updateList(){
        while(is_work){
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                is_work = false;
            }
            runOnUiThread(this::setRecycleView);
        }
    }
    private List<TableModel> getList() {
        try {
            return DB.getInstance().getTableModelByTime(getDateFromString((String) date_picker.getText()), room_id);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}