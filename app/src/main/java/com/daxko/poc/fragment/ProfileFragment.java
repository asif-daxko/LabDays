package com.daxko.poc.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.daxko.poc.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_profile, container, false);
        setUpData(view);
        return view;
    }

    private void setUpData(View view) {
        TextView textViewLevel = (TextView) view.findViewById(R.id.textView_level);
        BarChart chart = (BarChart) view.findViewById(R.id.barchart);
        List stepsTaken = new ArrayList();
        stepsTaken.add(new BarEntry(1240f, 0));
        stepsTaken.add(new BarEntry(1040f, 1));
        stepsTaken.add(new BarEntry(1437f, 2));
        stepsTaken.add(new BarEntry(1245f, 3));
        stepsTaken.add(new BarEntry(1369f, 4));
        List dates = new ArrayList();
        dates.add("26 Oct'19");
        dates.add("02 Nov'19");
        dates.add("09 Nov'19");
        dates.add("11 Nov'19");
        dates.add("18 Nov'19");
        BarDataSet bardataset = new BarDataSet(stepsTaken, "Steps");
        chart.animateY(3000);
       BarData data = new BarData(dates, bardataset);
        bardataset.setValueTextColor(Color.parseColor("#FFFFFF"));
        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
        chart.setData(data);
    }
}
