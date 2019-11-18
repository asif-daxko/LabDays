package com.daxko.poc.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daxko.poc.R;
import com.daxko.poc.activity.ChallengeListActivity;
import com.daxko.poc.interfaces.ChallengeClickListener;

public class ChallengesAdapter extends RecyclerView.Adapter<ChallengesAdapter.VH> {
    Context context;
    ChallengeClickListener challengeClickListener;
    String[] nameArray;

    public ChallengesAdapter(Context context,ChallengeClickListener challengeClickListener) {
        this.context = context;
        this.challengeClickListener = challengeClickListener;
        this.nameArray = context.getResources().getStringArray(R.array.challenges_name);
    }

    @NonNull
    @Override
    public ChallengesAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.challenge_item, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChallengesAdapter.VH holder, final int position) {
        holder.challengeName.setText(nameArray[position]);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                challengeClickListener.OnItemClick(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return nameArray.length;
    }

    public class VH extends RecyclerView.ViewHolder {
        TextView challengeName;

        public VH(@NonNull View itemView) {
            super(itemView);
            challengeName=itemView.findViewById(R.id.challenge_name);
        }
    }
}
