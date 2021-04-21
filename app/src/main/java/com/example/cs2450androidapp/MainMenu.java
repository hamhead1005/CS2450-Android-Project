package com.example.cs2450androidapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;

public class MainMenu extends AppCompatActivity {
    private static int gameSize;

    public static int getGameSize()
    {
        return gameSize;
    }

    @Override
    public void onBackPressed() {
        // Do nothing. Disables Back button on this page.
    }//end OnBackPressed

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        String [] options = {"4","6","8","10","12","14","16","18","20"};
        Spinner dropdown = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, options);
        dropdown.setAdapter(adapter);

        //Start New Game
        SwitchCompat switchCompat = findViewById(R.id.musicSwitchMenu);
        Button startbutton = (Button) findViewById(R.id.startButton);
        startbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String test = String.valueOf(dropdown.getSelectedItem());
                gameSize = Integer.parseInt(test);

                Intent intent = new Intent(MainMenu.this, ConcentrationGame.class);
                startActivity(intent);
            }
        });

        //For Music Switch
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