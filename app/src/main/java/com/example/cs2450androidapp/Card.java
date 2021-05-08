package com.example.cs2450androidapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Card extends androidx.appcompat.widget.AppCompatTextView {
    private static int cardWidth = 180;
    private static int cardHeight = 260;
    private String assignedWord;
    private boolean clickedState = false; //Used to check if a button has been clicked
    private boolean markedCorrect = false; //Used to mark correct tiles
    private boolean previouslyChecked = false; //Used as a part of the Scoring System

    public Card(Context context) {
        super(context);
    }//end Constructor

    /**
     * Used for when button has been clicked on.
     */
    public void setOnClickListener() {
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ConcentrationGame.getClickCount() == 2){
                    Toast toast = Toast.makeText(getContext(), "Click the Check Answers button first!", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else if(ConcentrationGame.getClickCount() < 2){ //Only flip if number of clicked tiles is less than 2
                    clickedState = true;
                    setText(assignedWord);
                    setBackgroundColor(Color.WHITE);
                    ConcentrationGame.addClick();
                }
            }
        });
    }//End Click Event Listener

    /**
     * Used in intialization process to set Properties of Cards
     */
    public void setProperties() {
        setBackgroundColor(Color.YELLOW);
        setWidth(cardWidth);
        setHeight(cardHeight);
        setTextSize(13);
        setGravity(Gravity.CENTER);
        Typeface b = Typeface.defaultFromStyle(Typeface.BOLD);
        setTypeface(b);
        setOnClickListener();
    }//end setProperties

    /**
     * Used to flip back to its blank un clicked state
     */
    public void flip(){
        clickedState = false;
        markedCorrect = false;
        setText("");
        setBackgroundColor(Color.YELLOW);
    }//end flip method

    /**
     * Used when end game button is pressed to show all cards with their assigned words
     */
    public void show(){
        clickedState = false;

        setText(assignedWord);
        setBackgroundColor(Color.WHITE);
    }//end flip method

    /**
     * Used during initilization to set the assigned word for each card.
     * @param word
     */
    public void setAssignedWord(String word) {
        assignedWord = word;
    }//end setAssignedWord

    /**
     * Button has been clicked but not checked yet
     * @return true if clicked, False if not
     */
    public boolean getClicked() {
        return clickedState;
    }

    /**
     * Card has been marked correct
     * @return true if marked carrect
     */
    public boolean getMarkedCorrect() {
        return markedCorrect;
    }

    /**
     * Marked as correct
     */
    public void setMarkedCorrect(){
        this.markedCorrect = true;
    }

    /**
     *
     * @return assigned Word
     */
    public String getWord() {
        return assignedWord;
    }

    /**
     * Used for scoring system
     */
    public void setPreviouslyChecked() {
        previouslyChecked = true;
    }

    public boolean getPreviouslyChecked(){
        return previouslyChecked;
    }
}