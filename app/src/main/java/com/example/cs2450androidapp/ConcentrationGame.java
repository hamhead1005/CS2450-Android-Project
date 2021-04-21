package com.example.cs2450androidapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

public class ConcentrationGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concentration_game);

        TextView test = findViewById(R.id.testTextVie);


        String gameSize = String.valueOf(MainMenu.getGameSize());
        test.setText(gameSize);

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

        Button newGame = findViewById(R.id.NewGameButton);
        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
    }
}