package com.dim_on2.smart_camera;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class CommonActivity extends AppCompatActivity {
    protected void updateUI(Context now, Class<? extends AppCompatActivity> future){
        Intent intent = new Intent(now, future);
        startActivity(intent);
    }
}
