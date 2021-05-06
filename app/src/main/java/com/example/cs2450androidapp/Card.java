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
    private boolean clickedState = false;
    private boolean beenChecked = false; //Used to mark correct tiles

    public Card(Context context) {
        super(context);
    }//end Constructor

    public void setOnClickListener() {
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ConcentrationGame.getClickCount() == 2){
                    Toast toast = Toast.makeText(getContext(), "Click the Check Answers button first!", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    clickedState = true;
                    setText(assignedWord);
                    setBackgroundColor(Color.WHITE);
                    ConcentrationGame.addClick();
                }
            }
        });
    }//End Click Event Listener

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

    public void flip(){
        clickedState = false;
        beenChecked = false;
        setText("");
        setBackgroundColor(Color.YELLOW);
    }//end flip method

    public void show(){
        clickedState = false;

        setText(assignedWord);
        setBackgroundColor(Color.WHITE);
    }//end flip method

    public void setAssignedWord(String word) {
        assignedWord = word;
    }//end setAssignedWord

    //Button has been clicked but not checked
    public boolean getClicked() {
        return clickedState;
    }

    public boolean beenChecked() {
        return beenChecked;
    }

    public void setBeenChecked(){
        this.beenChecked = true;
    }

    public String getWord() {
        return assignedWord;
    }
}