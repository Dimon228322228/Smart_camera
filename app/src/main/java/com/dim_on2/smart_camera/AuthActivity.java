package com.dim_on2.smart_camera;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dim_on2.smart_camera.database.DB;

import java.sql.SQLException;
import java.util.Objects;

public class AuthActivity extends CommonActivity {

    private EditText email;
    private EditText password;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btn = findViewById(R.id.logIn);

        btn.setOnClickListener((View v) ->
        {
            String loginText = email.getText().toString();
            String passwordText = password.getText().toString();
            System.out.println(loginText + passwordText);
            if (!loginText.equals("") && !passwordText.equals("")) {
                try {
                    if (DB.getInstance().isAuth(Integer.parseInt(loginText), passwordText)){
                        updateUI(AuthActivity.this, RoomsManageActivity.class);
                    } else {
                        showError("Invalid login or password");
                    }
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }


}