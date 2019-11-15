package com.daxko.poc.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.daxko.poc.R;
import com.daxko.poc.adapter.RewardClaimAdapter;

import java.util.ArrayList;
import java.util.List;

public class RedeemPointFragment extends Fragment {
    View view;
    TextView headerTextvw;
    RecyclerView rewardClaimRecyclerview;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.reward_log_screen, container, false);

        // set up images
        int[] imagesArray = {R.drawable.bag, R.drawable.caps,R.drawable.mug,R.drawable.t_shirt};

        setUpdata(imagesArray);
        return view;
    }

    private void setUpdata(int[] imageArray) {
        headerTextvw = view.findViewById(R.id.header_textvw);
        rewardClaimRecyclerview = view.findViewById(R.id.reward_claim_recyclerview);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        rewardClaimRecyclerview.setLayoutManager(manager);
        rewardClaimRecyclerview.setAdapter(new RewardClaimAdapter(getContext(), imageArray));

    }
}
