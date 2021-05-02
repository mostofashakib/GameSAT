package com.example.gamesat;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PassageTrainQuestion extends AppCompatActivity {

    Button buttonBackPassTrain, buttonPassTrainExit;
    private final int DarkGreen = 0xFF006400; // FF is for transparency, rest is rgb

    //---------------------------------------------
    private TextView textViewPassageTrainQuestion;
    private RadioGroup radioGroupPassTrain;
    private RadioButton rbPassTrain1;
    private RadioButton rbPassTrain2;
    private RadioButton rbPassTrain3;
    private Button bConfirmPassTrain;

    private List<Question> allPassageQuestionList;
    private List<Question> trainPassageQuestionList;

    private ColorStateList rbDefaultColorPT;

    private Question currentPQ;

    private boolean passQuestAnswered = false;
    private int allPassQuestCount;
    private int trainPassQuestCount;
    private int minRepeats = 3;
    private int questIndex = 0;

    //------------------------------------------
    GameDbHelper gameDbHelper;
    //------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passage_train_question);


        buttonBackPassTrain = (Button) findViewById(R.id.buttonBackPassTrain);
        buttonPassTrainExit = (Button) findViewById(R.id.buttonPassTrainExit);

        //----------------------------------------------------------
        textViewPassageTrainQuestion = findViewById(R.id.text_vew_pass_train_quest);
        radioGroupPassTrain = findViewById(R.id.radio_group_pass_train);
        rbPassTrain1 = findViewById(R.id.radio_button_pass_train1);
        rbPassTrain2 = findViewById(R.id.radio_button_pass_train2);
        rbPassTrain3 = findViewById(R.id.radio_button_pass_train3);

        bConfirmPassTrain = findViewById(R.id.buttonPassTrainConfirm);
        //-----------------------------------------------------

        rbDefaultColorPT = rbPassTrain1.getTextColors(); // store the default button color
        //-------------------------------------------------

        gameDbHelper = new GameDbHelper(this);
        gameDbHelper.fillPassageQuestTableIfEmpty();

        allPassageQuestionList = gameDbHelper.getAllPassageQuestions();
        gameDbHelper.resetPassageQuestionCorrectValues();

        trainPassageQuestionList = new ArrayList<>();
        allPassQuestCount = allPassageQuestionList.size();

        if (allPassQuestCount > 0){
            Collections.shuffle(allPassageQuestionList);
        }

        getPassageTrainList();
        if (!trainPassageQuestionList.isEmpty()){
            trainPassQuestCount = trainPassageQuestionList.size();
            Collections.shuffle(trainPassageQuestionList);
        } else {
            trainPassQuestCount = 0;
        }


        showNextPassageTrainQuestion();
        /*****************************************************************/

        bConfirmPassTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!passQuestAnswered){
                    if (rbPassTrain1.isChecked() || rbPassTrain2.isChecked() || rbPassTrain3.isChecked()){
                        checkPTAnswer();
                    } else {
                        Toast.makeText(PassageTrainQuestion.this, "Please select an answer.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    showNextPassageTrainQuestion();
                }

            }
        });

        /*******************************************************************/

    //------------------------------------------------------------------------------
        buttonBackPassTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //-------------------------------------------------------------
                AlertDialog alertDialog = new AlertDialog.Builder(PassageTrainQuestion.this)

                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Warning")
                        .setMessage("Are you sure you want to leave training?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //set what would happen when positive button is clicked
                                Intent intent = new Intent(getApplicationContext(), TrainScreen.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //set what should happen when negative button is clicked
                                Toast.makeText(getApplicationContext(),"Resuming Training.",Toast.LENGTH_LONG).show();
                            }
                        })
                        .show();

                //---------------------------------------------------------------


            }
        });
    //-------------------------------------------------------------------------------

        buttonPassTrainExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
                System.exit(0);
            }
        });

    //---------------------------------------------------------------------------------

    }

    /**********************---On----Create-----Ends-----******************/

    //***********************************************
    private void getPassageTrainList(){
        for (Question question: allPassageQuestionList){
            if (question.getCorrectVal() > 0 && question.getCorrectVal() < minRepeats){
                trainPassageQuestionList.add(question);
            }
        }
    }

    //*****************************************************

    private void showNextPassageTrainQuestion(){

        //----------------------------------------
        rbPassTrain1.setTextColor(rbDefaultColorPT);
        rbPassTrain2.setTextColor(rbDefaultColorPT);
        rbPassTrain3.setTextColor(rbDefaultColorPT);
        radioGroupPassTrain.clearCheck();
        //------------------------------------------

        if (trainPassQuestCount > 0 ){
            questIndex = findQuestionIndex(trainPassQuestCount); // get missed question if possible
            currentPQ = trainPassageQuestionList.get(questIndex);
        } else {
            questIndex = findQuestionIndex(allPassQuestCount); // or any question after
            currentPQ = allPassageQuestionList.get(questIndex);
        }

        textViewPassageTrainQuestion.setText(currentPQ.getQuestion()); // display question
        rbPassTrain1.setText(currentPQ.getOption1());
        rbPassTrain2.setText(currentPQ.getOption2());
        rbPassTrain3.setText(currentPQ.getOption3());

        passQuestAnswered = false; // getting things ready for the next question
        bConfirmPassTrain.setText("Confirm");

    }
    //*******************************************

    private int findQuestionIndex(int seedVal){

        Random random = new Random();
        int randomIndex = random.nextInt(seedVal);
        return randomIndex;
    }
    //******************************************************

    private void checkPTAnswer(){

        passQuestAnswered = true; // question has been answered
        int correctVal = currentPQ.getCorrectVal();

        RadioButton rbSelected = findViewById(radioGroupPassTrain.getCheckedRadioButtonId());
        int ansNumSelect = radioGroupPassTrain.indexOfChild(rbSelected) + 1;


        if (ansNumSelect == currentPQ.getAnswerNr()){ // if answer is correct
            if (correctVal > 0){ // if the user has gotten the question wrong at some point

                correctVal += 1;
                if (correctVal < minRepeats){

                    setQuestionCorrectValue(correctVal);
                    trainPassageQuestionList.clear();
                    getPassageTrainList();
                    trainPassQuestCount = trainPassageQuestionList.size();
                } else { // remove question if the user gets it right twice in a row

                    setQuestionCorrectValue(0);
                    trainPassageQuestionList.clear();
                    getPassageTrainList();
                    trainPassQuestCount = trainPassageQuestionList.size();
                }
            }
        } else { // if wrong choice selected
            if (!isPassageQuestInTrainList()){
                correctVal += 1;
                setQuestionCorrectValue(correctVal);
                trainPassageQuestionList.clear();
                getPassageTrainList();
                trainPassQuestCount = trainPassageQuestionList.size();
            }
        }

        showSolutionPT();

    }

    //***********************************************
    private void showSolutionPT(){

        rbPassTrain1.setTextColor(Color.RED);
        rbPassTrain2.setTextColor(Color.RED);
        rbPassTrain3.setTextColor(Color.RED);

        switch (currentPQ.getAnswerNr()){
            case 1:
                rbPassTrain1.setTextColor(DarkGreen);
                rbPassTrain1.setText(currentPQ.getOption1() + " is correct.");
                break;
            case 2:
                rbPassTrain2.setTextColor(DarkGreen);
                rbPassTrain2.setText(currentPQ.getOption2() + " is correct.");
                break;
            case 3:
                rbPassTrain3.setTextColor(DarkGreen);
                rbPassTrain3.setText(currentPQ.getOption3() + " is correct.");
                break;

        }

        bConfirmPassTrain.setText("Next ");
    }

    //******************************************************
    private boolean isPassageQuestInTrainList(){
        if (trainPassageQuestionList.isEmpty()){
            return false;
        } else {
            for (Question question: trainPassageQuestionList){
                if (currentPQ.getQuestionID() == question.getQuestionID()){
                    return true;
                }
            }
            return false;
        }
    }
    //***************************************************

    //*****************************************************

    private void setQuestionCorrectValue(int correctValue){
        for (Question question: allPassageQuestionList){
            if (currentPQ.getQuestionID() == question.getQuestionID()){
                question.setCorrectVal(correctValue);
            }
        }
    }

    //*******************************************************






}