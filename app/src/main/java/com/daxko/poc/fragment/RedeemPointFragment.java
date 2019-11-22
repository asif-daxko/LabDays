package com.daxko.poc.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class RedeemPointFragment extends Fragment implements View.OnClickListener {
    View view;
    TextView headerTextvw;
    RecyclerView rewardClaimRecyclerview;
    TextView textViewRedeemNow;
    ProgressDialog pd;
    Handler handler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_reward, container, false);

        // set up images
        int[] imagesArray = {R.mipmap.bag, R.mipmap.caps, R.mipmap.mug, R.mipmap.t_shirt};

        setUpdata(imagesArray);
        return view;
    }

    private void setUpdata(int[] imageArray) {
        headerTextvw = view.findViewById(R.id.header_textvw);
        rewardClaimRecyclerview = view.findViewById(R.id.reward_claim_recyclerview);
        textViewRedeemNow = view.findViewById(R.id.textView_redeem_now);

        handler = new Handler();
        pd = new ProgressDialog(getContext());
        pd.setMessage("Loading. Please wait...");
        GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        rewardClaimRecyclerview.setLayoutManager(manager);
        rewardClaimRecyclerview.setAdapter(new RewardClaimAdapter(getContext(), imageArray));

        // on click listener
        textViewRedeemNow.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textView_redeem_now:
                pd.show();

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showSuccessDialog();
                    }
                }, 3000);

                break;
        }
    }

    private void showSuccessDialog() {
        pd.dismiss();
        //Uncomment the below code to Set the message and title from the strings.xml file
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        //Setting message manually and performing action on button click
        builder.setMessage("Redeemed Successfully.")
                .setCancelable(false)
                .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                        loadFragment(new HomeFragment());
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Congratulations");
        alert.show();
    }

    private boolean loadFragment(Fragment fragment) {
        BottomNavigationView view = getActivity().findViewById(R.id.navigation);
        view.getMenu().getItem(0).setChecked(true);
        //switching fragment
        if (fragment != null) {
             getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }
}
