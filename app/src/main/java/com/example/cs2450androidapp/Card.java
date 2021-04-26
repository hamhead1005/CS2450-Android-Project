package com.example.cs2450androidapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;

public class Card extends androidx.appcompat.widget.AppCompatTextView {
    String assignedWord;
    boolean clickedState = false;

    public Card(Context context) {
        super(context);
    }//end Constructor

    public void setOnClickListener() {
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                clickedState = true;
                setText(assignedWord);
                setBackgroundColor(Color.WHITE);
            }
        });
    }//End Click Event Listener

    public void setProperties() {
        setBackgroundColor(Color.YELLOW);
        setWidth(200);
        setHeight(280);
        setTextSize(15);
        setGravity(Gravity.CENTER);
        Typeface b = Typeface.defaultFromStyle(Typeface.BOLD);
        setTypeface(b);
        setOnClickListener();
    }//end setProperties

    public void setAssignedWord(String word) {
        assignedWord = word;
    }//end setAssignedWord
}
