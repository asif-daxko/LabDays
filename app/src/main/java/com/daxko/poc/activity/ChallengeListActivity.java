package com.daxko.poc.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.daxko.poc.R;
import com.daxko.poc.adapter.ChallengesAdapter;
import com.daxko.poc.interfaces.ChallengeClickListener;
import com.daxko.poc.modelData.ChallengeData;

import java.util.ArrayList;
import java.util.List;

public class ChallengeListActivity extends AppCompatActivity implements ChallengeClickListener {
    RecyclerView challengesRecyclerview;
    List<ChallengeData> challengeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_list);

        setUpData();
    }

    private void setUpData() {
        challengeList = new ArrayList<>();
        challengesRecyclerview=findViewById(R.id.challenges_recyclerview);
        challengesRecyclerview.setAdapter(new ChallengesAdapter(ChallengeListActivity.this,this,
                prepareChallengeList()));
    }

    private List<ChallengeData> prepareChallengeList() {

        ChallengeData data1 = new ChallengeData();
        data1.setChallengeName("1,000 mile Cycle Challenge");
        data1.setChallengeDetail("Accumulate 1,000 miles from cycle classes");
        data1.setChallengeDate("20 Sep 2019 - 22 Sep 2019");

        ChallengeData data2 = new ChallengeData();
        data2.setChallengeName("Weight Loss Challenge");
        data2.setChallengeDetail("Lose 20 kgs weight and get a chance to win exciting rewards");
        data2.setChallengeDate("14 Oct 2019 - 14 Nov 2019");

        ChallengeData data3 = new ChallengeData();
        data3.setChallengeName("1,000 Steps Daily Challenge");
        data3.setChallengeDetail("1,000 steps for 2 consecutive days and you can win the challenge. So gear up");
        data3.setChallengeDate("30 Oct 2019 - 31 Oct 2019");

        ChallengeData data4 = new ChallengeData();
        data4.setChallengeName("Swimming Challenge");
        data4.setChallengeDetail("Take out your swim suits and get ready for the upcoming swimming challenge");
        data4.setChallengeDate("11 Nov 2019");

        challengeList.add(data3);
        challengeList.add(data2);
        challengeList.add(data1);
        challengeList.add(data4);

        return challengeList;
    }

    @Override
    public void OnItemClick(View v, int position) {
        Intent intent = new Intent(ChallengeListActivity.this, ChallengeDetailActivity.class);
        intent.putExtra("challenge_name", challengeList.get(position).getChallengeName());
        intent.putExtra("challenge_detail", challengeList.get(position).getChallengeDetail());
        startActivity(intent);
    }
}
