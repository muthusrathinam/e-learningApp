package com.example.e_learn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button userNav,adminNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userNav = findViewById(R.id.Admin);
        adminNav = findViewById(R.id.User);

        userNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent user = new Intent(MainActivity.this, userLogin.class);
                startActivity(user);
            }
        });

        adminNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent admin = new Intent(MainActivity.this, userRegistration.class);
                startActivity(admin);
            }
        });
    }
}