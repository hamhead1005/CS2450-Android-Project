package com.example.cs2450androidapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HighScore extends AppCompatActivity {

    TextView highScore;

    int lastScore;
    int best1,best2,best3;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        highScore = (TextView) findViewById(R.id.highScore);

        SharedPreferences preferences = getSharedPreferences("PREFS",0);
        lastScore = preferences.getInt("lastScore", 0);
        best1 = preferences.getInt("best1", 0);
        best2 = preferences.getInt("best2", 0);
        best3 = preferences.getInt("best3", 0);


        //replaces scores if there is a new high score
        if(lastScore> best3){
            best3=lastScore;
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("best3", best3);
            editor.apply();
        }

        if(lastScore> best2){
            int temp = best2;
            best2=lastScore;
            best3 = temp;
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("best3", best3);
            editor.putInt("best2", best2);
            editor.apply();
        }

        if(lastScore> best1){
            int temp = best1;
            best1 = lastScore;
            best2 = temp;
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("best2", best2);
            editor.putInt("best1", best1);
            editor.apply();
        }

        //used to display the scores
        highScore.setText("Last Score: "+ lastScore + "\n" +
                "1: " + best1 + "\n" +
                "2: " + best2 + "\n" +
                "3: " + best3 + "\n" );
    }


    //goes back to main on back button push
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), MainMenu.class);
        startActivity(intent);
        finish();
    }




}
