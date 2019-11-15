package com.daxko.poc.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.daxko.poc.R;
import com.daxko.poc.fragment.HomeFragment;
import com.daxko.poc.fragment.RedeemPointFragment;
import com.daxko.poc.fragment.FriendsFragment;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    ViewPager pager;
    TabLayout tabs;
    private int[] tabIcons = {
            R.drawable.catalogue,
            R.drawable.catalogue,
            R.drawable.ic_card_giftcard_black_24dp
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpViewPager();
        setupTabIcons();
    }
    private void setupTabIcons() {
        tabs.getTabAt(0).setIcon(tabIcons[0]);
        tabs.getTabAt(1).setIcon(tabIcons[1]);
        tabs.getTabAt(2).setIcon(tabIcons[2]);
    }
    private void setUpViewPager() {
        tabs = findViewById(R.id.tabs);
        pager = findViewById(R.id.viewPager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        tabs.setupWithViewPager(pager);
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        private String[] tabTitles = new String[]{"Catalogue", "Points", "My Rewards"};
        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int pos) {
            switch (pos) {
                case 0:
                    return new HomeFragment();
                case 1:
                    return new RedeemPointFragment();
                case 2:
                    return new FriendsFragment();
                default:
                    return new FriendsFragment();
            }
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }
    }

}
