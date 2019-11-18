package com.daxko.poc.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daxko.poc.R;
import com.daxko.poc.activity.ChallengeListActivity;
import com.daxko.poc.interfaces.ChallengeClickListener;

public class ChallengesAdapter extends RecyclerView.Adapter<ChallengesAdapter.VH> {
    Context context;
    ChallengeClickListener challengeClickListener;

    public ChallengesAdapter(Context context,ChallengeClickListener challengeClickListener) {
        this.context = context;
        this.challengeClickListener = challengeClickListener;
    }

    @NonNull
    @Override
    public ChallengesAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.challenge_item, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChallengesAdapter.VH holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                challengeClickListener.OnItemClick(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class VH extends RecyclerView.ViewHolder {
        public VH(@NonNull View itemView) {
            super(itemView);
        }
    }
}
