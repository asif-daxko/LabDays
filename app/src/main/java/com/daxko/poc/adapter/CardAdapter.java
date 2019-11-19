package com.daxko.poc.adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.daxko.poc.R;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.VH> {
    Context context;
    String[] itemTypeArray;

    public CardAdapter(Context context) {
        this.context=context;
        this.itemTypeArray=context.getResources().getStringArray(R.array.home_screen_item);
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.card_item,parent,false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.itemTypeTextvw.setText(itemTypeArray[position]);
    }

    @Override
    public int getItemCount() {
        return itemTypeArray.length;
    }

    public class VH extends RecyclerView.ViewHolder {
        TextView itemTypeTextvw;

        public VH(@NonNull View itemView) {
            super(itemView);

            itemTypeTextvw=itemView.findViewById(R.id.item_type_textvw);
        }
    }
}
