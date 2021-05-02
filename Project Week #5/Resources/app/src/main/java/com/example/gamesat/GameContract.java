package com.example.gamesat;

import android.provider.BaseColumns;

/*
 * Here we create the inner structure of each table object, which
 * will be used in the DbHelper.
 */
public final class GameContract {

    private GameContract(){} // private constructor to avoid creating an accidental contract object

    //--------------------------------------------------------------------
    // Word questions table
    public static class WordQuestionsTable implements BaseColumns {

        public static final String TABLE_NAME = "word_questions";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_OPTION1 = "option1";
        public static final String COLUMN_OPTION2 = "option2";
        public static final String COLUMN_OPTION3 = "option3";
        public static final String COLUMN_ANSWER_NR = "answer_nr";
        public static final String COLUMN_LEVEL = "level";
        public static final String COLUMN_CORRECT_VAL = "correct_val";
    }
    //--------------------------------------------------------------------------
    // Passage questions table
    public static class PassageQuestionsTable implements BaseColumns {

        public static final String TABLE_NAME = "passage_questions";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_OPTION1 = "option1";
        public static final String COLUMN_OPTION2 = "option2";
        public static final String COLUMN_OPTION3 = "option3";
        public static final String COLUMN_ANSWER_NR = "answer_nr";
        public static final String COLUMN_LEVEL = "level";
        public static final String COLUMN_CORRECT_VAL = "correct_val";
    }
    //----------------------------------------------------------------------------
    // Word username and completion times table
    public static class UsersWordCompletionTimeTable implements BaseColumns {
        public static final String TABLE_NAME = "word_user_times";
        public static final String COLUMN_USERNAME = "username"; //we want to record usernames and
        public static final String COLUMN_TIME = "time"; // their completion time.
    }
    //-------------------------------------------------------------------------------
    // Passage username and completion times table
    public static class UsersPassageCompletionTimeTable implements BaseColumns {
        public static final String TABLE_NAME = "passage_user_times";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_TIME = "time";
    }
    //------------------------------------------------------------------
    // User login data table
    public static class UserLoginDataTable implements BaseColumns {
        public static final String  TABLE_NAME = "login_data";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_PASSWORD ="password";
    }
    //------------------------------------------------------------

}
