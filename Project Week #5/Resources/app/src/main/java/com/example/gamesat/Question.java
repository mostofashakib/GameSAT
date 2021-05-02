package com.example.gamesat;

/*
 * The inner makeup of a word question object.
 */
public class Question {
    private int questionID = 0;
    private String question;
    private String option1;
    private String option2;
    private String option3;
    private int answerNr;
    private int level;
    private int correctVal;

    public Question(){}

    public Question(String question, String option1, String option2, String option3, int answerNr, int level) {
        this.questionID = 0;
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.answerNr = answerNr;
        this.level = level;
        this.correctVal = 0;
    }

    public int getQuestionID(){ return questionID; }

    public void setQuestionID(int questionID){ this.questionID = questionID; }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public int getAnswerNr() {
        return answerNr;
    }

    public void setAnswerNr(int answerNr) {
        this.answerNr = answerNr;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getCorrectVal(){ return correctVal; }  // will be used during training

    public void setCorrectVal(int correctVal) { this.correctVal = correctVal; }

}
