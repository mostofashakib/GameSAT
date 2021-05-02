package com.example.gamesat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;

public class TrainScreen extends AppCompatActivity {

    Button buttonBackTrain, buttonTrainWord, buttonTrainPassage, buttonTrainExit;
    GameDbHelper gameDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_train_screen);

        buttonBackTrain = (Button) findViewById(R.id.buttonBackTrain);
        buttonTrainWord = (Button) findViewById(R.id.buttonTrainWord);
        buttonTrainPassage = (Button) findViewById(R.id.buttonTrainPassage);
        buttonTrainExit = (Button) findViewById(R.id.buttonTrainExit);

        gameDbHelper = new GameDbHelper(this);

    //----------------------------------------------------------------------------------
        buttonBackTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Welcome.class);
                startActivity(intent);
                finish();
            }
        });

    //-------------------------------------------------------------------------------------
        buttonTrainWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WordTrainQuestion.class);
                startActivity(intent);
                finish();
            }
        });

    //------------------------------------------------------------------------------------
        buttonTrainPassage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PassageTrainQuestion.class);
                startActivity(intent);
                finish();
            }
        });
    //----------------------------------------------------------------------------------
        buttonTrainExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
                System.exit(0);
            }
        });


    //---------------------------------------------------------------------------


    }
}