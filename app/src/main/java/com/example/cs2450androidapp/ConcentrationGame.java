package com.example.cs2450androidapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.CompoundButton;
import android.widget.ImageButton;

public class ConcentrationGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concentration_game);

        SwitchCompat switchCompat = findViewById(R.id.musicSwitch);

        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (switchCompat.isChecked())
                {
                    MainActivity.pausePlayer();
                }
                else if(!switchCompat.isChecked()){
                    MainActivity.continuePlayer();
                }
            }
        });

    }
}