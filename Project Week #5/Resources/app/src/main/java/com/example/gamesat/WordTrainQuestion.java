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

public class WordTrainQuestion extends AppCompatActivity {

    Button buttonBackWordTrain, buttonWordTrainExit;
    private final int DarkGreen = 0xFF006400; // FF is for transparency, rest is rgb

    //-------------------------------------------
    private TextView textViewWordTrainQuestion;
    private RadioGroup radioGroupWordTrain;
    private RadioButton rbWordTrain1;
    private RadioButton rbWordTrain2;
    private RadioButton rbWordTrain3;
    private Button bConfirmWordTrain;

    private List<Question> allWordQuestionList;
    private List<Question> trainWordQuestionList;

    private ColorStateList rbDefaultColorWT;

    private Question currentWQ;

    private boolean wordQuestAnswered = false;
    private int allWordQuestCount;
    private int trainWordQuestCount;
    private int minRepeats = 3;

    private int questIndex = 0;

    //-------------------------------------------

    GameDbHelper gameDbHelper;

    //------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_train_question);


        buttonBackWordTrain = (Button) findViewById(R.id.buttonBackWordTrain);
        buttonWordTrainExit = (Button) findViewById(R.id.buttonWordTrainExit);

        //----------------------------------------------------------
        textViewWordTrainQuestion = findViewById(R.id.text_vew_word_question_train);
        radioGroupWordTrain = findViewById(R.id.radio_group_word_train);
        rbWordTrain1 = findViewById(R.id.radio_button_word_train1);
        rbWordTrain2 = findViewById(R.id.radio_button_word_train2);
        rbWordTrain3 = findViewById(R.id.radio_button_word_train3);

        bConfirmWordTrain = findViewById(R.id.bConfirmWordTrain);
        //-----------------------------------------------------

        rbDefaultColorWT = rbWordTrain1.getTextColors(); // store the default button color
        //-------------------------------------------------

        gameDbHelper = new GameDbHelper(this);
        gameDbHelper.fillWordQuestTableIfEmpty(); // populate the word question table if needed

        allWordQuestionList = gameDbHelper.getAllWordQuestions(); // get all the questions
        gameDbHelper.resetWordQuestionCorrectValues();

        trainWordQuestionList = new ArrayList<>();
        allWordQuestCount = allWordQuestionList.size();

        if (allWordQuestCount > 0){
            Collections.shuffle(allWordQuestionList);
        }

        getWordTrainList();
        if (!trainWordQuestionList.isEmpty()){
            trainWordQuestCount = trainWordQuestionList.size();
            Collections.shuffle(trainWordQuestionList);
        } else {
            trainWordQuestCount = 0;
        }


        showNextWordTrainQuestion();

        /*****************************************/


        bConfirmWordTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!wordQuestAnswered){
                    if (rbWordTrain1.isChecked() || rbWordTrain2.isChecked() || rbWordTrain3.isChecked()){
                        checkWTAnswer();
                    } else {
                        Toast.makeText(WordTrainQuestion.this, "Please select an answer.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    showNextWordTrainQuestion();
                }

            }
        });



        /******************************************/

    //--------------------------------------------------------------------------------
        buttonBackWordTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //-------------------------------------------------------------
                AlertDialog alertDialog = new AlertDialog.Builder(WordTrainQuestion.this)

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
    //----------------------------------------------------------------------------

        buttonWordTrainExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
                System.exit(0);
            }
        });

    //------------------------------------------------------------------------

    }

    /** ---- End of onCreate ------**/

    private void showNextWordTrainQuestion(){

        //----------------------------------------
        rbWordTrain1.setTextColor(rbDefaultColorWT);
        rbWordTrain2.setTextColor(rbDefaultColorWT);
        rbWordTrain3.setTextColor(rbDefaultColorWT);
        radioGroupWordTrain.clearCheck();
        //------------------------------------------

        if (trainWordQuestCount > 0 ){
            questIndex = findQuestionIndex(trainWordQuestCount); // get missed question if possible
            currentWQ = trainWordQuestionList.get(questIndex);
        } else {
            questIndex = findQuestionIndex(allWordQuestCount); // or any question after
            currentWQ = allWordQuestionList.get(questIndex);
        }

        textViewWordTrainQuestion.setText(currentWQ.getQuestion()); // display question
        rbWordTrain1.setText(currentWQ.getOption1());
        rbWordTrain2.setText(currentWQ.getOption2());
        rbWordTrain3.setText(currentWQ.getOption3());

        wordQuestAnswered = false; // getting things ready for the next question
        bConfirmWordTrain.setText("Confirm");

    }
    //*******************************************

    private int findQuestionIndex(int seedVal){

        Random random = new Random();
        int randomIndex = random.nextInt(seedVal);
        return randomIndex;
    }
    //******************************************************

    private void checkWTAnswer(){

        wordQuestAnswered = true; // question has been answered
        int correctVal = currentWQ.getCorrectVal();

        RadioButton rbSelected = findViewById(radioGroupWordTrain.getCheckedRadioButtonId());
        int ansNumSelect = radioGroupWordTrain.indexOfChild(rbSelected) + 1;


        if (ansNumSelect == currentWQ.getAnswerNr()){ // if answer is correct
            if (correctVal > 0){ // if the user has gotten the question wrong at some point

                correctVal += 1;
                if (correctVal < minRepeats){

                    setQuestionCorrectValue(correctVal);
                    trainWordQuestionList.clear();
                    getWordTrainList();
                    trainWordQuestCount = trainWordQuestionList.size();
                } else { // remove question if the user gets it right twice in a row

                    setQuestionCorrectValue(0);
                    trainWordQuestionList.clear();
                    getWordTrainList();
                    trainWordQuestCount = trainWordQuestionList.size();
                }
            }
        } else { // if wrong choice selected
            if (!isWordQuestInTrainList()){
                correctVal += 1;
                setQuestionCorrectValue(correctVal);
                trainWordQuestionList.clear();
                getWordTrainList();
                trainWordQuestCount = trainWordQuestionList.size();
            }
        }

        showSolutionWT();

    }

    //***********************************************
    private void showSolutionWT(){

        rbWordTrain1.setTextColor(Color.RED);
        rbWordTrain2.setTextColor(Color.RED);
        rbWordTrain3.setTextColor(Color.RED);

        switch (currentWQ.getAnswerNr()){
            case 1:
                rbWordTrain1.setTextColor(DarkGreen);
                rbWordTrain1.setText(currentWQ.getOption1() + " is correct.");
                break;
            case 2:
                rbWordTrain2.setTextColor(DarkGreen);
                rbWordTrain2.setText(currentWQ.getOption2() + " is correct.");
                break;
            case 3:
                rbWordTrain3.setTextColor(DarkGreen);
                rbWordTrain3.setText(currentWQ.getOption3() + " is correct.");
                break;

        }

        bConfirmWordTrain.setText("Next ");
    }

    //******************************************************
    private boolean isWordQuestInTrainList(){
        if (trainWordQuestionList.isEmpty()){
            return false;
        } else {
            for (Question question: trainWordQuestionList){
                if (currentWQ.getQuestionID() == question.getQuestionID()){
                    return true;
                }
            }
            return false;
        }
    }
    //***************************************************

    //*****************************************************

    private void setQuestionCorrectValue(int correctValue){
        for (Question question: allWordQuestionList){
            if (currentWQ.getQuestionID() == question.getQuestionID()){
                question.setCorrectVal(correctValue);
            }
        }
    }

    //*******************************************************


    //***********************************************
    private void getWordTrainList(){
        for (Question question: allWordQuestionList){
            if (question.getCorrectVal() > 0 && question.getCorrectVal() < minRepeats){
                trainWordQuestionList.add(question);
            }
        }
    }

    //*****************************************************





}