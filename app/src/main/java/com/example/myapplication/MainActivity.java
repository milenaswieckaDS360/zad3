package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private TextView questionTextView;

    private static final String KEY_CURRENT_INDEX = "current_index";
    public static final String KEY_EXTRA_ANSWER = "answer";
    private static final int REQUEST_CODE_PROMPT = 0;
    public static boolean answerWasShown;
    private int currentIndex = 0;

    private final Question[] questions = new Question[]{
            new Question(R.string.q_1, false),
            new Question(R.string.q_2, false),
            new Question(R.string.q_3, false),
            new Question(R.string.q_4, false),
            new Question(R.string.q_5, true),
            new Question(R.string.q_6, true),
            new Question(R.string.q_7, false),
            new Question(R.string.q_8, false),
            new Question(R.string.q_9, true),
            new Question(R.string.q_10, true),
    };


    private void checkAnswerCorrectness(boolean userAnswer) {
        boolean correctAnswer = questions[currentIndex].isTrueAnswer();
        int resultMessageId;
        if (userAnswer == correctAnswer) {
            resultMessageId = R.string.correct_answer;
        } else {
            resultMessageId = R.string.incorrect_answer;
        }
        Toast.makeText(this, resultMessageId, Toast.LENGTH_SHORT).show();
    }

    private void checkHintView() {
        int resultMessageId;
        if (answerWasShown) {
            resultMessageId = R.string.answer_was_shown;
            Toast.makeText(this, resultMessageId, Toast.LENGTH_SHORT).show();
        } else{
            Intent intent = new Intent(MainActivity.this, PromptActivity.class);
            int correctAnswer = currentIndex;
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(KEY_EXTRA_ANSWER, correctAnswer);
            startActivity(intent);
            startActivityForResult(intent, REQUEST_CODE_PROMPT);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        Log.d("QUIZ_TAG", "Wywołana została metoda cyklu życia: onCreate");
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX);
        }

        com.example.myapplication.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Button podpowiedzButton = findViewById(R.id.podpowiedz_button);
        Button trueButton = findViewById(R.id.button_true);
        Button falseButton = findViewById(R.id.button_false);
        Button nextButton = findViewById(R.id.next_button);
        questionTextView = findViewById(R.id.question_text_view);

        podpowiedzButton.setOnClickListener(view -> checkHintView());
        trueButton.setOnClickListener(view -> checkAnswerCorrectness(true));

        falseButton.setOnClickListener(view -> checkAnswerCorrectness(false));

        nextButton.setOnClickListener(view -> {
            currentIndex = (currentIndex + 1) % questions.length;
            answerWasShown = false;
            setNextQuestion();
        });

        setNextQuestion();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_PROMPT) {
            if (data == null) {
                return;
            }
            answerWasShown = data.getBooleanExtra(PromptActivity.KEY_EXTRA_ANSWER_SHOWN, false);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("QUIZ_TAG", "Start");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("QUIZ_TAG", "Resume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("QUIZ_TAG", "Pause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("QUIZ_TAG", "Stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("QUIZ_TAG", "Destroy");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("QUIZ_TAG", "Wywołana została metoda onSaveInstanceState");
        outState.putInt(KEY_CURRENT_INDEX, currentIndex);
    }

    private void setNextQuestion() {
        questionTextView.setText(questions[currentIndex].getQuestionId());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}