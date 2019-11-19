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

import org.w3c.dom.Text;

public class LevelTypeFragment extends Fragment {
    View view;
    TextView coinIcon,toUpgrade,downgradeTextvw;
    int count = 0;
    TextView safeZoneTextvw;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.level_type_layout, container, false);


        setData();
        return view;
    }

    private void setData() {
        coinIcon = view.findViewById(R.id.coin_icon_textvw);
        safeZoneTextvw = view.findViewById(R.id.safe_zone_textvw);
        toUpgrade = view.findViewById(R.id.to_upgrade);
        downgradeTextvw = view.findViewById(R.id.downgrade_textvw);

        count = (int) getArguments().get("count");

        switch (count) {
            case 1:
                coinIcon.setText("5");
                safeZoneTextvw.setText("Safe Zone: 0 to 5 coins");
                toUpgrade.setText("Reach the Daily Limit for 3 days in a row, to upgrade to\nLevel 2");
                downgradeTextvw.setText("You cannot get downgraded from Level1");
                break;
            case 2:
                coinIcon.setText("10");
                safeZoneTextvw.setText("Safe Zone: 5 to 10 coins");
                toUpgrade.setText("Reach the Daily Limit for 3 days in a row, to upgrade to\nLevel 3");
                downgradeTextvw.setText("Failed to reach Safe Zone, 3 days in a row,, and get downgraded to \nLevel 1");
                break;
            case 3:
                coinIcon.setText("15");
                safeZoneTextvw.setText("Safe Zone: 10 to 15 coins");
                toUpgrade.setText("Reach the Daily Limit for 3 days in a row, to upgrade to\nLevel 4");
                downgradeTextvw.setText("Miss the Safe Zone, 3 days in a row, and get downgraded to \nLevel 2");
                break;
            case 4:
                coinIcon.setText("20");
                safeZoneTextvw.setText("Safe Zone: 15 to 20 coins");
                toUpgrade.setText("Reach the Daily Limit for 3 days in a row, to upgrade to\nLevel 5");
                downgradeTextvw.setText("Miss the Safe Zone, 3 days in a row, and get downgraded to \nLevel 3");
                break;
            case 5:
                coinIcon.setText("30");
                safeZoneTextvw.setText("Safe Zone: 25 to 30 coins");
                toUpgrade.setText("If you are able to stay on\nLevel 5, you are a Daxko/nLegend");
                downgradeTextvw.setText("Miss the Safe Zone, 3 days in a row, and get downgraded to \nLevel 4");
                break;
        }

    }
}
