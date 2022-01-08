package com.example.topquizz.controller;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.topquizz.R;
import com.example.topquizz.model.User;

public class MainActivity extends AppCompatActivity {
    private TextView mGreetingTextView;
    private EditText mNameEditText;
    private Button mPlayButton;
    private User mUser;
    private static final int GAME_ACTIVITY_REQUEST_CODE = 42;
    private static final String SHARED_PREF_USER_INFO = "SHARED _PREF_USER_INFO";
    private static final String SHARED_PREF_USER_INFO_NAME = "SHARED_PREF_USER_INFO_NAME";
    private static final String SHARED_PREF_USER_INFO_SCORE = "SHARED_PREF_USER_INFO_SCORE";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUser = new User();
        mGreetingTextView = findViewById(R.id.main_textview_greeting);
        mPlayButton = findViewById(R.id.main_button_play);
        mNameEditText = findViewById(R.id.main_edittext_name);
        mPlayButton.setEnabled(false);
        mNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mPlayButton.setEnabled(!editable.toString().isEmpty());
            }
        });
        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gameActivityIntent = new Intent(MainActivity.this, GameActivity.class);
                mUser.setFirstName(mNameEditText.getText().toString());
                startActivityForResult(gameActivityIntent, GAME_ACTIVITY_REQUEST_CODE);
                getSharedPreferences(SHARED_PREF_USER_INFO, MODE_PRIVATE)
                        .edit()
                        .putString(SHARED_PREF_USER_INFO_NAME, mUser.getFirstName())
                        .apply();

            }
        });

        greetingVisitor();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(GAME_ACTIVITY_REQUEST_CODE == requestCode && RESULT_OK == resultCode)
        {
            int score = data.getIntExtra(GameActivity.BUNDLE_EXTRA_SCORE,0);
            getSharedPreferences(SHARED_PREF_USER_INFO, MODE_PRIVATE)
                    .edit()
                    .putInt(SHARED_PREF_USER_INFO_SCORE, score)
                    .apply();
        }

        greetingVisitor();
    }

    private void greetingVisitor()
    {
        String firstName = getSharedPreferences(SHARED_PREF_USER_INFO,
                MODE_PRIVATE).getString(SHARED_PREF_USER_INFO_NAME, null);
        Integer score = getSharedPreferences(SHARED_PREF_USER_INFO,MODE_PRIVATE).getInt(SHARED_PREF_USER_INFO_SCORE, -1);
        if (firstName != null && score != -1)
        {
            mGreetingTextView.setText("Bon retour, " +  firstName + " !\n" +
                    "Ton dernier score était " + score +", essaie de faire mieux cette fois ci !");

            mNameEditText.setHint(firstName);
        }
    }
}