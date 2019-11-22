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
import com.daxko.poc.activity.LeaderBoardActivity;
import com.daxko.poc.utility.AppPrefs;

import java.util.ArrayList;
import java.util.Arrays;

public class LeaderBoardAdapter extends RecyclerView.Adapter<LeaderBoardAdapter.VH> {
    Context context;
    ArrayList<String> friendslist;
    View view;
    int count=0;

    public LeaderBoardAdapter(Context context, ArrayList<String> friendslist) {
        this.context=context;
        this.friendslist=friendslist;
        this.count=friendslist.size();
    }

    @NonNull
    @Override
    public LeaderBoardAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.leader_board_item,parent,false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaderBoardAdapter.VH holder, int position) {
        String[] arrayName = friendslist.get(position).split(" ");
        String sName = "";
        if (arrayName.length > 1) {
            sName = Character.toString(arrayName[0].charAt(0)) + Character.toString(arrayName[1].charAt(0));
        } else {
            sName = Character.toString(arrayName[0].charAt(0));
        }
        holder.shortName.setText(sName);
        holder.player_name.setText(friendslist.get(position));
        holder.rank_textvw.setText(position+1+"\nRank");
        if ( count!=0 && count<=friendslist.size())
        holder.steps_walked.setText(100*count--+" - Steps Walked");
    }

    @Override
    public int getItemCount() {
        return friendslist.size();
    }

    class VH extends RecyclerView.ViewHolder {
        TextView shortName,player_name,steps_walked,rank_textvw;

        VH(@NonNull View itemView) {
            super(itemView);

            shortName = itemView.findViewById(R.id.shortName);
            player_name = itemView.findViewById(R.id.player_name);
            steps_walked = itemView.findViewById(R.id.steps_walked);
            rank_textvw = itemView.findViewById(R.id.rank_textvw);
        }
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
