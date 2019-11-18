package com.daxko.poc.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.daxko.poc.R;
import com.daxko.poc.adapter.ChallengesAdapter;
import com.daxko.poc.interfaces.ChallengeClickListener;

public class ChallengeListActivity extends AppCompatActivity implements ChallengeClickListener {
    RecyclerView challengesRecyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_list);

        ChallengeListActivity.this.setTitle("Challenges");

        setUpData();
    }

    private void setUpData() {
        challengesRecyclerview=findViewById(R.id.challenges_recyclerview);
        challengesRecyclerview.setAdapter(new ChallengesAdapter(ChallengeListActivity.this,this));
    }

    @Override
    public void OnItemClick(View v, int position) {
        startActivity(new Intent(ChallengeListActivity.this, ChallengeDetailActivity.class));
    }
}
