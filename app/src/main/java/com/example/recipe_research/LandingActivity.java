package com.example.recipe_research;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LandingActivity extends AppCompatActivity implements View.OnClickListener {
    private Button startButton;

    // Creates a new ContentView for the Layout.activity_landing and sets the onClickListener for the startButton
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(this);
    }

    // Starts the MainActivity when the startButton is clicked
    @Override
    public void onClick(View v) {
        if (v == startButton) {
            Intent i = new Intent(LandingActivity.this, MainActivity.class);
            startActivity(i);
        }
    }
}