package com.example.cs2450androidapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HighScore extends AppCompatActivity {

    TextView highScore;
    Button savehighScore;
    EditText highscoreName;

    static int lastScore;
    int best1,best2,best3;
    String name1,name2,name3,enteredName;
    boolean checkMain;


    public static int getLastScore() {
        return lastScore;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);

        highScore = (TextView) findViewById(R.id.highScore);
        highScore.setVisibility(View.INVISIBLE);
        highscoreName = (EditText) findViewById(R.id.enterName);
        savehighScore = (Button) findViewById(R.id.savehighscoreButton);


        SharedPreferences preferences = getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        savehighScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                highScore.setVisibility(View.VISIBLE);


                enteredName = highscoreName.getText().toString();
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("enteredName",enteredName);
                editor.commit();

                savehighScore.setVisibility(View.INVISIBLE);
                highscoreName.setVisibility(View.INVISIBLE);
            }
        });


        lastScore = preferences.getInt("lastScore", 0);
        best1 = preferences.getInt("best1", 0);
        best2 = preferences.getInt("best2", 0);
        best3 = preferences.getInt("best3", 0);

        enteredName = preferences.getString("enteredName", "");
        name1 = preferences.getString("name1", "");
        name2 = preferences.getString("name2", "");
        name3 = preferences.getString("name3", "");


        //replaces scores if there is a new high score
        if(lastScore> best3){
            best3=lastScore;
            name3=enteredName;
            //SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("best3", best3);
            editor.putString("name3",name3);
            editor.apply();
        }

        if(lastScore> best2){
            int temp = best2;
            best2=lastScore;
            best3 = temp;
            String tempName = name2;
            name2 = enteredName;
            name3 = tempName;
            //SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("best3", best3);
            editor.putString("name3",name3);
            editor.putInt("best2", best2);
            editor.putString("name2",name2);
            editor.apply();
        }

        if(lastScore> best1){
            int temp = best1;
            best1 = lastScore;
            best2 = temp;
            String tempName = name1;
            name1 = enteredName;
            name2 = tempName;
            //SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("best2", best2);
            editor.putInt("best1", best1);
            editor.putString("name2",name2);
            editor.putString("name1",name1);
            editor.apply();
        }

        //used to display the scores
        highScore.setText("Last Score: "+ lastScore + "\n" +
                "1: " + name1 + " - " + best1 + "\n" +
                "2: " + name2 + " - " + best2 + "\n" +
                "3: " + name3 + " - " + best3 + "\n" );
    }


    //goes back to main on back button push
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), MainMenu.class);
        startActivity(intent);
        finish();
    }
}