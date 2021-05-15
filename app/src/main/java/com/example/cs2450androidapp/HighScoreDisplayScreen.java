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

public class HighScoreDisplayScreen extends AppCompatActivity {

    static boolean newScore = false;
    TextView highScore;

    int best1,best2,best3,lastScore; //Scores
    String name1,name2,name3,enteredName;
    int lastButton;
    int button1,button2,button3; //Number of buttons in the game

    public static void setNewScore() {
        newScore = true;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore_display_screen);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        highScore = (TextView) findViewById(R.id.highScore); //Print highScores here.

        SharedPreferences preferences = getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        //Get Values from save File.
        lastScore = preferences.getInt("lastScore", 0);
        best1 = preferences.getInt("best1", 0);
        best2 = preferences.getInt("best2", 0);
        best3 = preferences.getInt("best3", 0);

        enteredName = preferences.getString("enteredName", "");
        name1 = preferences.getString("name1", "");
        name2 = preferences.getString("name2", "");
        name3 = preferences.getString("name3", "");

        lastButton = preferences.getInt("lastButton", 0);
        button1 = preferences.getInt("button1",0);
        button2 = preferences.getInt("button2",0);
        button3 = preferences.getInt("button3",0);

        if(newScore){
            //replaces scores if there is a new high score
            if(lastScore> best3){
                best3=lastScore;
                name3=enteredName;
                button3 = lastButton;
                //SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("best3", best3);
                editor.putString("name3",name3);
                editor.putInt("button1",button3);
                editor.apply();
            }

            if(lastScore> best2){
                int temp = best2;
                best2=lastScore;
                best3 = temp;

                int tempB = button2;
                button2 = lastButton;
                button3 = tempB;

                String tempName = name2;
                name2 = enteredName;
                name3 = tempName;
                //SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("best3", best3);
                editor.putString("name3",name3);
                editor.putInt("button3",button3);
                editor.putInt("best2", best2);
                editor.putString("name2",name2);
                editor.putInt("button2",button2);
                editor.apply();
            }

            if(lastScore> best1){
                int temp = best1;
                best1 = lastScore;
                best2 = temp;

                int tempB = button1;
                button1 = lastButton;
                button2 = tempB;

                String tempName = name1;
                name1 = enteredName;
                name2 = tempName;

                //SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("best2", best2);
                editor.putInt("best1", best1);
                editor.putString("name2",name2);
                editor.putString("name1",name1);
                editor.putInt("button2",button2);
                editor.putInt("button1",button1);
                editor.apply();
            }
        }//end if

        //used to display the scores
        highScore.setText(
                "Last Score: "+ enteredName + " - " + lastScore + "\n" +
                "1: " + name1 + " - " + best1 + " (Cards: " + button1 + ")" +"\n" +
                "2: " + name2 + " - " + best2 + " (Cards: " + button2 + ")" +"\n" +
                "3: " + name3 + " - " + best3 + " (Cards: " + button3 + ")" +"\n");

        newScore = false;
    }


    //goes back to main on back button push
    @Override
    public void onBackPressed(){
        finish(); //Close Screen go back to menu.
    }
}