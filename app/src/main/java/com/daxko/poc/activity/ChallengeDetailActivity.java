package com.daxko.poc.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.daxko.poc.R;

public class ChallengeDetailActivity extends AppCompatActivity {
    Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_detail);

        setData();
        setActions();
    }

    private void setData() {
        TextView textViewChallengeName = findViewById(R.id.textView_challenge_name);
        TextView textViewChallengeDetail = findViewById(R.id.textView_challenge_detail);

        // set text
        textViewChallengeName.setText(getIntent().getStringExtra("challenge_name"));
        textViewChallengeDetail.setText(getIntent().getStringExtra("challenge_detail"));
    }

    private void setActions() {
        startButton=findViewById(R.id.start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChallengeDetailActivity.this, ChallengeRewardActivity.class));
            }
        });
    }
}
