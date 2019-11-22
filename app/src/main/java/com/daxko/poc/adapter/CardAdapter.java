package com.daxko.poc.adapter;

import android.content.Context;
import android.hardware.SensorEventListener;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.daxko.poc.R;
import com.daxko.poc.activity.ChallengeListActivity;
import com.daxko.poc.interfaces.ChallengeClickListener;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.VH> {

    Context context;
    String[] itemTypeArray;
    ChallengeClickListener challengeClickListener;
    int[] imagesArray;
    double[] fitData;

    public CardAdapter(Context context, ChallengeClickListener challengeClickListener,int[] imagesArray,double[] fitData) {
        this.context=context;
        this.challengeClickListener=challengeClickListener;
        this.itemTypeArray=context.getResources().getStringArray(R.array.home_screen_item);
        this.imagesArray=imagesArray;
        this.fitData=fitData;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.card_item,parent,false);

        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, final int position) {
        holder.itemTypeTextvw.setText(itemTypeArray[position]);
        holder.textView2.setText(String.format("%.2f", fitData[position])+"");
        Glide.with(context).load(imagesArray[position]).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                challengeClickListener.OnItemClick(v,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemTypeArray.length;
    }

    public class VH extends RecyclerView.ViewHolder {
        TextView itemTypeTextvw,textView2;
        ImageView imageView;

        public VH(@NonNull View itemView) {
            super(itemView);

            textView2=itemView.findViewById(R.id.textView2);
            itemTypeTextvw=itemView.findViewById(R.id.item_type_textvw);
            imageView=itemView.findViewById(R.id.imageView);
        }
    }
}
