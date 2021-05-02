package com.example.gamesat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.gamesat.GameContract.*;

public class LeaderBoard extends AppCompatActivity {

    Button buttonExitLeaderBoard, buttonBackLeaderBoard;
    private int wBoardSelect;
    GameDbHelper gameDbHelper;
    SQLiteDatabase database;
    private GameAdapter gameAdapter;
    private int displayCount = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);

        buttonExitLeaderBoard = findViewById(R.id.buttonExitLeaderBoard);
        buttonBackLeaderBoard = findViewById(R.id.buttonBackLeaderBoard);

        wBoardSelect = getIntent().getIntExtra("wordBoardSelect", 0);

        gameDbHelper = new GameDbHelper(this);
        database = gameDbHelper.getReadableDatabase(); // get readable database

        RecyclerView recyclerViewList = findViewById(R.id.recyclerViewList);
        recyclerViewList.setLayoutManager(new LinearLayoutManager(this));


        if (wBoardSelect > 0){ // adjust the right cursor based on which leader board is selected
            gameAdapter = new GameAdapter(this, getAllNameTimeWord(), wBoardSelect);
        } else {
            gameAdapter = new GameAdapter(this, getAllNameTimePassage(), wBoardSelect);
        }

        recyclerViewList.setAdapter(gameAdapter);

        //-------------------------------------------------------

        buttonBackLeaderBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PlayScreen.class); // go back where I came from
                startActivity(intent);
                finish();
            }
        });

        //----------------------------------------------------------

        buttonExitLeaderBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
                System.exit(0);
            }
        });

        //----------------------------------------------------------

    }

    //****************************************
    private Cursor getAllNameTimeWord(){
        final String SQL_USERNAME_TIME_WORD = "SELECT " +
                UsersWordCompletionTimeTable.COLUMN_USERNAME + ", " +
                UsersWordCompletionTimeTable.COLUMN_TIME +
                " FROM " + UsersWordCompletionTimeTable.TABLE_NAME +
                " ORDER BY " + UsersWordCompletionTimeTable.COLUMN_TIME +
                " LIMIT " + displayCount;

        return database.rawQuery(SQL_USERNAME_TIME_WORD,null);
    }
    //******************************************

    //****************************************
    private Cursor getAllNameTimePassage(){
        final String SQL_USERNAME_TIME_Passage = "SELECT " +
                UsersPassageCompletionTimeTable.COLUMN_USERNAME + ", " +
                UsersPassageCompletionTimeTable.COLUMN_TIME +
                " FROM " + UsersPassageCompletionTimeTable.TABLE_NAME +
                " ORDER BY " + UsersPassageCompletionTimeTable.COLUMN_TIME +
                " LIMIT " + displayCount;

        return database.rawQuery(SQL_USERNAME_TIME_Passage,null);
    }
    //******************************************


}