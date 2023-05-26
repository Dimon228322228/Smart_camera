package com.dim_on2.smart_camera;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.dim_on2.smart_camera.database.Room;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class InfoRoom extends CommonActivity {
    private Room room;
    private RecyclerView recyclerView;
    private TableAdapter adapter;

    private DatePickerDialog datePickerDialog;

    private Button date_picker, back_room_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_room);
        recyclerView = findViewById(R.id.recycle_view);
        date_picker = findViewById(R.id.date_picker);
        back_room_activity = findViewById(R.id.back_room_activity);
        back_room_activity.setOnClickListener( (View view) -> updateUI(InfoRoom.this, RoomsManageActivity.class));
        intitDatePicker();

        setRecycleView();

        date_picker.setOnClickListener( (View v) -> openDataPicker() );
        date_picker.setText(getToday());
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

    private void openDataPicker() {
        datePickerDialog.show();
    }

    private void setRecycleView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TableAdapter(this, this.getList());
        recyclerView.setAdapter(adapter);
    }

    private List<TableModel> getList() {
        List<TableModel> items = new ArrayList<>();
        items.add(new TableModel("1", "2", "3"));
        items.add(new TableModel("1", "2", "3"));
        items.add(new TableModel("1", "2", "3"));
        items.add(new TableModel("1", "2", "3"));
        items.add(new TableModel("1", "2", "3"));
        items.add(new TableModel("1", "2", "3"));
        items.add(new TableModel("1", "2", "3"));
        items.add(new TableModel("1", "2", "3"));
        items.add(new TableModel("1", "2", "3"));
        items.add(new TableModel("1", "2", "3"));
        return items;
    }
}