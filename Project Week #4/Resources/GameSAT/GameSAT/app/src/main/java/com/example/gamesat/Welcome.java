package com.example.gamesat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;

public class Welcome extends AppCompatActivity {

    Button buttonPlay, buttonTrain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_welcome);

        buttonPlay = (Button) findViewById(R.id.buttonPlay);
        buttonTrain = (Button) findViewById(R.id.buttonTrain);


        buttonTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), TrainScreen.class);
                startActivity(intent);
                finish();
            }
        });

        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), PlayScreen.class);
                startActivity(intent);
                finish();
            }
        });


    }
}