package com.example.gamesat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.gamesat.GameContract.*;

import java.util.ArrayList;
import java.util.List;

/*
 * Here we created the database and all the needed tables.
 */


public class GameDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "GameSat.db";
    private static final int DATABASE_VERSION = 26; /**  Reset the database num for new tables*/


    public GameDbHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //-------------------------------------------------------------------------
        // create table of login data
        final String SQL_CREATE_LOGIN_DATA_TABLE = "CREATE TABLE IF NOT EXISTS " +
                UserLoginDataTable.TABLE_NAME + " ( " +
                UserLoginDataTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                UserLoginDataTable.COLUMN_USERNAME + " TEXT, " +
                UserLoginDataTable.COLUMN_PASSWORD + " TEXT" +
                " ) ";

        db.execSQL(SQL_CREATE_LOGIN_DATA_TABLE); // create the login data table

        // create a unique index for the table to avoid duplicate username and passwords
        final String SQL_CREATE_LOGIN_DATA_INDEX = "CREATE UNIQUE INDEX IF NOT EXISTS " +
                "login_data_index ON " +  UserLoginDataTable.TABLE_NAME + "(" + UserLoginDataTable.COLUMN_USERNAME
                + "," + UserLoginDataTable.COLUMN_PASSWORD + ")";
        db.execSQL(SQL_CREATE_LOGIN_DATA_INDEX); // we don't want same user, same password to be entered repeatedly


        //----------------------------------------------------------------------

        //------------------------------------------
        // create word questions table

        final String SQL_CREATE_WORD_QUEST_TABLE = "CREATE TABLE IF NOT EXISTS " +
                WordQuestionsTable.TABLE_NAME + " ( " +
                WordQuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WordQuestionsTable.COLUMN_QUESTION + " TEXT, " +
                WordQuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                WordQuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                WordQuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                WordQuestionsTable.COLUMN_ANSWER_NR + " INTEGER, " +
                WordQuestionsTable.COLUMN_LEVEL + " INTEGER, " +
                WordQuestionsTable.COLUMN_CORRECT_VAL + " INTEGER" +
                " ) ";

        db.execSQL(SQL_CREATE_WORD_QUEST_TABLE);

        // create a unique index for the table to avoid duplicate questions
        final String SQL_CREATE_WORD_QUEST_INDEX = "CREATE UNIQUE INDEX IF NOT EXISTS " +
                "word_index ON " +  WordQuestionsTable.TABLE_NAME + "(" + WordQuestionsTable.COLUMN_QUESTION + ")";
        db.execSQL(SQL_CREATE_WORD_QUEST_INDEX);

        //----------------------------------------------------------------------

        //------------------------------------------
        // create passage questions table

        final String SQL_CREATE_PASSAGE_QUEST_TABLE = "CREATE TABLE IF NOT EXISTS " +
                PassageQuestionsTable.TABLE_NAME + " ( " +
                PassageQuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PassageQuestionsTable.COLUMN_QUESTION + " TEXT, " +
                PassageQuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                PassageQuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                PassageQuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                PassageQuestionsTable.COLUMN_ANSWER_NR + " INTEGER, " +
                PassageQuestionsTable.COLUMN_LEVEL + " INTEGER, " +
                PassageQuestionsTable.COLUMN_CORRECT_VAL + " INTEGER" +
                " ) ";

        db.execSQL(SQL_CREATE_PASSAGE_QUEST_TABLE);

        // create a unique index for the table to avoid duplicate questions
        final String SQL_CREATE_PASSAGE_QUEST_INDEX = "CREATE UNIQUE INDEX IF NOT EXISTS " +
                "passage_index ON " +  PassageQuestionsTable.TABLE_NAME + "(" + PassageQuestionsTable.COLUMN_QUESTION + ")";
        db.execSQL(SQL_CREATE_PASSAGE_QUEST_INDEX);

        //----------------------------------------------------------------------

        //------------------------------------------------------------------------------
        // create table of users and their Word Game completion times

        final String SQL_CREATE_WORD_USERNAME_TIME_TABLE = "CREATE TABLE IF NOT EXISTS " +
                UsersWordCompletionTimeTable.TABLE_NAME + " ( " +
                UsersWordCompletionTimeTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                UsersWordCompletionTimeTable.COLUMN_USERNAME + " TEXT, " +
                UsersWordCompletionTimeTable.COLUMN_TIME + " INTEGER" +
                " ) ";
        //*** Time will be stored as an integer and retrieved as a long.

        db.execSQL(SQL_CREATE_WORD_USERNAME_TIME_TABLE); // create the username time table

        // create a unique index for the table to avoid duplicate username and times
        final String SQL_CREATE_WORD_USERNAME_TIME_INDEX = "CREATE UNIQUE INDEX IF NOT EXISTS " +
                "username_time_word_index ON " +  UsersWordCompletionTimeTable.TABLE_NAME + "(" + UsersWordCompletionTimeTable.COLUMN_USERNAME
                + "," + UsersWordCompletionTimeTable.COLUMN_TIME + ")";
        db.execSQL(SQL_CREATE_WORD_USERNAME_TIME_INDEX); // we don't want same user, same time to be entered repeatedly


        //----------------------------------------------------------------------

        //----------------------------------------------------------------------

        // create table of users and their Passage Game completion times

        final String SQL_CREATE_PASSAGE_USERNAME_TIME_TABLE = "CREATE TABLE IF NOT EXISTS " +
                UsersPassageCompletionTimeTable.TABLE_NAME + " ( " +
                UsersPassageCompletionTimeTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                UsersPassageCompletionTimeTable.COLUMN_USERNAME + " TEXT, " +
                UsersPassageCompletionTimeTable.COLUMN_TIME + " INTEGER" +
                " ) ";
        //*** Time will be stored as an integer and retrieved as a long.

        db.execSQL(SQL_CREATE_PASSAGE_USERNAME_TIME_TABLE); // create the username time table for passage game

        // create a unique index for the table to avoid duplicate username and times
        final String SQL_CREATE_PASSAGE_USERNAME_TIME_INDEX = "CREATE UNIQUE INDEX IF NOT EXISTS " +
                "username_time_passage_index ON " +  UsersPassageCompletionTimeTable.TABLE_NAME + "(" + UsersPassageCompletionTimeTable.COLUMN_USERNAME
                + "," + UsersWordCompletionTimeTable.COLUMN_TIME + ")";
        db.execSQL(SQL_CREATE_PASSAGE_USERNAME_TIME_INDEX); // we don't want same user, same time to be entered repeatedly


        //----------------------------------------------------------------------

    }
                            /*********----On--Create----Ends----*****/
    /**********************************************************************************************************/

    //*********************************************************************
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + WordQuestionsTable.TABLE_NAME); // drop word questions table

        db.execSQL("DROP TABLE IF EXISTS " + PassageQuestionsTable.TABLE_NAME); // drop passage questions table

        db.execSQL("DROP TABLE IF EXISTS " + UsersWordCompletionTimeTable.TABLE_NAME); // drop username time Word table

        db.execSQL("DROP TABLE IF EXISTS " + UsersPassageCompletionTimeTable.TABLE_NAME); // drop username time Passage table

        db.execSQL("DROP TABLE IF EXISTS " + UserLoginDataTable.TABLE_NAME); // drop username time table

        onCreate(db);
    }
    //*******************************************************************

    /************************************************************************************************************************/
    public void fillWordQuestTableIfEmpty(){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(WordQuestionsTable.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);
        if (cursor != null && cursor.getCount() > 0){
            return;
        }
        fillWordQuestTable();
    }



    //***********************************************************************************************************
    private void addWordQuestion(String word, String opt1, String opt2, String opt3, int ansNr, int level){

        Question wordQuestion = new Question(word + " is closest in meaning to:", opt1, opt2, opt3, ansNr, level);


        ContentValues cv = new ContentValues();
        cv.put(WordQuestionsTable.COLUMN_QUESTION, wordQuestion.getQuestion());
        cv.put(WordQuestionsTable.COLUMN_OPTION1, wordQuestion.getOption1());
        cv.put(WordQuestionsTable.COLUMN_OPTION2, wordQuestion.getOption2());
        cv.put(WordQuestionsTable.COLUMN_OPTION3, wordQuestion.getOption3());
        cv.put(WordQuestionsTable.COLUMN_ANSWER_NR, wordQuestion.getAnswerNr());
        cv.put(WordQuestionsTable.COLUMN_LEVEL, wordQuestion.getLevel());
        cv.put(WordQuestionsTable.COLUMN_CORRECT_VAL, wordQuestion.getCorrectVal()); // all correctVals are zero as default

        // now we add the question to the database db
        this.getWritableDatabase().insertWithOnConflict(WordQuestionsTable.TABLE_NAME,null, cv,SQLiteDatabase.CONFLICT_IGNORE);

    }

    //************************************************************************************************
    public List<Question> getAllWordQuestionsPerLevel(int level){
        List<Question> wordQuestionList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(WordQuestionsTable.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        if (cursor.moveToFirst()){
            do {
                Question question = new Question();
                question.setQuestionID(cursor.getInt(cursor.getColumnIndex(WordQuestionsTable._ID))); // automatically set by android
                question.setQuestion(cursor.getString(cursor.getColumnIndex(WordQuestionsTable.COLUMN_QUESTION)));
                question.setOption1(cursor.getString(cursor.getColumnIndex(WordQuestionsTable.COLUMN_OPTION1)));
                question.setOption2(cursor.getString(cursor.getColumnIndex(WordQuestionsTable.COLUMN_OPTION2)));
                question.setOption3(cursor.getString(cursor.getColumnIndex(WordQuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(cursor.getInt(cursor.getColumnIndex(WordQuestionsTable.COLUMN_ANSWER_NR)));
                question.setLevel(cursor.getInt(cursor.getColumnIndex(WordQuestionsTable.COLUMN_LEVEL)));
                question.setCorrectVal(cursor.getInt(cursor.getColumnIndex(WordQuestionsTable.COLUMN_CORRECT_VAL)));
                if (cursor.getInt(cursor.getColumnIndex(WordQuestionsTable.COLUMN_LEVEL)) == level){
                    wordQuestionList.add(question);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return wordQuestionList;
    }
    //*********************************************************************************************************************



    //************************************************************************************************
    public List<Question> getAllWordQuestions(){

        List<Question> allWordQuestionList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(WordQuestionsTable.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        if (cursor.moveToFirst()){
            do {
                Question question = new Question();
                question.setQuestionID(cursor.getInt(cursor.getColumnIndex(WordQuestionsTable._ID))); // automatically set by android
                question.setQuestion(cursor.getString(cursor.getColumnIndex(WordQuestionsTable.COLUMN_QUESTION)));
                question.setOption1(cursor.getString(cursor.getColumnIndex(WordQuestionsTable.COLUMN_OPTION1)));
                question.setOption2(cursor.getString(cursor.getColumnIndex(WordQuestionsTable.COLUMN_OPTION2)));
                question.setOption3(cursor.getString(cursor.getColumnIndex(WordQuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(cursor.getInt(cursor.getColumnIndex(WordQuestionsTable.COLUMN_ANSWER_NR)));
                question.setLevel(cursor.getInt(cursor.getColumnIndex(WordQuestionsTable.COLUMN_LEVEL)));
                question.setCorrectVal(cursor.getInt(cursor.getColumnIndex(WordQuestionsTable.COLUMN_CORRECT_VAL)));

                allWordQuestionList.add(question);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return allWordQuestionList;
    }
    //*********************************************************************************************************************



    //*********************************************************************************************************************

    public void updateWordQuestionCorrectVal(int questionID, int correctVal){

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(WordQuestionsTable.COLUMN_CORRECT_VAL, correctVal);

        Cursor cursor = database.query(WordQuestionsTable.TABLE_NAME,
                new String[]{WordQuestionsTable._ID, WordQuestionsTable.COLUMN_CORRECT_VAL},
                WordQuestionsTable._ID + " =?",
                new String[]{String.valueOf(questionID)},
                null,
                null,
                null);
        if (cursor.moveToFirst()){
            if (cursor.getInt(cursor.getColumnIndex(WordQuestionsTable.COLUMN_CORRECT_VAL)) == 0){
                this.getWritableDatabase().updateWithOnConflict(WordQuestionsTable.TABLE_NAME, contentValues,
                        WordQuestionsTable._ID + "=?",
                        new String[]{String.valueOf(questionID)},SQLiteDatabase.CONFLICT_IGNORE);
            }
        }
    }

    //-------------------------------------------------------------------------------------
    public void resetWordQuestionCorrectValues(){

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(WordQuestionsTable.COLUMN_CORRECT_VAL, 0);

        Cursor cursor = database.query(WordQuestionsTable.TABLE_NAME,
                new String[]{WordQuestionsTable.COLUMN_CORRECT_VAL},
                WordQuestionsTable.COLUMN_CORRECT_VAL + " !=?",
                new String[]{String.valueOf(0)},
                null,
                null,
                null);
        if (cursor.moveToFirst()){
            do {
                this.getWritableDatabase().updateWithOnConflict(WordQuestionsTable.TABLE_NAME, contentValues,
                        WordQuestionsTable.COLUMN_CORRECT_VAL+ " !=? ",
                        new String[]{String.valueOf(0)},SQLiteDatabase.CONFLICT_IGNORE);
            } while (cursor.moveToNext());
        }
    }
    //------------------------------------------------------------------------

    /****************************************************************************************************/
    // insert user data into the login database

    //***********************************************************************************************************
    public void insertUserLogin(String username, String password){

        ContentValues cv = new ContentValues();
        cv.put(UserLoginDataTable.COLUMN_USERNAME, username);
        cv.put(UserLoginDataTable.COLUMN_PASSWORD, password);
        // now we add the question to the database db
        this.getWritableDatabase().insertWithOnConflict(UserLoginDataTable.TABLE_NAME,null, cv, SQLiteDatabase.CONFLICT_IGNORE);
    }
    //******************************************
    public void clearAllLoginData(){

        this.getWritableDatabase().delete(UserLoginDataTable.TABLE_NAME, null, null);
    }
    //*****************************************


    //******************************************************************

    public String getUserName(){
        SQLiteDatabase db = this.getReadableDatabase();
        String usernameResult = "";

        Cursor cursor = db.rawQuery("SELECT " + UserLoginDataTable.COLUMN_USERNAME +
                " FROM " + UserLoginDataTable.TABLE_NAME, null);

        if (cursor.moveToFirst()){
            usernameResult = cursor.getString(cursor.getColumnIndex(UserLoginDataTable.COLUMN_USERNAME));
            cursor.close();
            return usernameResult;
        }
        cursor.close();

        return "";
    }
    /***********************************************************************************************************/

    //*****************************************************************************************
    // insert username and time to complete the game
    public void insertUserNameTimeWordTable(String username, Long time){

        ContentValues cv = new ContentValues();
        cv.put(UsersWordCompletionTimeTable.COLUMN_USERNAME, username);
        cv.put(UsersWordCompletionTimeTable.COLUMN_TIME, time);

        // insert if the user doesn't exist, or if they exist and their time is less than current
        if (doesUserExistInWordTable(username)){
            if (isUserWordTableUpdateNecessary(username, time)){
                deleteUserWordTableRecord(username);
                this.getWritableDatabase().insertWithOnConflict(UsersWordCompletionTimeTable.TABLE_NAME,null, cv, SQLiteDatabase.CONFLICT_IGNORE);
            }
        } else { // add users that do not exist
            this.getWritableDatabase().insertWithOnConflict(UsersWordCompletionTimeTable.TABLE_NAME,null, cv, SQLiteDatabase.CONFLICT_IGNORE);
        }

    }

    //******************************************************

    // does user exist
    public boolean doesUserExistInWordTable(String username){

        SQLiteDatabase db = this.getReadableDatabase();

        // check if a user exists and if their time is larger then current
        Cursor cursor = db.query(UsersWordCompletionTimeTable.TABLE_NAME,
                new String[] {UsersWordCompletionTimeTable.COLUMN_USERNAME},
                UsersWordCompletionTimeTable.COLUMN_USERNAME + "=?",
                new String[]{username},
                null,
                null,
                null,
                null
        );

        if (cursor != null && cursor.getCount() > 0){ // only if results exist
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    //**********************************************
    public boolean isUserWordTableUpdateNecessary(String username, Long time){

        SQLiteDatabase db = this.getReadableDatabase();

        // check if a user exists and if their time is larger then current
        Cursor cursor = db.query(UsersWordCompletionTimeTable.TABLE_NAME,
                new String[] {UsersWordCompletionTimeTable.COLUMN_USERNAME},
                UsersWordCompletionTimeTable.COLUMN_USERNAME + "=? AND " +
                UsersWordCompletionTimeTable.COLUMN_TIME + ">?",
                new String[]{username, String.valueOf(time)},
                null,
                null,
                null,
                null
                );

        if (cursor != null && cursor.getCount() > 0){ // only if results exist
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }
    //***********************************************************
    // delete user from table
    private void deleteUserWordTableRecord(String username){

        this.getWritableDatabase().delete(UsersWordCompletionTimeTable.TABLE_NAME,
                UsersWordCompletionTimeTable.COLUMN_USERNAME + " =?",
                new String[]{username});
    }
    //******************************************************
    public void clearUserNameTimeWordTable(){
        this.getWritableDatabase().delete(UsersWordCompletionTimeTable.TABLE_NAME, null, null);
    }
    //**************************************************
    /******************************************************************************************************************************/
    //*******************************************************************

    public void fillPassageQuestTableIfEmpty(){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query(PassageQuestionsTable.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);
        if (cursor != null && cursor.getCount() > 0){
            return;
        }
        fillPassageQuestTable();
    }

    // fill word database with word questions

    public void fillWordQuestTable(){

        /**--------------------- Level 1 -------------------------------------------------*/

        //*-------A----

        addWordQuestion("Aberration", "anomaly", "remote", "desist", 1, 1);

        addWordQuestion("Abreast", "uncertain", "informed", "above", 2, 1);

        addWordQuestion("Abstain", "entice", "refrain", "abhor", 2, 1);

        addWordQuestion("Abyss", "attract", "entice", "void", 3, 1);

        addWordQuestion("Adept", "proficient", "uncertain", "adorn", 1, 1);

        //---------------------------------------------------------------------------------------------------------

        //*----B-----

        addWordQuestion("Blight", "mite", "sophisticate", "damage", 3, 1);

        addWordQuestion("Blithe", "bright", "indifferent", "vivacious", 2, 1);

        addWordQuestion("Bolster", "suffice", "strengthen", "cordial", 2, 1);

        addWordQuestion("Bombastic", "pompous", "cordial", "jabber", 1, 1);

        addWordQuestion("Boycott", "rejection", "beguile", "implosion", 1, 1);

        //--------------------------------------------------------------------------------------------------------------

        //*----C---

        addWordQuestion("Cacophony", "calm", "sinuous", "clamor", 3, 1);

        addWordQuestion("Coda", "cede", "finale", "bard", 2, 1);

        addWordQuestion("Confound", "contradict", "chagrin", "cerebral", 1, 1);

        //--------------------------------------------------------------------------------------------------------------

        //*---D--

        addWordQuestion("Deign", "anodyne", "epithet", "condescend", 3, 1);

        addWordQuestion("Docile", "quandary", "compliant", "cerebral", 2, 1);

        addWordQuestion("Doff", "remove", "goad", "lint", 1, 1);

        //--------------------------------------------------------------------------------------------------------------

        //*---E--

        addWordQuestion("Endow", "egress", "cursory", "equip", 3, 1);

        addWordQuestion("Ephemeral", "eclectic", "fleeting", "cerebral", 2, 1);

        addWordQuestion("Ethos", "disposition", "efficacy", "ebullient", 1, 1);

        //--------------------------------------------------------------------------------------------------------------

        //*---F--

        addWordQuestion("Facetious", "fallible", "cursory", "flippant", 3, 1);

        addWordQuestion("Fallow", "fallible", "dormant", "cerebral", 2, 1);

        //--------------------------------------------------------------------------------------------------------------

        //*---G--

        addWordQuestion("Gist", "glow", "cursory", "quintessence", 3, 1);

        addWordQuestion("Gossamer", "erudite", "delicate", "cerebral", 2, 1);

        //--------------------------------------------------------------------------------------------------------------

        //*---I--

        addWordQuestion("Impetuous", "glow", "cursory", "hasty", 3, 1);

        addWordQuestion("Ingrate", "ungrateful", "inept", "cerebral", 1, 1);

        //--------------------------------------------------------------------------------------------------------------

        //*---L--

        addWordQuestion("Loll", "glow", "cursory", "lounge", 3, 1);

        addWordQuestion("Lurid", "exaggerated", "inept", "lurk", 1, 1);

        //--------------------------------------------------------------------------------------------------------------

        //*---M--

        addWordQuestion("Mar", "glow", "spoil", "mend", 2, 1);

        addWordQuestion("Morose", "sullen", "jettison", "lurk", 1, 1);

        //--------------------------------------------------------------------------------------------------------------

        //*---O--

        addWordQuestion("Oblique", "opus", "spoil", "inexplicit", 3, 1);

        addWordQuestion("Opaque", "obscure", "jettison", "opiate", 1, 1);

        //--------------------------------------------------------------------------------------------------------------

        //*---O--

        addWordQuestion("Placate", "opus", "spoil", "mollify", 3, 1);

        addWordQuestion("Platitude", "obscure", "cliché", "opiate", 2, 1);

        //--------------------------------------------------------------------------------------------------------------

        //*---R--

        addWordQuestion("Reticence", "reverence", "spoil", "introversion", 3, 1);

        addWordQuestion("Rue", "bemoan", "cliché", "opiate", 1, 1);

        //--------------------------------------------------------------------------------------------------------------

        //*---S--

        addWordQuestion("Surly", "reverence", "spoil", "grumpy", 3, 1);

        addWordQuestion("Stigma", "bemoan", "dishonor", "opiate", 2, 1);

        //--------------------------------------------------------------------------------------------------------------

        //*---T--

        addWordQuestion("Tome", "opiate", "spoil", "opus", 3, 1);

        addWordQuestion("Torrid", "bemoan", "difficult", "reverence", 2, 1);

        //--------------------------------------------------------------------------------------------------------------

        //*---V--

        addWordQuestion("Vestige", "opiate", "remnant", "opus", 2, 1);

        addWordQuestion("Vapid", "insipid", "difficult", "reverence", 1, 1);

        //--------------------------------------------------------------------------------------------------------------

        //*---W--

        addWordQuestion("Wry", "opiate", "remnant", "sardonic", 3, 1);

        addWordQuestion("Winsome", "insipid", "difficult", "charming", 3, 1);

        //--------------------------------------------------------------------------------------------------------------

        /**---------------------- Level 2 -------------------------------------------------*/

        //*-------A----

        addWordQuestion("Abasement", "anomaly", "belittlement", "aggravate", 2,2);

        addWordQuestion("Abate", "subside", "adorn", "stray", 1,2);

        addWordQuestion("Accession", "acumen", "combustion", "joining",3,2);

        addWordQuestion("Acerbic", "caustic", "edible", "dullard", 1, 2);

        addWordQuestion("Acolyte", "cryptic", "assistant", "euphoria", 2, 2);

        //-------------------------------------------------------------------------------------------------------------

        //*----B-----

        addWordQuestion("Barrage", "bogus", "edify", "abundance", 3, 2);

        addWordQuestion("Bevy", "cluster", "brave", "evasive", 1, 2);

        addWordQuestion("Boor", "garrulous", "lout", "foster", 2, 2);

        addWordQuestion("Bucolic", "benison", "entreat", "rustic", 3, 2);

        addWordQuestion("Apprise", "notify", "boggle", "rant", 1, 2);

        //--------------------------------------------------------------------------------

        //*----C-----

        addWordQuestion("Canonical", "bogus", "edify", "established", 3, 2);

        addWordQuestion("Capricious", "cluster", "fickle", "evasive", 2, 2);

        //--------------------------------------------------------------------------------

        //*----D-----

        addWordQuestion("Dictum", "maxim", "edify", "established", 1, 2);

        addWordQuestion("Discordant", "cluster", "fickle", "incongruous", 3, 2);

        //--------------------------------------------------------------------------------

        //*----F-----

        addWordQuestion("Foible", "maxim", "peculiarity", "established", 2, 2);

        addWordQuestion("Flout", "defy", "fickle", "incongruous", 1, 2);

        //--------------------------------------------------------------------------------

        //*----G-----

        addWordQuestion("Gall", "audacity", "peculiarity", "established", 1, 2);

        addWordQuestion("Goad", "defy", "fickle", "provoke", 3, 2);

        addWordQuestion("Grandiloquent", "pompous", "fickle", "incongruous", 1,2);

        //--------------------------------------------------------------------------------

        //*----H-----

        addWordQuestion("Hapless", "audacity", "peculiarity", "unlucky", 3, 2);

        addWordQuestion("Homage", "tribute", "fickle", "provoke", 1, 2);

        //--------------------------------------------------------------------------------

        //*----I-----

        addWordQuestion("Immutable", "permanent", "peculiarity", "unlucky", 1, 2);

        addWordQuestion("Inculcate", "tribute", "infuse", "provoke", 2, 2);

        //--------------------------------------------------------------------------------

        //*----L-----

        addWordQuestion("Laconic", "permanent", "peculiarity", "concise", 3, 2);

        addWordQuestion("Largesse", "munificence", "infuse", "provoke", 1, 2);

        //--------------------------------------------------------------------------------

        //*----M-----

        addWordQuestion("Malign", "permanent", "harmful", "concise", 2, 2);

        addWordQuestion("Maudlin", "munificence", "infuse", "emotional", 3, 2);

        //--------------------------------------------------------------------------------

        //*----N-----

        addWordQuestion("Nascent", "permanent", "harmful", "emerging", 3, 2);

        addWordQuestion("Nexus", "union", "infuse", "emotional", 1, 2);

        //--------------------------------------------------------------------------------

        //*----P-----

        addWordQuestion("Opine", "permanent", "suggest", "emerging", 2, 2);

        addWordQuestion("Panache", "style", "infuse", "emotional", 1, 2);

        addWordQuestion("Paragon", "stigma", "infuse", "model", 3, 2);

        //--------------------------------------------------------------------------------

        //*----Q-----

        addWordQuestion("Qualm", "misgiving", "suggest", "emerging", 1, 2);

        addWordQuestion("Quell", "style", "suppress", "emotional", 2, 2);

        addWordQuestion("Quotidian", "ordinary", "infuse", "model", 1, 2);

        //--------------------------------------------------------------------------------

        //*----R-----

        addWordQuestion("Recalcitrant", "misgiving", "uncooperative", "emerging", 2, 2);

        addWordQuestion("Recant", "style", "suppress", "disavow", 3, 2);

        //--------------------------------------------------------------------------------

        //*----S-----

        addWordQuestion("Sardonic", "satirical", "uncooperative", "emerging", 1, 2);

        addWordQuestion("Soliloquy", "monologue", "suppress", "disavow", 1, 2);

        //--------------------------------------------------------------------------------

        //*----T-----

        addWordQuestion("Tenet", "satirical", "precept", "urge", 2, 2);

        addWordQuestion("Tout", "endorse", "suppress", "disavow", 1, 2);

        addWordQuestion("Urbane", "endorse", "suppress", "suave", 3, 2);

        addWordQuestion("Verbose", "endorse", "suppress", "loquacious", 3, 2);

        addWordQuestion("Whet", "stimulate ", "suppress", "suave", 1, 2);

        //--------------------------------------------------------------------------------

        /**---------------------- Level 3 -------------------------------------------------*/

        //*----A-----

        addWordQuestion("Abeyance", "anomaly", "remission", "chisel", 2,3);

        addWordQuestion("Abjure", "reject", "adorn", "stray", 1,3);

        addWordQuestion("Anodyne", "acumen", "combustion", "inoffensive",3,3);

        addWordQuestion("Bilk", "swindle", "gallant", "dullard", 1, 3);

        addWordQuestion("Canard", "cryptic", "gossip", "shard", 2, 3);
        //-------------------------------------------------------------------------------------------------

        //*----C-----

        addWordQuestion("Catalyst", "esoteric", "imbroglio", "stimulus", 3, 3);

        addWordQuestion("Catharsis", "relief", "aplomb", "cabal", 1, 3);

        addWordQuestion("Conscript", "lugubrious", "recruit", "officious", 2, 3);

        addWordQuestion("Cosset", "shunt", "inure", "indulge", 3, 3);

        addWordQuestion("Coterie", "clique", "cater", "denouement", 1, 3);

        //-------------------------------------------------------------------------------------------------------------

        //*----D-----

        addWordQuestion("Dilettante", "dabbler", "imbroglio", "stimulus", 1, 3);

        addWordQuestion("Diurnal", "relief", "daily", "cabal", 2, 3);

        //-------------------------------------------------------------------------------------------------------------

        //*----E-----

        addWordQuestion("Ebullient:", "dabbler", "imbroglio", "joyful", 3, 3);

        addWordQuestion("Edify", "relief", "daily", "instruct", 3, 3);

        addWordQuestion("Egress", "dabbler", "departure", "stimulus", 2, 3);

        addWordQuestion("Ersatz", "substitute", "daily", "superior", 1, 3);

        addWordQuestion("Erstwhile", "dabbler", "former", "stimulus", 2, 3);

        //-------------------------------------------------------------------------------------------------------------

        //*----F-----

        addWordQuestion("Fracas", "dabbler", "departure", "scuffle", 3, 3);

        addWordQuestion("Fusillade", "salvo", "daily", "scuffle", 1, 3);

        //-------------------------------------------------------------------------------------------------------------

        //*----G-----

        addWordQuestion("Gaffe", "blunder", "departure", "scuffle", 1, 3);

        addWordQuestion("Gainsay", "salvo", "daily", "oppose", 3, 3);

        //-------------------------------------------------------------------------------------------------------------

        //*----H-----

        addWordQuestion("Halcyon", "blunder", "departure", "happy", 3, 3);

        addWordQuestion("Hermetic", "salvo", "daily", "airtight", 3, 3);

        addWordQuestion("Heterodox", "heterogeneous", "unorthodox", "daily", 2, 3);

        //-------------------------------------------------------------------------------------------------------------

        //*----I------

        addWordQuestion("Iconoclast", "insidious", "critic", "uniform", 2, 3);

        addWordQuestion("Idyllic", "insidious", "critic", "delightful", 3, 3);

        addWordQuestion("Ignoble", "dishonorable", "critic", "intrepid", 1, 3);

        addWordQuestion("Impugn", "insidious", "imperfect", "dispute", 3, 3);
        //--------------------------------------------------------------------------------------------------
        //*---L-----

        addWordQuestion("Lachrymose", "insidious", "sad", "laconic", 2, 3);

        addWordQuestion("Lacuna", "interval", "imperfect", "laconic", 1, 3);

        addWordQuestion("Lambaste", "lope", "imperfect", "castigate", 3, 3);

        addWordQuestion("Larceny", "insidious", "pilfer", "dispute", 2, 3);

        addWordQuestion("Libertine", "dispute", "pilfer", "freethinker", 3, 3);

        addWordQuestion("Lugubrious", "mournful", "pilfer", "render", 1, 3);

        //--------------------------------------------------------------------------------------------------

        //*---M-------

        addWordQuestion("Maelstrom", "insidious", "clarity", "turbulence", 3, 3);

        addWordQuestion("Malapropism", "interval", "imperfect", "misuse", 3, 3);

        addWordQuestion("Misanthropy", "cynicism", "imperfect", "castigate", 1, 3);

        addWordQuestion("Munificent", "bountiful", "clarity", "turbulence", 1, 3);

        addWordQuestion("Myopic", "interval", "imperfect", "insular", 3, 3);

        //--------------------------------------------------------------------------------------------------

        //*---N-------

        addWordQuestion("Neophyte", "experienced", "imperfect", "novice", 3, 3);

        addWordQuestion("Noisome", "irritating", "imperfect", "insular", 1, 3);

        addWordQuestion("Nostrum", "interval", "prescription", "popular",  2, 3);
        //--------------------------------------------------------------------------------------------------

        //*---P-------

        addWordQuestion("Panoply", "apply", "array", "novice", 2, 3);

        addWordQuestion("Paucity", "indicative", "scarcity", "plausibility", 2, 3);

        addWordQuestion("Pellucid", "comprehensible", "prescription", "cataclysmic",  1, 3);
        //--------------------------------------------------------------------------------------------------

        //*---Q-------

        addWordQuestion("Querulous", "proper", "pettish", "novice", 2, 3);

        addWordQuestion("Quiescence", "indicative", "inactivity", "plausibility", 2, 3);

        addWordQuestion("Quixotic", "comprehensible", "indeterminate", "impractical",  3, 3);
        //--------------------------------------------------------------------------------------------------

        //*---R------

        addWordQuestion("Raconteur", "storyteller", "pettish", "novice", 1, 3);

        addWordQuestion("Redress", "indicative", "reparation", "plausibility", 2, 3);

        addWordQuestion("Ribald", "indecent", "indeterminate", "impractical",  1, 3);
        //--------------------------------------------------------------------------------------------------

        //*---S-----

        addWordQuestion("Sanguine", "storyteller", "pettish", "buoyant", 3, 3);

        addWordQuestion("Scintilla", "soft", "smidgen", "plausibility", 2, 3);

        addWordQuestion("Synoptic", "sporadic", "indeterminate", "succinct",  3, 3);
        //--------------------------------------------------------------------------------------------------

        //*---T-----

        addWordQuestion("Truculent", "jolly", "tremendous", "aggressive", 3, 3);

        addWordQuestion("Turgid", "soft", "modest", "overblown", 3, 3);

        addWordQuestion("Tyro", "neophyte", "indeterminate", "succinct",  1, 3);
        //--------------------------------------------------------------------------------------------------

        //*---T-----

        addWordQuestion("Virulent", "jolly", "healthy", "toxic", 3, 3);

        addWordQuestion("Vitriol", "respectful", "modest", "venom", 3, 3);

        addWordQuestion("Vociferous", "clamorous", "cogent", "succinct",  1, 3);
        //--------------------------------------------------------------------------------------------------

        //*---W----

        addWordQuestion("Welter", "clarity", "confusion", "trepid", 2, 3);

        //--------------------------------------------------------------------------------------------------

    }

    // fill database with passage questions

    public void fillPassageQuestTable(){

        //?---------------------- Level 1 -------------------------------------------------*/

        //*----A-----


        addPassageQuestion("Like the rest of the world, the store is a slight aberration of normal. " +
                "Aberration in the sentence above", "dormant", "remote", "anomaly", 3, 1);

        addPassageQuestion("They indicated that the commissioners would keep abreast of future developments. " +
                "Abreast in the sentence above", "uncertain", "volatile", "informed", 3, 1);

        addPassageQuestion("As a result, they tend to abstain or drink very little. " +
                "Abstain in the sentence above", "entice", "refrain", "abhor", 2, 1);

        addPassageQuestion("This is a huge leap of faith, and I hope it’s not into the abyss. " +
                "Abyss in the sentence above", "attract", "void", "perish", 2, 1);

        addPassageQuestion("They became adept at explaining radiation basics and risks to residents and officials. " +
                "Adept in the sentence above", "proficient", "uncertain", "adorn", 1, 1);

        //------------------------------------------------------------------------------------------------------------

        //*----B-----


        addPassageQuestion("Because of slowing growth and climate-related blights, the forests will become an emissions source. " +
                "Blight in the sentence above", "damage", "mite", "pest", 1, 1);

        addPassageQuestion("Given the timeline that we face, this blithe acceptance of indecision is a road map for catastrophe. " +
                "Blithe in the sentence above", "vivacious", "bright", "indifferent", 3, 1);

        addPassageQuestion("The case against the defendant has been bolstered by the discovery of important new evidence. " +
                "Bolster in the sentence above","elaborate", "forgery", "strengthen", 3, 1);

        addPassageQuestion("He was a showy, bombastic man, with a weakness for fine clothes and other personal adornments. " +
                "Bombastic in the sentence above", "brass", "turgid", "elaborate", 2, 1);

        addPassageQuestion("Pressure groups urged a consumer boycott of clothing brands made using child labour. " +
                "Boycott in the sentence above", "beguile", "rejection", "pilfer", 2, 1);

        //--------------------------------------------------------------------------------------------------------------

        //*----C-----


        addPassageQuestion("The cacophony of voices faded to a dull hum as she began to read what she’d written. " +
                "Cacophony in the sentence above", "clamor", "calm", "sordid", 1, 1);

        addPassageQuestion("A short introduction and a coda are added, and the diction throughout is thrown into high relief. " +
                "Coda in the sentence above", "cede", "finale", "divulge", 2, 1);

        addPassageQuestion("The U.S. researchers acknowledge some confounding factors in the study. " +
                "Confound in the sentence above", "create", "evince", "contradict", 3, 1);

        //--------------------------------------------------------------------------------------------------------------

        //*----D----

        addPassageQuestion("If the Senate deigns to consider and confirm a nominee, do not expect changes overnight. " +
                "Deign in the sentence above", "condescend", "anodyne", "epithet", 1, 1);

        addPassageQuestion("Docile with humans, they are fierce defenders of territory and their young. " +
                "Docile in the sentence above", "dissent", "compliant", "contradict", 2, 1);

        addPassageQuestion("To don shoes, to doff them, or even to throw them at somebody? " +
                "Doff in the sentence above", "dolt", "evince", "remove", 3, 1);

        //--------------------------------------------------------------------------------------------------------------

        //*----E----

        addPassageQuestion("Good and inspiring teachers are portrayed as endowed with supernatural gifts. " +
                "Endow in the sentence above", "equip", "egress", "epithet", 1, 1);

        addPassageQuestion("The mayfly is an ephemeral creature: its adult life lasts little more than a day. " +
                "Ephemeral in the sentence above", "eclectic", "fleeting", "contradict", 2, 1);

        addPassageQuestion("They run crooked gambling, so the law under their local ethos must be that crooked gambling is the norm. " +
                "Ethos in the sentence above", "efficacy", "evince", "disposition", 3, 1);

        //--------------------------------------------------------------------------------------------------------------

        //*----F----

        addPassageQuestion("Instead, the crowd began sarcastically to cheer him on and showered him with facetious advice. " +
                "Facetious in the sentence above", "fallible", "humorous", "epithet", 2, 1);

        addPassageQuestion("Farmers have learned that it is advisable to permit land to lie fallow every few years. " +
                "Fallow in the sentence above", "uncultivated", "fleeting", "contradict", 1, 1);


        //--------------------------------------------------------------------------------------------------------------

        //*----G----

        addPassageQuestion("Machine translation, too, has gone from terrible to usable for getting the gist of a text. " +
                "Gist in the sentence above", "glib", "quintessence", "epithet", 2, 1);

        addPassageQuestion("They would laugh in gossamer tones, and then move on gracefully to someone else. " +
                "Gossamer in the sentence above", "uncultivated", "fleeting", "delicate", 3, 1);


        //--------------------------------------------------------------------------------------------------------------

        //*----I----

        addPassageQuestion("It was a stultifying procession of patriotic songs insipid skits and bald propaganda. " +
                "Insipid in the sentence above", "glib", "vapid", "epithet", 2, 1);

        addPassageQuestion("If you mean I am an ingrate, that is an unpleasant word, Aunt Mary. " +
                "Ingrate in the sentence above", "ungrateful", "inept", "delicate", 1, 1);


        //--------------------------------------------------------------------------------------------------------------

        //*----L----

        addPassageQuestion("They loll around in their chairs watching television. " +
                "Loll in the sentence above", "glib", "lounge", "laud", 2, 1);

        addPassageQuestion("Their absence from the public eye, tends to spark unbelievable lurid rumours. " +
                "Lurid in the sentence above", "incline", "inept", "melodramatic", 3, 1);


        //--------------------------------------------------------------------------------------------------------------

        //*----M----

        addPassageQuestion("I barely recognized him, convulsed with mirth, breathing hard, his face bright pink. " +
                "Mirth in the sentence above", "merriment", "lounge", "laud", 1, 1);

        addPassageQuestion("Feeling morose is difficult while actively wishing a person to be happy. " +
                "Morose in the sentence above", "incline", "missive", "sulky", 3, 1);


        //--------------------------------------------------------------------------------------------------------------

        //*----O----

        addPassageQuestion("She made prodigious strides as a writer and learned to temper her overwrought outpourings. " +
                "Overwrought in the sentence above", "merriment", "exaggerated", "laud", 2, 1);

        addPassageQuestion("Fire at Sea' has been praised for offering an oblique alternative to a more conventional documentary. " +
                "Oblique in the sentence above", "incline", "missive", "indirect", 3, 1);


        //--------------------------------------------------------------------------------------------------------------

        //*----P----

        addPassageQuestion("The government has tried to placate voters without abandoning its policies. " +
                "Placate in the sentence above", "appease", "exaggerated", "laud", 1, 1);

        addPassageQuestion("She wore an aquiline scowl, quibbling with the questions and, when pushed, cleaving to evasive platitudes. " +
                "Platitude in the sentence above", "incline", "missive", "truism", 3, 1);


        //--------------------------------------------------------------------------------------------------------------

        //*----R----

        addPassageQuestion("Unable to digest quickly the baffling events of the day, he ruminated about them till four in the morning. " +
                "Ruminate in the sentence above", "ponder", "exaggerated", "laud", 1, 1);

        addPassageQuestion("It commanded its followers to be reticent, to never degrade intimate emotions by parading them in public. " +
                "Reticence in the sentence above", "incline", "reserved", "truism", 2, 1);


        //--------------------------------------------------------------------------------------------------------------

        //*----S----

        addPassageQuestion("Yet life in the ocean can still mount sublime spectacles. " +
                "Sublime in the sentence above", "awesome", "exaggerated", "retort", 1, 1);

        addPassageQuestion("Poverty and economic decline has led to the surly separation of a left-behind, resentful working class. " +
                "Surly in the sentence above", "incline", "reserved", "glum", 3, 1);


        //--------------------------------------------------------------------------------------------------------------

        //*----T----

        addPassageQuestion("The pound, after a few torrid days of trading immediately after the vote, has stabilized. " +
                "Torrid in the sentence above", "tribulation", "trap", "retort", 1, 1);

        addPassageQuestion("It is a tome to which most recent arguments about regulation and economic reform are merely annotations. " +
                "Tome in the sentence above", "reserved", "book", "glum", 2, 1);

        //--------------------------------------------------------------------------------------------------------------

        //*----V----

        addPassageQuestion("Mr. Silver delighted in savaging commentators who relied on vapid cliches like momentum-shifts. " +
                "Vapid: in the sentence above", "insipid", "glum", "retort", 1, 1);

        addPassageQuestion("He said this would remove a lingering vestige of the cold war. " +
                "Vestige in the sentence above", "reserved", "book", "remnant", 3, 1);

        //--------------------------------------------------------------------------------------------------------------

        //?---------------------- Level 2 -------------------------------------------------*/

        //*-----A----

        addPassageQuestion("Not a trace of humiliation or abasement was to be seen in the Duke's countenance or demeanor. " +
                "Abasement in the sentence above", "sobriety", "belittlement", "aggravate", 2,2);

        addPassageQuestion("The sky remained dark around us, but the clouds of dust abated somewhat. " +
                "Abate in the sentence above", "subside", "adorn", "stray", 1,2);

        addPassageQuestion("Each State will declare, at the time of its accession, in which of the said classes it desires to be placed. " +
                "Accession in the sentence above", "acumen", "combustion", "joining",3,2);

        addPassageQuestion("But sometimes, one is taken over by a dark mood, an unusually acerbic wit, strange and vitrified. " +
                "Acerbic in the sentence above", "caustic", "edible", "dullard", 1, 2);

        addPassageQuestion("The priest, his acolytes, the director and I all went outside. " +
                "Acolyte in the sentence above", "cryptic", "assistant", "euphoria", 2, 2);

        //-------------------------------------------------------------------------------------------------------

        //*----B-------

        addPassageQuestion("They heard the stomping from inside their apartment and the barrage of clicks and bangs from locks being disengaged. " +
                "Barrage in the sentence above", "arcane", "refine", "profusion", 3, 2);

        addPassageQuestion("Then a bevy of laughing tourists passed between and separated them. " +
                "Bevy in the sentence above", "cluster", "brave", "concur", 1, 2);

        addPassageQuestion("End a sentence in a preposition, and there are still people who will think you a boor. " +
                "Boor in the sentence above", "bright", "lout", "derelict", 2, 2);

        addPassageQuestion("We dream of bucolic scenes in grey, a grey stream, a grey tree, grey boulders. " +
                "Bucolic in the sentence above", "bane", "prudish", "rustic", 3, 2);

        addPassageQuestion("Secret information is often useful in apprising countries of the intentions of others. " +
                "Apprise in the sentence above", "notify", "stigmatize", "contrite", 1, 2);

        //--------------------------------------------------------------------------------

        //*----C-------

        addPassageQuestion("For one thing, voters can be capricious which causes elections to often misfire. " +
                "Capricious in the sentence above", "arcane", "refine", "fickle", 3, 2);

        addPassageQuestion("This is an area where we need to be extraordinarily careful and circumspect. " +
                "Circumspect in the sentence above", "cautious", "brave", "concur", 1, 2);

        //--------------------------------------------------------------------------------

        //*----D-------

        addPassageQuestion("Critics—so far a minority—deride him for posing incessantly with fans and celebrities. " +
                "Deride in the sentence above", "ridicule", "refine", "fickle", 1, 2);

        addPassageQuestion("Sometimes the old army dictum 'Don’t volunteer for anything' must be broken. " +
                "Dictum in the sentence above", "cautious", "maxim", "concur", 2, 2);

        //--------------------------------------------------------------------------------

        //*----E------

        addPassageQuestion("He was called upon to elucidate the disputed points in his article. " +
                "Elucidate in the sentence above", "arcane", "clarify", "fickle", 2, 2);

        addPassageQuestion("In his speech, the president will extol the astronauts, calling them the pioneers of the Space Age. " +
                "Extol in the sentence above", "cautious", "praise", "concur", 2, 2);

        //--------------------------------------------------------------------------------

        //*----F-------

        addPassageQuestion("The fetid smog that settled could join the ranks of these game-changing environmental disruptions. " +
                "Fetid in the sentence above", "stinking", "refine", "fickle", 1, 2);

        addPassageQuestion("You should overlook his foible; no one is perfect. " +
                "Foible in the sentence above", "cautious", "brave", "failing", 3, 2);

        //--------------------------------------------------------------------------------

        //*---G-------

        addPassageQuestion("Sometimes, gall is so shameless it's turned into an art form. " +
                "Gall in the sentence above", "validity", "refine", "vexation", 3, 2);

        addPassageQuestion("Perhaps they were waiting for some event to once again galvanize people into marching. " +
                "Galvanize in the sentence above", "cautious", "stagger", "stimulate", 3, 2);

        //--------------------------------------------------------------------------------

        //*----I------

        addPassageQuestion("A vacuous speaker draws big crowds and media and doesn't imbue them with substance. " +
                "Imbue in the sentence above", "fill", "refine", "instigate", 1, 2);

        addPassageQuestion("The ceremonies were to start inculcating a sense of common values that had previously been lacking. " +
                "Inculcate in the sentence above", "cautious", "remove", "infuse", 3, 2);

        //--------------------------------------------------------------------------------

        //*----L------

        addPassageQuestion("The past two decades have left working-class voters leery of globalization. " +
                "Leery in the sentence above", "careful", "refine", "careless", 1, 2);

        addPassageQuestion("This is an area where we need to be extraordinarily careful and circumspect. " +
                "Circumspect in the sentence above", "circumspect", "brave", "concur", 1, 2);

        //--------------------------------------------------------------------------------

        //*----M------

        addPassageQuestion("Putting her hands over her ears, Rose refused to listen to Betty malign her friend. " +
                "Malign in the sentence above", "defame", "drastic", "fickle", 1, 2);

        addPassageQuestion("With these modish demonstrations becoming the norm, what exactly do they accomplish? " +
                "Modish in the sentence above", "trendy", "baseless", "conflate", 1, 2);

        //--------------------------------------------------------------------------------

        //*----N------

        addPassageQuestion("Weakening the legislature in a nascent democracy will not fix corruption by itself. " +
                "Nascent in the sentence above", "emerging", "experienced", "fickle", 1, 2);

        addPassageQuestion("The internet commenter seemed nonplussed by Oxford validating teenage slang. " +
                "Nonplussed in the sentence above", "cautious", "confounded", "clear", 2, 2);

        //--------------------------------------------------------------------------------

        //*----P------

        addPassageQuestion("They posit a ratio of expected benefits to costs of 1.4 for every project. " +
                "Posit in the sentence above", "prepare", "refine", "postulate", 3, 2);

        addPassageQuestion("Academics avoid brevity, and physics does not lend itself to pithy introductions. " +
                "Pithy in the sentence above", "cautious", "concise", "concur", 2, 2);

        //--------------------------------------------------------------------------------

        //*----Q-----

        addPassageQuestion("Qualms about the force’s quality extend beyond their handling of demonstrators. " +
                "Qualm in the sentence above", "cautious", "doubt", "concur", 2, 2);

        addPassageQuestion("They are mystics unfettered by the quotidian, callous to mundane tasks. " +
                "Quotidian in the sentence above", "exotic", "doubt", "ordinary", 3, 2);

        //--------------------------------------------------------------------------------

        //*----S------

        addPassageQuestion("In trade negotiations larger economies can stipulate terms that suit them. " +
                "Stipulate in the sentence above", "demand", "refine", "defame", 1, 2);

        addPassageQuestion("Exalting aviation security to a higher stratum than it deserves is a delusion. " +
                "Stratum in the sentence above", "cautious", "echelon", "laconic", 2, 2);

        //--------------------------------------------------------------------------------

        //?---------------------- Level 3 -------------------------------------------------*/

        //*------A----

        addPassageQuestion("The scientific mood dominated them, the artistic and practical moods were in abeyance. " +
                "Abeyance in the sentence above", "anomaly", "remission", "chisel", 2,3);

        addPassageQuestion("In words they may pretend to abjure their empire: but in reality they will remain subject to it all the while. " +
                "Abjure in the sentence above", "reject", "adorn", "stray", 1,3);

        addPassageQuestion("Despite its anodyne title the book contained shocking revelations. " +
                "Anodyne in the sentence above", "acumen", "combustion", "inoffensive",3,3);

        addPassageQuestion("Authorities said they were part of a conspiracy to bilk owners of time-share properties. " +
                "Bilk in the sentence above", "swindle", "gallant", "dullard", 1, 3);

        addPassageQuestion("Elephants will be admitted, too, on account of the unjust canard concerning their fear of mice." +
                "Canard in the sentence above", "cryptic", "rumor", "shard", 2, 3);

        //--------------------------------------------------------------------------------

        //*---------C-------

        addPassageQuestion("An outside leader is often needed to serve as a catalyst for change. " +
                "Catalyst in the sentence above", "fervid", "vestige", "stimulus", 3, 3);

        addPassageQuestion("The blood pressure is lowered by such catharsis, and the heart is often slowed. " +
                "Catharsis in the sentence above", "relief", "obsequious", "spurious", 1,3);

        addPassageQuestion("She was successfully conscripted into the team. " +
                "Conscript in the sentence above", "chary", "recruit", "dulcet", 2, 3);

        addPassageQuestion("A luxury spa where guests are cosseted with no expense spared. " +
                "Cosset in the sentence above", "shunt", "sordid", "indulge", 3, 3);

        //--------------------------------------------------------------------------------

        //--------------------------------------------------------------------------------

        //*----D-------

        addPassageQuestion("This is not the work of a dilettante, but a strong follow-up to her acclaimed short stories. " +
                "Dilettante in the sentence above", "dabbler", "refine", "fickle", 1, 3);

        addPassageQuestion("The best properties could be sold quickly, but the dross might take years to offload. " +
                "Dross in the sentence above", "cautious", "rubbish", "priceless", 2, 3);

        //--------------------------------------------------------------------------------

        //*----E------

        addPassageQuestion("The earliest extant painting dates to 1825 and shows him with vivid eyes and thin, sculpted lips. " +
                "Extant in the sentence above", "arcane", "living", "fickle", 2, 3);

        addPassageQuestion("Creating an ersatz version of a software is more costly than using the original. " +
                "Ersatz in the sentence above", "cautious", "support", "substitute", 3, 3);

        //--------------------------------------------------------------------------------

        //*----F-------

        addPassageQuestion("The video was taken off the website, but it had already caused a diplomatic fracas. " +
                "Fracas in the sentence above", "scuffle", "refine", "support", 1, 3);

        addPassageQuestion("Thanks to poor communication, many saw it as a first fusillade in a global currency war. " +
                "Fusillade in the sentence above", "cautious", "salvo", "belligerent", 2, 3);

        //--------------------------------------------------------------------------------

        //*---G-------

        addPassageQuestion("He is also gaffe-prone and the progenitor of a series of undiplomatic comments. " +
                "Gaffe in the sentence above", "validity", "refine", "blunder", 3, 3);

        addPassageQuestion("He was too young to know better, let alone gainsay his wicked uncle. " +
                "Gainsay in the sentence above", "oppose", "stagger", "stimulate", 1, 3);

        //--------------------------------------------------------------------------------

        //*----I------

        addPassageQuestion("They forgave the scriptwriters for the unwelcome disruption to their rural idyll. " +
                "Idyll in the sentence above", "fill", "refine", "honeymoon", 3, 3);

        addPassageQuestion("He controlled the equally unruly mind, keeping it pure from ignoble strife. " +
                "Ignoble in the sentence above", "unworthy", "important", "infuse", 1, 3);

        //--------------------------------------------------------------------------------

        //*----L------

        addPassageQuestion("Yet the president was lambasted for his otherworldly complacency. " +
                "Lambaste in the sentence above", "castigate", "commend", "careless", 1, 3);

        addPassageQuestion("There are aunts for every worldview, from libertine to puritan and from reactionary to radical. " +
                "Libertine in the sentence above", "circumspect", "freethinker", "concur", 2, 3);

        //--------------------------------------------------------------------------------

        //*----M------

        addPassageQuestion("The awards for physics, were followed by equally munificent prizes in mathematics. " +
                "Munificent in the sentence above", "defame", "drastic", "lavish", 3, 3);

        addPassageQuestion("They are also myopic, judging economic management only on the very recent past. " +
                "Myopic in the sentence above", "trendy", "baseless", "insular", 3, 3);

        //--------------------------------------------------------------------------------

        //*----N------

        addPassageQuestion("This will help both jazz neophytes and experts understand what they are listening to. " +
                "Neophyte in the sentence above", "emerging", "experienced", "novice", 3, 3);

        addPassageQuestion("The noisome atmosphere not only stank, it damaged the lungs of everyone living in the area. " +
                "Noisome in the sentence above", "cautious", "harmful", "helpful", 2, 3);

        //--------------------------------------------------------------------------------

        //*----P------

        addPassageQuestion("The paucity of businesses is not due to a shortage of opportunities to make money. " +
                "Paucity in the sentence above", "plenty", "scarcity", "postulate", 2, 3);

        addPassageQuestion("She showed her pique at her loss by refusing to appear with the other contestants. " +
                "Pique in the sentence above", "displeasure", "agreement", "support", 1, 3);

        //--------------------------------------------------------------------------------

        //*----Q-----

        addPassageQuestion("Their querulous, hostile or annoyed faces recur in her work from the late 1950s. " +
                "Querulous in the sentence above", "joyful", "doubt", "pettish", 3, 3);

        addPassageQuestion("The director interviews the quixotic visionaries driving the digital revolution forward. " +
                "Quixotic in the sentence above", "exotic", "unrealistic", "ordinary", 2, 3);

        //--------------------------------------------------------------------------------

        //*----S------

        addPassageQuestion("Some fear a future of mass unemployment, while others are sanguine that people will adapt. " +
                "Sanguine in the sentence above", "hopeful", "refine", "defame", 1, 3);

        addPassageQuestion("After a scintilla of regret over lost youth, to turn 50 should be to enter the prime of life. " +
                "Scintilla in the sentence above", "cautious", "echelon", "smidgen", 3, 3);

        //--------------------------------------------------------------------------------

    }

    //***********************************************************************************************************
    private void addPassageQuestion(String word, String opt1, String opt2, String opt3, int ansNr, int level){

        Question passageQuestion = new Question(word + " is closest in meaning to:", opt1, opt2, opt3, ansNr, level);


        ContentValues cv = new ContentValues();
        cv.put(PassageQuestionsTable.COLUMN_QUESTION, passageQuestion.getQuestion());
        cv.put(PassageQuestionsTable.COLUMN_OPTION1, passageQuestion.getOption1());
        cv.put(PassageQuestionsTable.COLUMN_OPTION2, passageQuestion.getOption2());
        cv.put(PassageQuestionsTable.COLUMN_OPTION3, passageQuestion.getOption3());
        cv.put(PassageQuestionsTable.COLUMN_ANSWER_NR, passageQuestion.getAnswerNr());
        cv.put(PassageQuestionsTable.COLUMN_LEVEL, passageQuestion.getLevel());
        cv.put(PassageQuestionsTable.COLUMN_CORRECT_VAL, passageQuestion.getCorrectVal()); // all correctVals are zero as default

        // now we add the question to the database db
        this.getWritableDatabase().insertWithOnConflict(PassageQuestionsTable.TABLE_NAME,null, cv,SQLiteDatabase.CONFLICT_IGNORE);

    }

    //-------------------------------------------------------------------------------------
    public void resetPassageQuestionCorrectValues(){

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PassageQuestionsTable.COLUMN_CORRECT_VAL, 0);

        Cursor cursor = database.query(PassageQuestionsTable.TABLE_NAME,
                new String[]{PassageQuestionsTable.COLUMN_CORRECT_VAL},
                PassageQuestionsTable.COLUMN_CORRECT_VAL + " !=?",
                new String[]{String.valueOf(0)},
                null,
                null,
                null);
        if (cursor.moveToFirst()){
            do {
                this.getWritableDatabase().updateWithOnConflict(PassageQuestionsTable.TABLE_NAME, contentValues,
                        PassageQuestionsTable.COLUMN_CORRECT_VAL+ " !=? ",
                        new String[]{String.valueOf(0)},SQLiteDatabase.CONFLICT_IGNORE);
            } while (cursor.moveToNext());
        }
    }
    //------------------------------------------------------------------------

    //*********************************************************************************************************************

    public void updatePassageQuestionCorrectVal(int questionID, int correctVal){

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PassageQuestionsTable.COLUMN_CORRECT_VAL, correctVal);

        Cursor cursor = database.query(PassageQuestionsTable.TABLE_NAME,
                new String[]{PassageQuestionsTable._ID, PassageQuestionsTable.COLUMN_CORRECT_VAL},
                PassageQuestionsTable._ID + " =?",
                new String[]{String.valueOf(questionID)},
                null,
                null,
                null);
        if (cursor.moveToFirst()){
            if (cursor.getInt(cursor.getColumnIndex(PassageQuestionsTable.COLUMN_CORRECT_VAL)) == 0){
                this.getWritableDatabase().updateWithOnConflict(PassageQuestionsTable.TABLE_NAME, contentValues,
                        PassageQuestionsTable._ID + "=?",
                        new String[]{String.valueOf(questionID)},SQLiteDatabase.CONFLICT_IGNORE);
            }
        }
    }

    //-------------------------------------------------------------------------------------


    //************************************************************************************************
    public List<Question> getAllPassageQuestionsPerLevel(int level){
        List<Question> passageQuestionList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(PassageQuestionsTable.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        if (cursor.moveToFirst()){
            do {
                Question question = new Question();
                question.setQuestionID(cursor.getInt(cursor.getColumnIndex(PassageQuestionsTable._ID))); // automatically set by android
                question.setQuestion(cursor.getString(cursor.getColumnIndex(PassageQuestionsTable.COLUMN_QUESTION)));
                question.setOption1(cursor.getString(cursor.getColumnIndex(PassageQuestionsTable.COLUMN_OPTION1)));
                question.setOption2(cursor.getString(cursor.getColumnIndex(PassageQuestionsTable.COLUMN_OPTION2)));
                question.setOption3(cursor.getString(cursor.getColumnIndex(PassageQuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(cursor.getInt(cursor.getColumnIndex(PassageQuestionsTable.COLUMN_ANSWER_NR)));
                question.setLevel(cursor.getInt(cursor.getColumnIndex(PassageQuestionsTable.COLUMN_LEVEL)));
                question.setCorrectVal(cursor.getInt(cursor.getColumnIndex(PassageQuestionsTable.COLUMN_CORRECT_VAL)));
                if (cursor.getInt(cursor.getColumnIndex(PassageQuestionsTable.COLUMN_LEVEL)) == level){
                    passageQuestionList.add(question);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return passageQuestionList;
    }
    //*********************************************************************************************************************
    //************************************************************************************************
    public List<Question> getAllPassageQuestions(){

        List<Question> allPassageQuestionList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(PassageQuestionsTable.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);

        if (cursor.moveToFirst()){
            do {
                Question question = new Question();
                question.setQuestionID(cursor.getInt(cursor.getColumnIndex(PassageQuestionsTable._ID))); // automatically set by android
                question.setQuestion(cursor.getString(cursor.getColumnIndex(PassageQuestionsTable.COLUMN_QUESTION)));
                question.setOption1(cursor.getString(cursor.getColumnIndex(PassageQuestionsTable.COLUMN_OPTION1)));
                question.setOption2(cursor.getString(cursor.getColumnIndex(PassageQuestionsTable.COLUMN_OPTION2)));
                question.setOption3(cursor.getString(cursor.getColumnIndex(PassageQuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(cursor.getInt(cursor.getColumnIndex(PassageQuestionsTable.COLUMN_ANSWER_NR)));
                question.setLevel(cursor.getInt(cursor.getColumnIndex(PassageQuestionsTable.COLUMN_LEVEL)));
                question.setCorrectVal(cursor.getInt(cursor.getColumnIndex(PassageQuestionsTable.COLUMN_CORRECT_VAL)));

                allPassageQuestionList.add(question);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return allPassageQuestionList;
    }
    //*********************************************************************************************************************

    public void insertUserNameTimePassageTable(String username, Long time){

        ContentValues cv = new ContentValues();
        cv.put(UsersPassageCompletionTimeTable.COLUMN_USERNAME, username);
        cv.put(UsersPassageCompletionTimeTable.COLUMN_TIME, time);

        // insert if the user doesn't exist, or if they exist and their time is less than current
        if (doesUserExistInPassageTable(username)){
            if (isUserPassageTableUpdateNecessary(username, time)){
                deleteUserPassageTableRecord(username);
                this.getWritableDatabase().insertWithOnConflict(UsersPassageCompletionTimeTable.TABLE_NAME,null, cv, SQLiteDatabase.CONFLICT_IGNORE);
            }
        } else { // add users that do not exist
            this.getWritableDatabase().insertWithOnConflict(UsersPassageCompletionTimeTable.TABLE_NAME,null, cv, SQLiteDatabase.CONFLICT_IGNORE);
        }

    }

    //******************************************************

    // does user exist
    public boolean doesUserExistInPassageTable(String username){

        SQLiteDatabase db = this.getReadableDatabase();

        // check if a user exists and if their time is larger then current
        Cursor cursor = db.query(UsersPassageCompletionTimeTable.TABLE_NAME,
                new String[] {UsersPassageCompletionTimeTable.COLUMN_USERNAME},
                UsersPassageCompletionTimeTable.COLUMN_USERNAME + "=?",
                new String[]{username},
                null,
                null,
                null,
                null
        );

        if (cursor != null && cursor.getCount() > 0){ // only if results exist
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    //**********************************************
    public boolean isUserPassageTableUpdateNecessary(String username, Long time){

        SQLiteDatabase db = this.getReadableDatabase();

        // check if a user exists and if their time is larger then current
        Cursor cursor = db.query(UsersPassageCompletionTimeTable.TABLE_NAME,
                new String[] {UsersPassageCompletionTimeTable.COLUMN_USERNAME},
                UsersPassageCompletionTimeTable.COLUMN_USERNAME + "=? AND " +
                        UsersPassageCompletionTimeTable.COLUMN_TIME + ">?",
                new String[]{username, String.valueOf(time)},
                null,
                null,
                null,
                null
        );

        if (cursor != null && cursor.getCount() > 0){ // only if results exist
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }
    //***********************************************************
    // delete user from table
    private void deleteUserPassageTableRecord(String username){

        this.getWritableDatabase().delete(UsersPassageCompletionTimeTable.TABLE_NAME,
                UsersPassageCompletionTimeTable.COLUMN_USERNAME + " =?",
                new String[]{username});
    }
    //******************************************************
    public void clearUserNameTimePassageTable(){
        this.getWritableDatabase().delete(UsersPassageCompletionTimeTable.TABLE_NAME, null, null);
    }

    //************************************************************************************************




}
