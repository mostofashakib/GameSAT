package com.example.gamesat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;

public class Welcome extends AppCompatActivity {

    Button buttonPlay, buttonTrain, buttonExit, buttonHelp;
    GameDbHelper gameDbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);

        buttonPlay = (Button) findViewById(R.id.buttonPlay);
        buttonTrain = (Button) findViewById(R.id.buttonTrain);
        buttonExit = (Button) findViewById(R.id.buttonExit);
        buttonHelp = findViewById(R.id.buttonHelp);

        // username info ---||||----
        gameDbHelper = new GameDbHelper(this);

    //--------------------------------------------------------------------------------
        buttonTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), TrainScreen.class);
                startActivity(intent);
                finish();
            }
        });
    //----------------------------------------------------------------------------------
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), PlayScreen.class);
                startActivity(intent);
                finish();
            }
        });
    //------------------------------------------------------------------------------------
        buttonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
                System.exit(0);
            }
        });
    //----------------------------------------------------------------------------

        buttonHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Help.class);
                startActivity(intent);
                finish();
            }
        });
        //----------------------------------------------------------------------

    }
}