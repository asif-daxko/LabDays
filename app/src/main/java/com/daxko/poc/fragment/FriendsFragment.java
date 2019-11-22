package com.daxko.poc.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.daxko.poc.R;
import com.daxko.poc.activity.SearchFriendActivity;
import com.daxko.poc.adapter.FriendListAdapyter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FriendsFragment extends Fragment {
    View view,viewOverLay;
    RecyclerView recyclerView;
    TextView tvLeaderBoard;
    LinearLayout tvRank;
    SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.friend_list_fragment, container, false);

        setUpdata(view);
        return view;
    }

    private void setUpdata(View view) {
        recyclerView=view.findViewById(R.id.recyclerView);
        tvRank=view.findViewById(R.id.rlRank);
        searchView=view.findViewById(R.id.searchView);
        tvLeaderBoard=view.findViewById(R.id.tvLeaderBoard);
        viewOverLay=view.findViewById(R.id.view);
        tvRank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//              Toast.makeText(getActivity(),"Rank",Toast.LENGTH_LONG).show();
            }
        });
        tvLeaderBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Leader Board",Toast.LENGTH_LONG).show();
            }
        });
         viewOverLay.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent=new Intent(getActivity(), SearchFriendActivity.class);
                 startActivityForResult(intent,102);
             }
         });
                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.setAdapter(new FriendListAdapyter(getActivity(), Arrays.asList("Clark Miller","Rodriguez Wilson","Lewis Davis","Lee Brown","Walker Jones", "Allen Jones","Hernandez Davis","Wright Davis","Lopez Williams","Gonzalez Johnson","Nelson Williams","Carter Smith","Garcia Johnson")) );
    }

    public void updateFriendList(ArrayList<String> newFriendList){
       List<String> previousList=new ArrayList<String>();
        previousList.add("Clark Miller");
        previousList.add("Rodriguez Wilson");
        previousList.add("Lewis Davis");
        previousList.add("Lee Brown");
        previousList.add("Walker Jones");
        previousList.add("Allen Jones");
        previousList.add("Hernandez Davis");
        previousList.add("Wright Davis");
        previousList.add("Lopez Williams");
        previousList.add("Gonzalez Johnson");
        previousList.add("Nelson Williams");
        previousList.add("Carter Smith");
        previousList.add("Garcia Johnson");
       for(String s:newFriendList){
           previousList.add(s);
       }
        recyclerView.setAdapter(new FriendListAdapyter(getActivity(),previousList));
    }
}
