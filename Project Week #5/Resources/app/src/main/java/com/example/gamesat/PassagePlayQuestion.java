package com.example.gamesat;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class PassagePlayQuestion extends AppCompatActivity {

    Button buttonBackPassPlay, buttonPassPlayExit, bSkipPassPlay;

    private final int DarkGreen = 0xFF006400; // FF is for transparency, rest is rgb

    //----------------------------------------------------
    // countdown variables
    private static final long CountDown_In_Millis_Lev_1 = 30000; // 30 seconds for level 1
    private static final long CountDown_In_Millis_Lev_2 = 20000; // 20 seconds for level 2
    private static final long CountDown_In_Millis_Lev_3 = 10000; // 10 seconds for level 3

    //------------------------------------------------------

    private TextView textViewPassQuestion;
    private TextView textViewPassScore;
    private TextView textViewPassLevel;
    private TextView textViewPassCountDown;
    private RadioGroup rbGroupPassPlay;
    private RadioButton rbPassPlay1;
    private RadioButton rbPassPlay2;
    private RadioButton rbPassPlay3;
    private Button bConfirmPassPlay;

    private List<Question> passQuestionList; // question outcome color

    private ColorStateList textClrDefaultRb;
    private ColorStateList colorDefaultPCd; // countdown color default

    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;

    private Question currentPQ;//current word question
    private int passPlayScore = 5;
    private int minScore = 0;
    private int maxScore = 30;

    private boolean passQuestAnswered = false; // what happens when button is clicked
    private int passQuestionCount; // pass questions count for a level
    private int level = 1; // start at level 1

    private int questIndex = 0; // index starts at 0
    private int prevIndex = 0;
    private long passGameTime = 0L;

    private int levelTippingPoint = 10;

    //-----------------------------------------------
    GameDbHelper gameDbHelper;
    //-----------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passage_play_question);

        buttonBackPassPlay = (Button) findViewById(R.id.buttonBackPassPlay);
        buttonPassPlayExit = (Button) findViewById(R.id.buttonPassPlayExit);
        bSkipPassPlay = findViewById(R.id.buttonSkipPassQuestPlay);
        //-------------------------------------------------------------

        textViewPassQuestion = findViewById(R.id.text_vew_passage_question);
        textViewPassScore = findViewById(R.id.text_view_passage_score);
        textViewPassLevel = findViewById(R.id.text_view_passage_level);
        textViewPassCountDown = findViewById(R.id.text_view_passage_countdown);
        rbGroupPassPlay = findViewById(R.id.radio_group_passage_play);
        rbPassPlay1 = findViewById(R.id.radio_button_passage_play1);
        rbPassPlay2 = findViewById(R.id.radio_button_passage_play2);
        rbPassPlay3 = findViewById(R.id.radio_button_passage_play3);
        bConfirmPassPlay = findViewById(R.id.buttonPlayPassConfirm);
        //----------------------------------------------------------------

        textClrDefaultRb = rbPassPlay1.getTextColors();
        colorDefaultPCd = textViewPassCountDown.getTextColors(); // countdown default colors
        //---------------------------------------------------------------

        gameDbHelper = new GameDbHelper(this);
        gameDbHelper.fillPassageQuestTableIfEmpty(); // fill the table with passage quests
        gameDbHelper.resetWordQuestionCorrectValues();
        gameDbHelper.resetPassageQuestionCorrectValues();

        setPassageQuestionList(level);

        passQuestionCount = passQuestionList.size();
        Collections.shuffle(passQuestionList);

        showNextPassPlayQuestion();

        /************************************************************************************/

        bConfirmPassPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!passQuestAnswered){
                    if (rbPassPlay1.isChecked() || rbPassPlay2.isChecked() || rbPassPlay3.isChecked()){
                        //check answer
                        checkPPAnswer();
                    } else {
                        Toast.makeText(PassagePlayQuestion.this, "Please select an answer.", Toast.LENGTH_SHORT).show();
                    }
                } else { // question answered
                    showNextPassPlayQuestion();
                }

            }
        });
        /**********************************************************************************/

        //---------------------------------------------------------------------

        bSkipPassPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gameDbHelper.updatePassageQuestionCorrectVal(passQuestionList.get(questIndex).getQuestionID(), 1);
                // find new index different from current and previous
                prevIndex = questIndex;

                countDownTimer.cancel(); // stop previous timer
                showNextPassPlayQuestion(); // start anew
            }
        });

    //-----------------------------------------------------------------------------------
        buttonBackPassPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //-------------------------------------------------------------
                AlertDialog alertDialog = new AlertDialog.Builder(PassagePlayQuestion.this)

                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Warning")
                        .setMessage("Are you sure you want to leave play?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //set what would happen when positive button is clicked
                                Intent intent = new Intent(getApplicationContext(), PlayScreen.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //set what should happen when negative button is clicked
                                Toast.makeText(getApplicationContext(),"Resuming Play.",Toast.LENGTH_LONG).show();
                            }
                        })
                        .show();

                //---------------------------------------------------------------


            }
        });
    //-----------------------------------------------------------------------------------

        buttonPassPlayExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
                System.exit(0);
            }
        });

    //-------------------------------------------------------------------

    }

    /******** On Create Ends**********************************/

    private void showNextPassPlayQuestion(){

        //-----------------------------------------------
        rbPassPlay1.setTextColor(textClrDefaultRb);
        rbPassPlay2.setTextColor(textClrDefaultRb);
        rbPassPlay3.setTextColor(textClrDefaultRb);
        rbGroupPassPlay.clearCheck();
        //--------------------------------------------------

        // if the score is not minScore or maxScore
        if (passPlayScore != minScore && passPlayScore != maxScore){

            //------------------------ set the timer and level ---
            if (0 < passPlayScore && passPlayScore < levelTippingPoint){
                timeLeftInMillis = CountDown_In_Millis_Lev_1;
                level = 1;
            } else if (levelTippingPoint <= passPlayScore && passPlayScore < 2*levelTippingPoint){
                timeLeftInMillis = CountDown_In_Millis_Lev_2;
                level = 2;
            } else if (2*levelTippingPoint <= passPlayScore && passPlayScore < 3*levelTippingPoint){
                timeLeftInMillis = CountDown_In_Millis_Lev_3;
                level = 3;
            }

            setPassageQuestionList(level); // select the right list

            //--- find question index, a unique random value
            questIndex = findQuestIndex();
            currentPQ = passQuestionList.get(questIndex); // get passage question

            textViewPassQuestion.setText(currentPQ.getQuestion()); // display the question
            rbPassPlay1.setText(currentPQ.getOption1());
            rbPassPlay2.setText(currentPQ.getOption2());
            rbPassPlay3.setText(currentPQ.getOption3());

            //***********|||||||||||||||||||||||***************************
            textViewPassLevel.setText("Level: " + currentPQ.getLevel());
            //textViewWordLevel.setText(userName_);
            //textViewWordLevel.setText("TimeVar: " + wordGameTime);
            textViewPassScore.setText("Score: " + passPlayScore);

            passQuestAnswered = false;
            bConfirmPassPlay.setText("Confirm");

            startCountDown();

        } else { // score reaches 0 or 30
            finishPassPlay();
        }
    }

    //***************************************************
    private void startCountDown(){
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();

            }

            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                updateCountDownText();
                checkPPAnswer(); // so we don't lose the answer chosen
            }
        }.start();
    }

    //************************************************

    private void updateCountDownText(){

        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60; // seconds left after minutes has been extracted

        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        textViewPassCountDown.setText(timeFormatted);

        if (timeLeftInMillis < 5000){ // change color if less than 5 seconds
            textViewPassCountDown.setTextColor(Color.RED);
        }else {
            textViewPassCountDown.setTextColor(colorDefaultPCd);
        }
    }

    //***************************************************

    private int findQuestIndex() {
        Random random = new Random();

        int randomIndex = random.nextInt(passQuestionCount);

        while (prevIndex == randomIndex) { // next quest is different from previous one
            randomIndex = random.nextInt(passQuestionCount);
        }
        prevIndex = randomIndex;

        return randomIndex;
    }

    //***************************************************************
    private void setPassageQuestionList(int level){
        switch (level){
            case 1: // if level is 1 get level 1 questions
                passQuestionList = gameDbHelper.getAllPassageQuestionsPerLevel(1);
                break;
            case 2:
                passQuestionList = gameDbHelper.getAllPassageQuestionsPerLevel(2);
                break;
            case 3:
                passQuestionList = gameDbHelper.getAllPassageQuestionsPerLevel(3);
                break;
        }
    }


    //******************************************************************
    //******************************************************************
    private void checkPPAnswer(){ // check word play answer

        passQuestAnswered = true; // question has been answered

        countDownTimer.cancel(); // stop the timer once the answer has been locked in

        switch (level){ // compute how long the user takes to complete the game
            case 1:
                passGameTime += (CountDown_In_Millis_Lev_1 - timeLeftInMillis);
                break;
            case 2:
                passGameTime += (CountDown_In_Millis_Lev_2 - timeLeftInMillis);
                break;
            case 3:
                passGameTime += (CountDown_In_Millis_Lev_3 - timeLeftInMillis);
                break;
        }


        RadioButton rbSelected = findViewById(rbGroupPassPlay.getCheckedRadioButtonId());
        int ansNumSelect = rbGroupPassPlay.indexOfChild(rbSelected) + 1; // add 1 since starts at 0

        if (ansNumSelect == currentPQ.getAnswerNr()){ // if correct choice selected

            // increase score
            passPlayScore += 1;

        } else {
            passPlayScore -= 1; // decrease score
            gameDbHelper.updatePassageQuestionCorrectVal(passQuestionList.get(questIndex).getQuestionID(),1);
        }

        // show solution regardless
        showSolutionWP();
    }

    //******************************************************************
    private void showSolutionWP(){
        rbPassPlay1.setTextColor(Color.RED);
        rbPassPlay2.setTextColor(Color.RED);
        rbPassPlay3.setTextColor(Color.RED);

        switch (currentPQ.getAnswerNr()){
            case 1:
                rbPassPlay1.setTextColor(DarkGreen);
                rbPassPlay1.setText(currentPQ.getOption1() + " is correct.");
                break;
            case 2:
                rbPassPlay2.setTextColor(DarkGreen);
                rbPassPlay2.setText(currentPQ.getOption2() + " is correct.");
                break;
            case 3:
                rbPassPlay3.setTextColor(DarkGreen);
                rbPassPlay3.setText(currentPQ.getOption3() + " is correct.");
                break;
        }

        if (passPlayScore != minScore && passPlayScore != maxScore){
            bConfirmPassPlay.setText("Next");
        } else {
            bConfirmPassPlay.setText("Finish");
        }
    }

    //******************************************************************
    private void finishPassPlay() {
        // for now go to welcome screen
        Intent intent = new Intent(getApplicationContext(), GameOver.class);
        intent.putExtra("wScoreExists", 0);
        intent.putExtra("gameScoreP", passPlayScore);

        intent.putExtra("gameTimeP", String.valueOf(passGameTime));

        startActivity(intent);
        finish();
    }

    protected void onDestroy(){
        super.onDestroy();
        if (countDownTimer != null){
            countDownTimer.cancel();
        }
    }

    //****************************************************************
}