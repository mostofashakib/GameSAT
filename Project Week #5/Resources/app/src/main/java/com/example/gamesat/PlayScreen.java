package com.example.gamesat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;

public class PlayScreen extends AppCompatActivity {

    Button buttonBackPlay, buttonPlayWord, buttonPlayPassage, buttonPlayExit;
    Button bWordLeaderBoard, bPassLeaderBoard;


    GameDbHelper gameDbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_screen);

        buttonBackPlay = findViewById(R.id.buttonBackPlay);
        buttonPlayWord = findViewById(R.id.buttonPlayWord);
        buttonPlayPassage = findViewById(R.id.buttonPlayPassage);
        buttonPlayExit = findViewById(R.id.buttonPlayExit);

        bWordLeaderBoard = findViewById(R.id.bWordLeaderBoard);
        bPassLeaderBoard = findViewById(R.id.bPassLeaderBoard);

        gameDbHelper = new GameDbHelper(this);

    //----------------------------------------------------------------------------------
        buttonBackPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Welcome.class);
                startActivity(intent);
                finish();
            }
        });
    //-----------------------------------------------------------------------------------------

        buttonPlayWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WordPlayQuestion.class);
                startActivity(intent);
                finish();
            }
        });
    //-------------------------------------------------------------------------------------
        buttonPlayPassage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),PassagePlayQuestion.class);
                startActivity(intent);
                finish();
            }
        });
    //---------------------------------------------------------------------------------
        buttonPlayExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
                System.exit(0);
            }
        });


    //------------------------------------------------------------------------------

        bWordLeaderBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LeaderBoard.class); // go to leaderboard
                intent.putExtra("wordBoardSelect", 1);

                startActivity(intent);
                finish();
            }
        });


    //-------------------------------------------------------------------

        bPassLeaderBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LeaderBoard.class);
                intent.putExtra("wordBoardSelect", 0);

                startActivity(intent);
                finish();
            }
        });

    //--------------------------------------------------------------

    }


}