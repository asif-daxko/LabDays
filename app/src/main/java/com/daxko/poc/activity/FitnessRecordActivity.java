package com.daxko.poc.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.daxko.poc.R;
import com.daxko.poc.utility.AppPrefs;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;


public class FitnessRecordActivity extends AppCompatActivity {
    RadioButton calories,distance,steps;
    TextView headingText;
    ImageView backImg;
    RadioGroup radiogrp;
    BarChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness_record);
        setData();
    }

    private void setData() {
        radiogrp = findViewById(R.id.radiogrp);
        backImg = findViewById(R.id.back_img);
         chart = findViewById(R.id.barchart);
        headingText = findViewById(R.id.heading_text);
        calories = findViewById(R.id.calories);
        steps = findViewById(R.id.steps);
        distance = findViewById(R.id.distance);


        double distancecovered=(float) (AppPrefs.getInstance(FitnessRecordActivity.this).getSteps())*0.000762;
        double calorieburned=(float) (AppPrefs.getInstance(FitnessRecordActivity.this).getSteps())*0.5;
        distance.setText(String.format("%.2f", distancecovered)+"\nDistance (km)");
        calories.setText(calorieburned+"\nCalorie");
        steps.setText(AppPrefs.getInstance(FitnessRecordActivity.this).getSteps()+"\nSteps");
        headingText.setText("Steps");

        setBarChartData();

        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        radiogrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(chart!=null){
                    chart.animateY(500);
                }
                switch (checkedId){
                    case R.id.calories :
                        headingText.setText("Calories");

                        break;
                    case R.id.distance :
                        headingText.setText("Distance [KM]");
                        break;
                    case R.id.steps :
                        headingText.setText("Steps");
                        break;
                }
            }
        });
    }

    private void setBarChartData() {

        // replace
//        ArrayList<BarEntry> entries = new ArrayList<>();
//        entries.add(new BarEntry (1, 5));
//        entries.add(new BarEntry (3, 7));
//        entries.add(new BarEntry (5,3));
//        entries.add(new BarEntry (7,4));
//        entries.add(new BarEntry (9,1));
//        BarDataSet dataset = new BarDataSet(entries, "First");
//        dataset.setColors(new int[] {Color.RED, Color.GREEN, Color.GRAY, Color.BLACK, Color.BLUE});
//        BarData data = new BarData(dataset);
//        chart.setData(data);
        // replace



        // below is simply styling decisions on code that I have)
        YAxis left = chart.getAxisLeft();
        left.setAxisMaxValue(10);//dataset.getYMax()+2);
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
        entries.add(new BarEntry (1, 5));
        ArrayList<BarEntry> entries2 = new ArrayList<>();
        entries2.add(new BarEntry (3, 2));
        ArrayList<BarEntry> entries3 = new ArrayList<>();
        entries3.add(new BarEntry (5, 7));
        ArrayList<BarEntry> entries4 = new ArrayList<>();
        entries4.add(new BarEntry (7, 7));
        ArrayList<BarEntry> entries5 = new ArrayList<>();
        entries5.add(new BarEntry (9, 1));
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
