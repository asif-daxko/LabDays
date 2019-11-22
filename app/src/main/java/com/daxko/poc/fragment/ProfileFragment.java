package com.daxko.poc.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.daxko.poc.R;
import com.daxko.poc.utility.AppPrefs;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
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
        TextView textViewCoins = (TextView) view.findViewById(R.id.textView_total_coins);

        // set coins
        textViewLevel.setText(String.valueOf(AppPrefs.getInstance(getContext()).getLevel()));
        textViewCoins.setText(String.valueOf(AppPrefs.getInstance(getContext()).getCoins()));

        BarChart chart =(BarChart) view.findViewById(R.id.barchart);

        /*List stepsTaken = new ArrayList();

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
       *//*BarData data = new BarData(dates, bardataset);
        bardataset.setValueTextColor(Color.parseColor("#FFFFFF"));
        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
        chart.setData(data);*/

        YAxis left = chart.getAxisLeft();
        left.setAxisMaxValue(2500);//dataset.getYMax()+2);
        left.setAxisMinValue(0);
        chart.getAxisRight().setEnabled(false);
        chart.animateY(500);
        XAxis bottomAxis = chart.getXAxis();
        bottomAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        bottomAxis.setAxisMinValue(0);

        bottomAxis.setLabelCount(10);
        bottomAxis.setDrawGridLines(false);
        chart.setDrawValueAboveBar(false);
        chart.getDescription().setEnabled(false);
        // legend
        Legend legend = chart.getLegend();
        legend.setYOffset(40);
        legend.setTextSize(200);
        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry (1, 500));
        ArrayList<BarEntry> entries2 = new ArrayList<>();
        entries2.add(new BarEntry (3, 700));
        ArrayList<BarEntry> entries3 = new ArrayList<>();
        entries3.add(new BarEntry (5, 950));
        ArrayList<BarEntry> entries4 = new ArrayList<>();
        entries4.add(new BarEntry (7, 650));
        ArrayList<BarEntry> entries5 = new ArrayList<>();
        entries5.add(new BarEntry (9, 1500));
        List<IBarDataSet> bars = new ArrayList<IBarDataSet>();
        BarDataSet dataset = new BarDataSet(entries, "First");
        dataset.setColor(Color.RED);
        dataset.setValueTextColor(Color.WHITE);
        bars.add(dataset);
        BarDataSet dataset2 = new BarDataSet(entries2, "Second");
        dataset2.setColor(Color.BLUE);
        dataset2.setValueTextColor(Color.WHITE);
        bars.add(dataset2);
        BarDataSet dataset3 = new BarDataSet(entries3, "Third");
        dataset3.setColor(Color.GREEN);
        dataset3.setValueTextColor(Color.WHITE);
        bars.add(dataset3);
        BarDataSet dataset4 = new BarDataSet(entries4, "Fourth");
        dataset4.setColor(Color.GRAY);
        dataset4.setValueTextColor(Color.WHITE);
        bars.add(dataset4);
        BarDataSet dataset5 = new BarDataSet(entries5, "Fifth");
        dataset5.setColor(Color.CYAN);
        dataset5.setValueTextColor(Color.BLACK);
        bars.add(dataset5);
        BarData data = new BarData(bars);
        chart.setData(data);
    }
}
