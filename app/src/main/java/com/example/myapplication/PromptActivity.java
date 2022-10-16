package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class PromptActivity extends AppCompatActivity {

    private int correctAnswer;
    public TextView answerTextView;
    public static final String KEY_EXTRA_ANSWER_SHOWN = "answerShown";

    private final String [] podpowiedzi = new String[]{
        ("W Kazachstanie padło takie same pytanie od dwóch obrońców bramy. Przypomnij sobie, jaka była wtedy odpowiedź?"),
        ("Lara pierwszy raz spotkała Rutlanda w Boliwii przed kamiennym podium."),
        ("W jakich lokalizacjach Lara jeździła na motocyklu?"),
        ("O losie ojca Lary dowiadujemy się od Natli."),
        ("Gracze późniejszych części byli zawiedzieni kolorem włosów Lary."),
        ("Amanda była w związku z Rutlandem."),
        ("Uncharted powstało na nowszym silniku, dzięki czemu grafika była znacznie lepsza."),
        ("W ilu lokalizacjach Lara używała granatnika?"),
        ("Czyżby Panna Croft była Walentynką dla graczy?"),
        ("Winston przez lata służył rodzinie Croft. Co robił gdy był młody?"),
};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_prompt);

        correctAnswer = getIntent().getIntExtra(MainActivity.KEY_EXTRA_ANSWER, 0);
        Button podpowiedzButton = findViewById(R.id.podpowiedz_button);
        answerTextView = findViewById(R.id.answer_text_view);

        podpowiedzButton.setOnClickListener(view -> {
            String answer = podpowiedzi[correctAnswer];
            answerTextView.setText(answer);
            setAnswerShownResult();
        });
    }
    private void setAnswerShownResult() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(KEY_EXTRA_ANSWER_SHOWN, true);
        setResult(RESULT_OK, resultIntent);
    }

}