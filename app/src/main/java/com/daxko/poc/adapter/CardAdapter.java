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

    public CardAdapter(Context context) {
        this.context=context;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.card_item,parent,false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public class VH extends RecyclerView.ViewHolder {

        public VH(@NonNull View itemView) {
            super(itemView);
        }
    }
}
