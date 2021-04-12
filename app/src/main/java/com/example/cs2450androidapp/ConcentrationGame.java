package com.example.cs2450androidapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import java.io.IOException;
import java.net.URI;

public class ConcentrationGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concentration_game);

        MediaPlayer player = MediaPlayer.create(this, R.raw.music);
        player.setLooping(true);
        player.start();

        SwitchCompat switchCompat = findViewById(R.id.musicSwitch);

        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (switchCompat.isChecked())
                    {
                    player.pause();
                    }
                else if(!switchCompat.isChecked()){
                    player.start();
                }
            }
        });

    }
}