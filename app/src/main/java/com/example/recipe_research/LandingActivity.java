package com.example.recipe_research;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LandingActivity extends AppCompatActivity implements View.OnClickListener  {
    Button _startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        _startButton = findViewById(R.id.startButton);
        _startButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(v == _startButton){
            Intent i = new Intent(LandingActivity.this, MainActivity.class);
            startActivity(i);
        }

    }
}