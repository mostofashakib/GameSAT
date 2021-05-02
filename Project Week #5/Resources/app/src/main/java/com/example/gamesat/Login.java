package com.example.gamesat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class Login extends AppCompatActivity {


    TextInputEditText usernameLoginInput, passwordLoginInput;
    Button buttonLogin, buttonLoginExit;
    TextView textViewSignUp;

    String username, password;
    GameDbHelper gameDbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        usernameLoginInput = findViewById(R.id.usernameLogin);
        passwordLoginInput = findViewById(R.id.passwordLogin);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewSignUp = findViewById(R.id.signUpText);
        buttonLoginExit = (Button) findViewById(R.id.buttonLoginExit);

        gameDbHelper = new GameDbHelper(this);


    //-------------------------------------------------------------------------------
        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SignUp.class);
                startActivity(intent);
                finish();
            }
        });


    //---------------------------------------------------------------------------
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = String.valueOf(usernameLoginInput.getText());
                password = String.valueOf(passwordLoginInput.getText());

                if (!username.equals("") && !password.equals("")){


                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[2];
                            field[0] = "username";
                            field[1] = "password";
                            //Creating array for data
                            String[] data = new String[2];
                            data[0] = username;
                            data[1] = password;
                            PutData putData = new PutData("http://192.168.0.3/LoginRegister/login.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if (result.equals("Success")){

                                        Toast.makeText(getApplicationContext(),result, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), Welcome.class); // move to the welcome screen

                                        gameDbHelper.clearAllLoginData();
                                        gameDbHelper.insertUserLogin(username, password); // store user login data

                                        startActivity(intent);
                                        finish();

                                    } else {

                                        Toast.makeText(getApplicationContext(),result, Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }

                        }
                    });
                } else {

                    Toast.makeText(getApplicationContext(), "All fields are required.", Toast.LENGTH_SHORT).show();
                }

            }
        });

    //------------------------------------------------------------------------------------------------------------------
        buttonLoginExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
                System.exit(0);
            }
        });
    //-------------------------------------------------------------------------------------------------------

    }


}