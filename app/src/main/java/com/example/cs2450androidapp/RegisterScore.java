package com.example.cs2450androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.File;

import android.view.View;
import android.widget.*;

public class RegisterScore extends AppCompatActivity {
private String file = "myfile";
private String fileContents;
private Button saveButton;
private EditText nameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_score);

        saveButton = findViewById(R.id.saveButton);
        nameText = findViewById(R.id.editTextTextPersonName);


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int highScore = HighScore.getLastScore();



            }
        });

    }
}