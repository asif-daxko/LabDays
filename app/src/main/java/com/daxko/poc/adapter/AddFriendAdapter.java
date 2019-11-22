package com.daxko.poc.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.daxko.poc.R;
import com.daxko.poc.modelData.AddFriendModel;

import java.util.ArrayList;
import java.util.List;

public class AddFriendAdapter  extends RecyclerView.Adapter<AddFriendAdapter.FriendHolder> implements Filterable {
    private Context context;
    private List<AddFriendModel> fixList;
    private List<AddFriendModel> friendList;
    private FriendFilter friendFilter;

    public AddFriendAdapter(@NonNull Context context, List<AddFriendModel> friendList,List<AddFriendModel> fixList) {
        this.context = context;
        this.friendList = friendList;
        this.fixList=fixList;
    }

    @NonNull
    @Override
    public AddFriendAdapter.FriendHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View listItem = layoutInflater.inflate(R.layout.add_item_view, parent, false);
        AddFriendAdapter.FriendHolder viewHolder = new AddFriendAdapter.FriendHolder(listItem);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(@NonNull AddFriendAdapter.FriendHolder holder, final int position) {
        holder.friendName.setText(friendList.get(position).getName());
        String name = friendList.get(position).getName();
        String[] arrayName = name.split(" ");
        String sName = "";
        if (arrayName.length > 1) {
            sName = Character.toString(arrayName[0].charAt(0)) + Character.toString(arrayName[1].charAt(0));
        } else {
            sName = Character.toString(arrayName[0].charAt(0));
        }
        holder.shortName.setText(sName);
        holder.memberId.setText(friendList.get(position).getMemberId());
        if(friendList.get(position).isAdd()){
            holder.invite.setBackground(context.getResources().getDrawable(R.drawable.invite_btn_sel));
            holder.invite.setTextColor(Color.BLACK);
        }else {
            holder.invite.setBackground(context.getResources().getDrawable(R.drawable.invite_btn_unsel));
            holder.invite.setTextColor(Color.WHITE);
        }
        holder.invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(friendList.get(position).isAdd()){
                    friendList.get(position).setAdd(false);
                }else {
                    friendList.get(position).setAdd(true);
                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return friendList.size();
    }

    @Override
    public Filter getFilter() {
        if(friendFilter == null) {
            friendFilter=new FriendFilter();
        }
        return friendFilter;
    }


    class FriendHolder extends RecyclerView.ViewHolder {
        TextView friendName, shortName, invite,memberId;

        FriendHolder(@NonNull View itemView) {
            super(itemView);
            friendName = itemView.findViewById(R.id.friendName);
            shortName = itemView.findViewById(R.id.shortName);
            invite = itemView.findViewById(R.id.invite);
            memberId=itemView.findViewById(R.id.memberId);
        }
    }

    private class FriendFilter extends Filter{


        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results = new FilterResults();
            if (constraint == null || constraint.length() == 0) {
                //No need for filter
                results.values = friendList;
                results.count = friendList.size();

            } else {
                //Need Filter
                // it matches the text  entered in the edittext and set the data in adapter list
                ArrayList<AddFriendModel> friendListRecord = new ArrayList<AddFriendModel>();

                for (AddFriendModel addFriendModel : fixList) {
                    if (addFriendModel.getName().toUpperCase().trim().contains(constraint.toString().toUpperCase().trim())) {
                        friendListRecord.add(addFriendModel);
                    }
                    if (addFriendModel.getMemberId().toUpperCase().trim().contains(constraint.toString().toUpperCase().trim())) {
                        friendListRecord.add(addFriendModel);
                    }
                }
                results.values = friendListRecord;
                results.count = friendListRecord.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint,FilterResults results) {

            //it set the data from filter to adapter list and refresh the recyclerview adapter
            friendList = (ArrayList<AddFriendModel>) results.values;
            Log.e("count",friendList.size()+"");
            notifyDataSetChanged();
        }
    }
    public List<AddFriendModel> getCurrentList(){
        return friendList;
    }
}

