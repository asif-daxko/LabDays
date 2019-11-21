package com.daxko.poc.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import com.daxko.poc.R;
import com.daxko.poc.modelData.InviteData;
import java.util.List;

public class InviteAdapter extends RecyclerView.Adapter<InviteAdapter.FriendHolder> {
private Context context;

        List<InviteData> friendList;
public InviteAdapter(@NonNull Context context, List<InviteData> friendList) {
        this.context=context;
        this.friendList=friendList;
        }

@NonNull
@Override
public FriendHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View listItem= layoutInflater.inflate(R.layout.invite_item_view, parent, false);
        FriendHolder viewHolder = new FriendHolder(listItem);
        return viewHolder;
        }

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
@Override
public void onBindViewHolder(@NonNull FriendHolder holder, final int position) {
        holder.friendName.setText(friendList.get(position).getName());
        String name=friendList.get(position).getName();
        String[] arrayName= name.split(" ");
        String sName="";
        if(arrayName.length>1){
        sName= Character.toString(arrayName[0].charAt(0))+ Character.toString(arrayName[1].charAt(0));
        }else {
        sName= Character.toString(arrayName[0].charAt(0));
        }
        holder.shortName.setText(sName);
        holder.number.setText(friendList.get(position).getNumber());
        if(friendList.get(position).isInvite()){
            holder.invite.setBackground(context.getResources().getDrawable(R.drawable.invite_btn_sel));
        }else {
            holder.invite.setBackground(context.getResources().getDrawable(R.drawable.invite_btn_unsel));
        }
        holder.invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(friendList.get(position).isInvite()){
                    friendList.get(position).setInvite(false);
                }else {
                    friendList.get(position).setInvite(true);
                }
                notifyDataSetChanged();
            }
        });
        }

@Override
public int getItemCount() {
        return friendList.size();
        }


public class FriendHolder extends RecyclerView.ViewHolder
{
    TextView friendName,shortName,invite,number;
    public FriendHolder(@NonNull View itemView) {
        super(itemView);
        friendName=itemView.findViewById(R.id.friendName);
        shortName=itemView.findViewById(R.id.shortName);
        invite=itemView.findViewById(R.id.invite);
        number=itemView.findViewById(R.id.number);
    }
}

}