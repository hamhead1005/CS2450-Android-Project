package com.example.cs2450androidapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ConcentrationGame extends AppCompatActivity {
    private static int buttonsClicked = 0;
    private int buttonCount = 0;
    GridLayout simpleGrid;
    String [] finalWords;
    public Card [] board;

    @Override
    public void onPause() {
        super.onPause();
        buttonsClicked = 0; //reset button clicked counter
    }

    @Override
    public void onBackPressed() {
        // Do nothing. Disables Back button on this page.
    }//end OnBackPressed

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concentration_game);

        // Adds back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        board = new Card[buttonCount]; //Intiallize size of the Card array

        //Create Board
        simpleGrid = (GridLayout) findViewById(R.id.CardGrid);
        for(int i = 0; i < buttonCount; i++){
            Card c = new Card(this);
            c.setId(i);
            c.setAssignedWord(finalWords[i]);
            c.setProperties();
            simpleGrid.addView(c);
            board[i] = c;
        }//end for


        //Try Again Button
        Button tryButton = findViewById(R.id.TryAgainButton);
        tryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonsClicked = 0;

                flipAllCards();
            }
        });


        //Check Answers Button
        Button checkButton = findViewById(R.id.CheckAnswerButton);
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonsClicked = 0;

                for(int i = 0; i < buttonCount; i++) {
                    if(board[i].getClicked()){
                        if(!matchFound(board[i])){
                            flipAllCards();
                        }
                    }
                }//end for

                if(gameWon()){
                    Toast toast = Toast.makeText(ConcentrationGame.this, "You Won!", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }//end OnCreate


    /*                              *
       Methods outside of On Create *
                                    *
     */

    /**
     *  Checks to see if the player matched all the cards
     *
     * @return true if game won. Triggers message
     */
    private boolean gameWon() {
        boolean result = false;
        int correctAnswers = 0;

        for(int i = 0; i < buttonCount; i++){
            if(board[i].beenChecked()){
                correctAnswers++;
            }
        }

        if(correctAnswers == buttonCount){
            return true;
        }

        return result;
    }//end gameWon

    /**
     * Used to flip all Cards
     */
    private void flipAllCards() {
        for(int i = 0; i < buttonCount; i++){
            board[i].flip();
        }
    }//end flipAllCards

    private void filpIncorrectCards(){
        for(int i = 0; i < buttonCount; i++){
            if(!board[i].beenChecked()){
                board[i].flip();
            }
        }
    }

    /**
     * Create the random string used for the card initilization
     *
     * Makes sure each word occurs twice and is in random order
     *
     * @param buttons number of buttons needed
     * @return Array of Strings
     */
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

    /**
     *  Used to check if a match is found between passed in target and other clicked cards
     *
     *  Also makes sure target Card is not checked against itself
     *
     * @param target card you want to compare
     * @return true if matchfound. false if not
     */
    public boolean matchFound(Card target){
        boolean found = false;

        for(int x = 0; x < buttonCount; x++) {
            if (board[x].getClicked() && target.getWord().equals(board[x].getWord()) && target.getId() != board[x].getId()) {
                target.setBeenChecked();
                board[x].setBeenChecked();
                return true;
            }
        }//end for

        return found;
    }//end matchFound()

    /**
     * add 1 to the clickCounter
     */
    public static void addClick(){
        buttonsClicked++;
    }//end addClick()


    /**
     * Getter for static click counter
     *
     * @return number of clicks
     */
    public static int getClickCount() {
        return  buttonsClicked;
    }//end getClickCount()
}//End Class