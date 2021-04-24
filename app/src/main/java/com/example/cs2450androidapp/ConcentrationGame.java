package com.example.cs2450androidapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
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
        Bundle extras = getIntent().getExtras();

        //Setup Switch Status
        SwitchCompat musicSwitch = findViewById(R.id.musicSwitch);
        boolean switchStatus = extras.getBoolean("AUDIO_SWITCH");
        if(switchStatus) {
            musicSwitch.setChecked(true);
        }//end if

        //Get the Game Size
        TextView test = findViewById(R.id.testTextVie);
        String gameSize = extras.getString("GAME_SIZE");
        test.setText(gameSize);

        //Music Switcher
        musicSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (musicSwitch.isChecked())
                {
                    MainActivity.pausePlayer();
                }
                else if(!musicSwitch.isChecked()){
                    MainActivity.continuePlayer();
                }
            }
        });

        //New Game Button
        Button newGame = findViewById(R.id.NewGameButton);
        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                boolean audioSwitchStatus = musicSwitch.isChecked();
                intent.putExtra("AUDIO_SWITCH_C", audioSwitchStatus);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}