package com.example.cs2450androidapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
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
    TextView totalPoints;
    int score = 0; //Game Score

    @Override
    public void onPause() {
        super.onPause();
        buttonsClicked = 0; //reset buttons clicked counter when we leave the Game screen.
    }//end OnPause

    @Override
    public void onBackPressed() {
        // Do nothing. Disables Back button on this page.
    }//end OnBackPressed

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concentration_game);

        // Adds Score TextView
        totalPoints = (TextView) findViewById(R.id.TotalScore);
        totalPoints.setText("Score: " + score);

        // Adds back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Copy status of Music Switch from menu page to music switcher on Game page
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

        /*
          Initialize the Cards in the board based on menu selection
         */

        //Get the Game Size and String Array
        String gameSize = extras.getString("GAME_SIZE");
        buttonCount = Integer.parseInt(gameSize); //How many buttons were selected from the Drop down on the menu screen
        finalWords = getFinalWords(buttonCount);  //List of assigned words
        board = new Card[buttonCount]; //Initialize size of the Card array

        //Create Board
        //Grid Layout from activity_concentration_game.xml
        simpleGrid = (GridLayout) findViewById(R.id.CardGrid);
        for(int i = 0; i < buttonCount; i++){
            Card c = new Card(this);
            c.setId(i);
            c.setAssignedWord(finalWords[i]);
            c.setProperties();
            simpleGrid.addView(c);
            board[i] = c;
        }//end for

        /*
          Initialize the Cards in the board based on menu selection
         */

        //Try Again Button
        Button tryButton = findViewById(R.id.TryAgainButton);
        tryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Dont allow user to click try-again if 2 cards have been flipped
                if(buttonsClicked != 2){
                    filpIncorrectCards();
                    buttonsClicked = 0;
                }
            }
        });


        //Check Answers Button
        Button checkButton = findViewById(R.id.CheckAnswerButton);
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean pointAdder = false;

                for(int i = 0; i < buttonCount; i++) {
                    if(board[i].getClicked()){
                        if(matchFound(board[i])) {
                            if(!board[i].getPreviouslyChecked()){
                                pointAdder = true;
                            }
                            board[i].setPreviouslyChecked();
                        }
                        else{
                            Toast toast = Toast.makeText(ConcentrationGame.this, "Wrong Answer Try Again!", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                }//end for

                if(pointAdder){
                    addPoints();
                    addPoints();
                    buttonsClicked  = 0;
                }//end Add Points
                else {
                    subtractPoints();
                    buttonsClicked = 3; //Click try again to start clicking
                }// end Subtract Points

                //If player wins pop-up Dialog to enter name.
                if(gameWon()){
                    Toast toast = Toast.makeText(ConcentrationGame.this, "You Won!", Toast.LENGTH_SHORT);
                    toast.show();

                    //Input Pop-up
                    AlertDialog.Builder builder = new AlertDialog.Builder(ConcentrationGame.this);
                    builder.setTitle("Save HighScore");
                    builder.setIcon(R.drawable.ic_launcher_background);
                    builder.setMessage("Enter your Name: ");

                    EditText input = new EditText(ConcentrationGame.this);
                    builder.setView(input);


                    //Save
                    builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String name = input.getText().toString();
                            saveHighScore(name);
                            //Toast.makeText(getApplicationContext(),txt, Toast.LENGTH_LONG).show();
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog ad = builder.create();
                    ad.show();
                }//end Game Won
            }
        });

        //end Game Button
        Button endGameButton = findViewById(R.id.endButton);
        endGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAndDisableAllCards();
                checkButton.setEnabled(false);
                tryButton.setEnabled(false);
                score = 0;
                totalPoints.setText("Score: " + score);
            }
        });
    }//end OnCreate

    /*                              *
       Methods outside of On Create *
                                    *
     */

    //method for keeping score
    //addition of points
    public void addPoints() {
        score+=1;                                                                                   //adds 2 to score if match is correct
        totalPoints.setText("Score: " + score);                                                     //updates textView totalPoints
    }

    //subtraction of points
    public void subtractPoints(){
        if (score==0){                                                                              //checks if score = 0 if so stays at zero
            totalPoints.setText("Score: " + score);                                                 //updates textView totalPoints
        }else{
            score-=1;                                                                               //else it will subtract 1 from total score
            if (score<0)                                                                            //if score were to reach below zero, sets score to 0
                score=0;
            totalPoints.setText("Score: " + score);                                                 //updates textView totalPoints
        }
    }

    /**
     *  Checks to see if the player matched all the cards
     *  Disable all button except new game if winner wins
     *  @return true if game won. Triggers message
     */
    private boolean gameWon() {
        boolean result = false;
        int correctAnswers = 0;

        for(int i = 0; i < buttonCount; i++){
            if(board[i].getMarkedCorrect()){
                correctAnswers++;
            }
        }

        if(correctAnswers == buttonCount){
            Button checkButton = findViewById(R.id.CheckAnswerButton);
            checkButton.setEnabled(false);
            Button tryButton = findViewById(R.id.TryAgainButton);
            tryButton.setEnabled(false);
            Button endGame = findViewById(R.id.endButton);
            endGame.setEnabled(false);
            return true;
        }

        return result;
    }//end gameWon

    /**
     * Saves highscore into PREFS.xml file
     *
     * HighScore includes score, cards in game and name.
     */
    private void saveHighScore(String enteredName){
        String name = ""; //Name for the new HighScore

        SharedPreferences preferences = getSharedPreferences("PREFS", 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("lastScore", (int) score);
        editor.putString("enteredName", enteredName);
        editor.putInt("lastButton", buttonCount);
        editor.apply();

        Intent intent = new Intent(getApplicationContext(), HighScoreDisplayScreen.class);
        HighScoreDisplayScreen.setNewScore();
        startActivity(intent);
        finish();
    }

    /**
     * Used to show the answers and disable clicking
     */
    private void showAndDisableAllCards() {
        for(int i = 0; i < buttonCount; i++){
            board[i].show();
            buttonsClicked = 3;
        }
    }//end flipAllCards

    private void filpIncorrectCards(){
        for(int i = 0; i < buttonCount; i++){
            if(!board[i].getMarkedCorrect()){
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
                target.setMarkedCorrect();
                board[x].setMarkedCorrect();
                board[x].setPreviouslyChecked();
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