package com.example.topquizz.controller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.topquizz.R;
import com.example.topquizz.model.Question;
import com.example.topquizz.model.QuestionBank;

import java.io.BufferedReader;
import java.util.Arrays;
import java.util.Collections;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mQuestionTextView;
    private Button m1Button;
    private Button m2Button;
    private Button m3Button;
    private Button m4Button;
    private QuestionBank mQuestionBank;
    private int mRemainingQuestionCount;
    private Question mCurrentQuestion;
    private int mScore;
    public static final String BUNDLE_EXTRA_SCORE = "BUNDLE_EXTRA_SCORE";
    private boolean mEnableTouchEvents = true;
    public static final String BUNDLE_STATE_SCORE = "BUNDLE_STATE_SCORE";
    public static final String BUNDLE_STATE_QUESTION = "BUNDLE_STATE_QUESTION";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mQuestionTextView = findViewById(R.id.game_activity_textview_question);
        m1Button = findViewById(R.id.game_activity_button_1);
        m2Button = findViewById(R.id.game_activity_button_2);
        m3Button = findViewById(R.id.game_activity_button_3);
        m4Button = findViewById(R.id.game_activity_button_4);

        //Use the same listener for the four buttons.
        // The view id value will be used to distinguish the button triggered
        m1Button.setOnClickListener(this);
        m2Button.setOnClickListener(this);
        m3Button.setOnClickListener(this);
        m4Button.setOnClickListener(this);

        mQuestionBank = new QuestionBank(Collections.emptyList());
        mQuestionBank = mQuestionBank.generateQuestions();

        if (savedInstanceState != null){
            mScore = savedInstanceState.getInt(BUNDLE_STATE_SCORE);
            mRemainingQuestionCount = savedInstanceState.getInt(BUNDLE_STATE_QUESTION);

        }
        else{
            mRemainingQuestionCount = 4;
            mScore=0;
        }

        mCurrentQuestion = mQuestionBank.getCurrentQuestion();
        displayQuestion(mCurrentQuestion);

    }


    private void displayQuestion(final Question question)
    {
        // Set the text for the question text view and the four buttons
        mQuestionTextView.setText(question.getQuestion());
        m1Button.setText(question.getChoiceList().get(0));
        m2Button.setText(question.getChoiceList().get(1));
        m3Button.setText(question.getChoiceList().get(2));
        m4Button.setText(question.getChoiceList().get(3));

    }

    @Override
    public void onClick(View view) {
        int index;

        if (view == m1Button){
            index = 0;
        }else if (view == m2Button){
            index = 1;
        }else if (view == m3Button){
            index = 2;
        }else if (view == m4Button){
            index = 3;
        }else{
            throw new IllegalStateException("Unknown clicked view : " + view);
        }

        if(index == mQuestionBank.getCurrentQuestion().getAnswerIndex())
        {
            mScore++;
            Toast.makeText(this,"Correct!", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this,"Wrong!", Toast.LENGTH_SHORT).show();
        }
        mEnableTouchEvents = false;
        mRemainingQuestionCount--;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mRemainingQuestionCount>0)
                {
                    Log.i("TAG", mQuestionBank.toString());
                    mCurrentQuestion = mQuestionBank.getNextQuestion();
                    displayQuestion(mCurrentQuestion);
                    mEnableTouchEvents=true;
                }
                else{
                    //end the game
                    endgame();

                }
            }
        }, 2000);

    }

    private void endgame(){
        AlertDialog.Builder builder= new AlertDialog.Builder(this);

        builder.setTitle("Well done!")
                .setMessage("Your score is " + mScore)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent();
                        intent.putExtra(BUNDLE_EXTRA_SCORE, mScore);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                })
                .create()
                .show();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return mEnableTouchEvents && super.dispatchTouchEvent(ev);
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(BUNDLE_STATE_SCORE, mScore);
        outState.putInt(BUNDLE_STATE_QUESTION, mRemainingQuestionCount);
    }
}