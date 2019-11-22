package com.daxko.poc.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.daxko.poc.R;
import com.daxko.poc.fragment.LevelTypeFragment;
import com.daxko.poc.utility.AppPrefs;

public class LevelDescriptionActivity extends AppCompatActivity {
    RadioGroup levelsRadiogrp;
    ImageView backImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_description);

        setData();

    }

    private void setData() {
        backImg=findViewById(R.id.back_img);
        levelsRadiogrp=findViewById(R.id.levels_radiogrp);
        loadFragment(new LevelTypeFragment(),1);
        int levelcount= AppPrefs.getInstance(LevelDescriptionActivity.this).getLevel();
        int checkedId = levelsRadiogrp.getChildAt(levelcount).getId();
        setRadioData(checkedId);

        levelsRadiogrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                setRadioData(checkedId);
            }
        });

        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setRadioData(int checkedId) {
        switch (checkedId){
            case R.id.level1:
                loadFragment(new LevelTypeFragment(),1);
                break;
            case R.id.level2:
                loadFragment(new LevelTypeFragment(),2);
                break;
            case R.id.level3:
                loadFragment(new LevelTypeFragment(),3);
                break;
            case R.id.level4:
                loadFragment(new LevelTypeFragment(),4);
                break;
            case R.id.level5:
                loadFragment(new LevelTypeFragment(), 5);
                break;
        }
    }

    private boolean loadFragment(Fragment fragment,int count) {
        //switching fragment
        Bundle b=new Bundle();
        b.putInt("count",count);
        if (fragment != null) {
            fragment.setArguments(b);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

}
