package com.daxko.poc.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daxko.poc.R;
import com.daxko.poc.adapter.RewardClaimAdapter;

public class RedeemPointFragment extends Fragment {
    View view;
    TextView headerTextvw;
    RecyclerView rewardClaimRecyclerview;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_reward, container, false);

        // set up images
        int[] imagesArray = {R.mipmap.bag, R.mipmap.caps,R.mipmap.mug,R.mipmap.t_shirt};

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
