package com.daxko.poc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.daxko.poc.R;

 public class RewardClaimAdapter extends RecyclerView.Adapter<RewardClaimAdapter.VH> {
    String[] nameArray;
    int[] imageArray;
    String[] valArray;
    Context context;

    public RewardClaimAdapter(Context ctx, int[] array) {
        this.context = ctx;
        this.nameArray = ctx.getResources().getStringArray(R.array.item_name);
        this.imageArray = array;
        this.valArray = ctx.getResources().getStringArray(R.array.item_values);
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.reward_claim_item, parent, false);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {

        // set image
        Glide.with(context).load(imageArray[position]).into(holder.imageView);

        holder.textViewItemName.setText(nameArray[position]);
        holder.textViewItemVal.setText(valArray[position]);
    }

    @Override
    public int getItemCount() {
        return nameArray.length;
    }

    public class VH extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textViewItemName;
        private TextView textViewItemVal;

        public VH(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView_item);
            textViewItemName = itemView.findViewById(R.id.textView_item_name);
            textViewItemVal = itemView.findViewById(R.id.textView_redeem_points_val);
        }
    }
}
