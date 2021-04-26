package com.example.cs2450androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    static MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player = MediaPlayer.create(this, R.raw.music);
        player.setLooping(true);
        player.start();

        // Count Down 4 seconds then change over to the game.
        int duration = 4000;
        new CountDownTimer(duration,1000)
        {
            @Override
            public void onTick(long l) { }

            @Override
            public void onFinish() {
                Intent intent = new Intent(MainActivity.this, MainMenu.class);
                startActivity(intent);
            }
        }.start();
    }

    public static void pausePlayer()
    {
        player.pause();
    }

    public static void continuePlayer(){
        player.start();
    }
}//end Class