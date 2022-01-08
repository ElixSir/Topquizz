package com.example.topquizz.model;

import android.util.Log;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class QuestionBank {

    private List<Question> mQuestionList;
    private int mQuestionIndex;
    private QuestionBank mQuestionBank;

    public QuestionBank(List<Question> questionList) {
        //shuffle the question list before storing it
        Collections.shuffle(questionList);
        mQuestionList = questionList;
    }

    public Question getCurrentQuestion()
    {
        Log.d("indexQuestion",String.valueOf(mQuestionIndex));
        Log.d("Question showed", mQuestionList.get(mQuestionIndex).toString());
        return mQuestionList.get(mQuestionIndex);

    }
    public Question getNextQuestion(){
        //Loop over the questions and return a new one at each call
        mQuestionIndex++;
        return getCurrentQuestion();
    }

    public QuestionBank generateQuestions(){
        Question question1 = new Question(
                "Who is the creator of Android?",
                Arrays.asList(
                        "Andy Rubin",
                        "Steve Wozniak",
                        "Jake Wharton",
                        "Paul Smith"
                ),
                0
        );
        Question question2 = new Question(
                "When did the first man land on the moon?",
                Arrays.asList(
                        "1958",
                        "1962",
                        "1967",
                        "1969"
                ),
                3
        );
        Question question3 = new Question(
                "What used to be the currency of Italy?",
                Arrays.asList(
                        "Pesetas",
                        "Lira",
                        "Euros",
                        "Venicians"
                ),
                1
        );
        Question question4 = new Question(
                "Quel est le nom du créateur de cette application?",
                Arrays.asList(
                        "Elie Boyrivent",
                        "Manon Bertrand",
                        "Kevin Boyrivent",
                        "Tiffany Boyrivent"
                ),
                0
        );

        return new QuestionBank(Arrays.asList(question1,question2,question3,question4));
    }

    @Override
    public String toString() {
        return "QuestionBank{" +
                "mQuestionList=" + mQuestionList +
                ", mQuestionIndex=" + mQuestionIndex +
                ", mQuestionBank=" + mQuestionBank +
                '}';
    }
}
