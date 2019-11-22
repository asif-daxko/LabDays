package com.daxko.poc.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.daxko.poc.R;
import com.daxko.poc.adapter.LeaderBoardAdapter;

import java.util.ArrayList;

public class LeaderBoardActivity extends AppCompatActivity {

    ArrayList<String> friendslist;
    RecyclerView leaderboardRecyclerview;
    ImageView imageView6,imageView5;
    TextView rank_textvw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board);

        friendslist= getIntent().getStringArrayListExtra("leaderboarddata");
        leaderboardRecyclerview  =findViewById(R.id.leaderboard_recyclerview);
        imageView6  =findViewById(R.id.imageView6);
        rank_textvw  =findViewById(R.id.rank_textvw);
        imageView5  =findViewById(R.id.imageView5);
        leaderboardRecyclerview.setAdapter(new LeaderBoardAdapter(LeaderBoardActivity.this,friendslist));

        rank_textvw.setText("Rank 1");
        Glide.with(LeaderBoardActivity.this)
                .load(R.raw.trophy)
                .into(imageView6);

        imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
