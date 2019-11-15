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
import com.daxko.poc.adapter.CardAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HomeFragment extends Fragment {
    View view;
    TextView timeTxtvw;
    RecyclerView cardsRecyclerview;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.home_layout, container, false);

        setData();
        return view;
    }

    private void setData() {
        timeTxtvw= view.findViewById(R.id.time_txtvw);
        cardsRecyclerview= view.findViewById(R.id.cards_recyclerview);
        cardsRecyclerview.setAdapter(new CardAdapter(getActivity()));
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat sdf=new SimpleDateFormat("hh:mm a");
        sdf.format(currentTime);
        timeTxtvw.setText("Updated at "+sdf.format(currentTime));
    }
}
