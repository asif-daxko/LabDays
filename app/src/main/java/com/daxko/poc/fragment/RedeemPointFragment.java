package com.daxko.poc.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.daxko.poc.R;

public class RedeemPointFragment extends Fragment {
    View view;
    TextView headerTextvw;
    RecyclerView rewardClaimRecyclerview;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.reward_log_screen, container, false);

        setUpdata();
        return view;
    }

    private void setUpdata() {
        headerTextvw = view.findViewById(R.id.header_textvw);
        rewardClaimRecyclerview = view.findViewById(R.id.reward_claim_recyclerview);
        rewardClaimRecyclerview.setAdapter(new RewardClaimAdapter());

    }

    private class RewardClaimAdapter extends RecyclerView.Adapter<RewardClaimAdapter.VH> {
        @NonNull
        @Override
        public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.reward_claim_item, parent, false);
            return new VH(view);
        }

        @Override
        public void onBindViewHolder(@NonNull VH holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 10;
        }

        public class VH extends RecyclerView.ViewHolder {
            public VH(@NonNull View itemView) {
                super(itemView);
            }
        }
    }
}
