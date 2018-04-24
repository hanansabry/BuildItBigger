package com.hanan.and.udacity.jokesdisplay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

public class JokeDisplayActivity extends AppCompatActivity {
    public static final String JOKE_KEY = "joke key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_display);

        TextView jokeView = findViewById(R.id.joke_view);

        Intent intent = getIntent();
        String joke = intent.getStringExtra(JOKE_KEY);
        if (!TextUtils.isEmpty(joke)) {
            jokeView.setText(joke);
        }
    }
}
