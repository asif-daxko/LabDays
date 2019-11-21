package com.daxko.poc.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daxko.poc.R;

import java.util.List;


public class FriendListAdapyter extends RecyclerView.Adapter<FriendListAdapyter.FriendHolder> {
    private Context context;
    List<String> friendList;
    public FriendListAdapyter(@NonNull Context context, List<String> friendList) {
        this.context=context;
        this.friendList=friendList;
    }

    @NonNull
    @Override
    public FriendHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View listItem= layoutInflater.inflate(R.layout.friend_item_view, parent, false);
        FriendHolder viewHolder = new FriendHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FriendHolder holder, int position) {
         holder.friendName.setText(friendList.get(position));
         String name=friendList.get(position);
         String[] arrayName= name.split(" ");
         String sName="";
         if(arrayName.length>1){
             sName= Character.toString(arrayName[0].charAt(0))+ Character.toString(arrayName[1].charAt(0));
         }else {
             sName= Character.toString(arrayName[0].charAt(0));
         }
         holder.shortName.setText(sName);

    }

    @Override
    public int getItemCount() {
        return friendList.size();
    }


    public class FriendHolder extends RecyclerView.ViewHolder
    {
        TextView friendName,shortName;
        public FriendHolder(@NonNull View itemView) {
            super(itemView);
            friendName=itemView.findViewById(R.id.friendName);
            shortName=itemView.findViewById(R.id.shortName);
        }
    }

}
