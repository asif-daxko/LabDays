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
import com.daxko.poc.modelData.ChallengeData;

import java.util.List;

public class ChallengesAdapter extends RecyclerView.Adapter<ChallengesAdapter.VH> {
    Context context;
    ChallengeClickListener challengeClickListener;
    List<ChallengeData> list;

    public ChallengesAdapter(Context context,ChallengeClickListener challengeClickListener, List<ChallengeData> ll) {
        this.context = context;
        this.challengeClickListener = challengeClickListener;
        this.list = ll;
    }

    @NonNull
    @Override
    public ChallengesAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.challenge_item, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChallengesAdapter.VH holder, final int position) {
        holder.challengeName.setText(list.get(position).getChallengeName());
        holder.challengeDetail.setText(list.get(position).getChallengeDetail());
        holder.date.setText(list.get(position).getChallengeDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                challengeClickListener.OnItemClick(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        TextView challengeName;
        TextView challengeDetail;
        TextView date;

        public VH(@NonNull View itemView) {
            super(itemView);
            challengeName=itemView.findViewById(R.id.challenge_name);
            challengeDetail=itemView.findViewById(R.id.textView_challenge_text);
            date=itemView.findViewById(R.id.textView_date);
        }
    }
}
