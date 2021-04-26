package com.example.cs2450androidapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.GridLayout;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ConcentrationGame extends AppCompatActivity {
    private int buttonCount = 0;
    GridLayout simpleGrid;
    String [] finalWords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concentration_game);

        //Setup Switch Status
        Bundle extras = getIntent().getExtras();
        SwitchCompat musicSwitch = findViewById(R.id.musicSwitch);
        boolean switchStatus = extras.getBoolean("AUDIO_SWITCH");
        if(switchStatus) {
            musicSwitch.setChecked(true);
        }//end if

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

        //Get the Game Size and String Array
        String gameSize = extras.getString("GAME_SIZE");
        buttonCount = Integer.parseInt(gameSize);
        finalWords = getFinalWords(buttonCount);

        //Create Board
        simpleGrid = (GridLayout) findViewById(R.id.CardGrid);

        for(int i = 0; i < buttonCount; i++){
            Card c = new Card(this);
            c.setId(i);
            c.setAssignedWord(finalWords[i]);
            c.setProperties();
            simpleGrid.addView(c);
        }//end for

    }//end OnCreate

    public String [] getFinalWords(int buttons)
    {
        String [] output = new String[buttons];
        String [] words = {"Fish","Shell","Ocean","Reef",
                            "Sea Water","Waves","Sail Boat",
                            "Treasure","Sun Shine", "Palm Tree"};

        int doubleCounter = 0;
        for(int i =0; i < buttons; i++) {
            output[i] = words[doubleCounter];
            if(i%2 == 1){
                doubleCounter++;
            }
        }//get words need for Size (Each word occurs Twice)

        //Randomize Order
        List<String> stringList = Arrays.asList(output);
        Collections.shuffle(stringList);
        stringList.toArray(output);

        return output;
    }//end getFinalWords
}//End Class