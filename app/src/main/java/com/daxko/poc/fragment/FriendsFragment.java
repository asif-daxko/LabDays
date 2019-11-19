package com.daxko.poc.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.daxko.poc.R;

public class FriendsFragment extends Fragment {
    View view;
    TextView headerTextvw,errorMsg,errorMsg1;
    ImageView errorImage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_reward, container, false);

        setUpdata();
        return view;
    }

    private void setUpdata() {
        errorMsg = view.findViewById(R.id.error_msg);
        errorMsg1 = view.findViewById(R.id.error_msg1);
        errorImage = view.findViewById(R.id.error_image);
        headerTextvw = view.findViewById(R.id.header_textvw);

        headerTextvw.setVisibility(View.GONE);
        errorMsg1.setVisibility(View.VISIBLE);
        errorMsg.setVisibility(View.VISIBLE);
        errorImage.setVisibility(View.VISIBLE);
    }
}
