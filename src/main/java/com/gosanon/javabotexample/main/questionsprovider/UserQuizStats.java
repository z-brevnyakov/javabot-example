package com.gosanon.javabotexample.main.questionsprovider;

public class UserQuizStats {
    public int questionNumber;
    public int answeredQuestionsNumber = 0;
    public int correctAnswerNumber = 0;
    public int score = 0;
    public Question currentQuestion;

    public UserQuizStats(int questionNumber){
        this.questionNumber = questionNumber;
    }
}
