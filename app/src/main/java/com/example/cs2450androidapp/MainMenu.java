package com.example.cs2450androidapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;

public class MainMenu extends AppCompatActivity {
    private String gameSize;
    private boolean audioSwitch = false; //T = checked

    //Used for passing music switch status from Concentration Game
    @Override
    public void onActivityResult(int requestCode,int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1)
        {
            if(resultCode == RESULT_OK)
            {
                SwitchCompat switchCompat = findViewById(R.id.musicSwitchMenu);
                boolean switchStatus = data.getBooleanExtra("AUDIO_SWITCH_C", true);
                switchCompat.setChecked(switchStatus);
            }
        }
    }//end OnActivityResult

    @Override
    public void onBackPressed() {
        // Do nothing. Disables Back button on this page.
    }//end OnBackPressed

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        //For DropDown Menu
        String [] options = {"4","6","8","10","12","14","16","18","20"};
        Spinner dropdown = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, options);
        dropdown.setAdapter(adapter);

        //Start New Game Button
        SwitchCompat switchCompat = findViewById(R.id.musicSwitchMenu);
        Button startbutton = (Button) findViewById(R.id.startButton);
        startbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameSize = String.valueOf(dropdown.getSelectedItem());

                Intent i = new Intent(MainMenu.this, ConcentrationGame.class);
                i.putExtra("GAME_SIZE", gameSize);
                i.putExtra("AUDIO_SWITCH", audioSwitch);
                startActivityForResult(i,1);
            }
        });

        //For Music Switch
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (switchCompat.isChecked())
                {
                    audioSwitch = true;
                    MainActivity.pausePlayer();
                }
                else if(!switchCompat.isChecked()){
                    audioSwitch = false;
                    MainActivity.continuePlayer();
                }
            }
        });

        //For Credits Button
        Button creditsButton = (Button) findViewById(R.id.creditsButton);
        creditsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainMenu.this, Credits.class);
                startActivity(i);
            }
        });
    }
    }