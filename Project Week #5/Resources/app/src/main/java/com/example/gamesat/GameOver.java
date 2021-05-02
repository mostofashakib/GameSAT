package com.example.gamesat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

public class GameOver extends AppCompatActivity {

    Button buttonBackGameOver, buttonExitGameOver;
    private int gameScore;
    private int wordScoreExists;
    private TextView textViewGameOver;

    GameDbHelper gameDbHelper;
    private long gameTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        buttonBackGameOver = findViewById(R.id.buttonBackGameOver);
        buttonExitGameOver = findViewById(R.id.buttonExitGameOver);
        textViewGameOver = findViewById(R.id.textViewGameOver);

        gameDbHelper = new GameDbHelper(this);

        wordScoreExists = getIntent().getIntExtra("wScoreExists", 0);

        if (wordScoreExists > 0){
            gameScore = getIntent().getIntExtra("gameScoreW",0); // either get a word score
            if (gameScore > 0){
                gameTime = Long.parseLong(getIntent().getStringExtra("gameTimeW"));
                gameDbHelper.insertUserNameTimeWordTable(gameDbHelper.getUserName(), gameTime);
            }
        } else {
            gameScore = getIntent().getIntExtra("gameScoreP",0); // either get a word score
            if (gameScore > 0){
                gameTime = Long.parseLong(getIntent().getStringExtra("gameTimeP"));
                gameDbHelper.insertUserNameTimePassageTable(gameDbHelper.getUserName(), gameTime);
            }
        }

        showMessage(gameScore); // display game ending message to the screen


        buttonBackGameOver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PlayScreen.class); // go to play screen
                startActivity(intent);
                finish();
            }
        });

        buttonExitGameOver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
                System.exit(0);
            }
        });
    }

    private void showMessage(int gameScore){
        if (gameScore == 0){
            textViewGameOver.setText("Game Over.");
            //gameDbHelper.clearUserNameTimeWordTable(); // we can clear both leaderboards
            //gameDbHelper.clearUserNameTimePassageTable(); // from here

        } else {
            textViewGameOver.setText("Congratulations! You won.");

        }
    }

}