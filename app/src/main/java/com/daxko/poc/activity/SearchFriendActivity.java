package com.daxko.poc.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import com.daxko.poc.R;
import com.daxko.poc.adapter.AddFriendAdapter;
import com.daxko.poc.adapter.FriendListAdapyter;
import com.daxko.poc.adapter.InviteAdapter;
import com.daxko.poc.modelData.AddFriendModel;
import com.daxko.poc.modelData.InviteData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchFriendActivity extends AppCompatActivity {

    RecyclerView rvInvite,rvAddFriend;
    TextView submit,notFound,participient;
    ProgressBar progressBar;
    SearchView searchView;
    AddFriendAdapter addFriendAdapter;
    List<InviteData> dataList;
    List<AddFriendModel> addFriendList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_friend);
        submit=findViewById(R.id.submit);
        rvInvite=findViewById(R.id.rvInvite);
        rvAddFriend=findViewById(R.id.rvFriend);
        notFound=findViewById(R.id.noMatchFound);
        participient=findViewById(R.id.participient);
        progressBar=findViewById(R.id.progressBar);
        searchView=findViewById(R.id.searchView);
        inviteData();
        friendData();
        rvInvite.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        rvInvite.setAdapter(new InviteAdapter(this, dataList));

        addFriendAdapter= new AddFriendAdapter(SearchFriendActivity.this, addFriendList,addFriendList);
        rvAddFriend.setLayoutManager(new LinearLayoutManager(SearchFriendActivity.this,LinearLayoutManager.VERTICAL,false));
        rvAddFriend.setAdapter(addFriendAdapter);

        submit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(rvInvite.getVisibility()==View.VISIBLE){
                   final List<String> numbersList=new ArrayList<>();
                   for (InviteData inviteData:dataList)
                   {
                       if(inviteData.isInvite()){
                           numbersList.add(inviteData.getNumber());
                       }
                   }
                   if(numbersList.size()>0){
                       progressBar.setVisibility(View.VISIBLE);
                       new Handler().postDelayed(new Runnable() {
                           @Override
                           public void run() {
                               progressBar.setVisibility(View.GONE);
                               inviteFriends(numbersList);
                           }
                       },1500);

                   }else {
                       Toast.makeText(SearchFriendActivity.this,"Please select contacts to Invite",Toast.LENGTH_LONG).show();
                   }
               }else if(rvAddFriend.getVisibility()==View.VISIBLE){
                    final ArrayList<String> addRequestList=new ArrayList<>();
                    for(AddFriendModel addFriendModel:addFriendList){
                        if(addFriendModel.isAdd()){
                          addRequestList.add(addFriendModel.getName());
                        }
                    }

                   if(addRequestList.size()>0){
                       progressBar.setVisibility(View.VISIBLE);
                       new Handler().postDelayed(new Runnable() {
                           @Override
                           public void run() {
                              progressBar.setVisibility(View.GONE);
                              Intent intent=new Intent();
                              intent.putStringArrayListExtra("requestFriend",addRequestList);
                              setResult(102,intent);
                              finish();
                           }
                       },1500);

                   }else {
                       Toast.makeText(SearchFriendActivity.this,"Please select contacts to Add Friend",Toast.LENGTH_LONG).show();
                   }

               }

           }
       });
  searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override
      public boolean onQueryTextSubmit(final String query) {
          if(query.length()>2){
              progressBar.setVisibility(View.VISIBLE);
              rvInvite.setVisibility(View.GONE);
              rvAddFriend.setVisibility(View.GONE);
              notFound.setVisibility(View.GONE);
              participient.setVisibility(View.GONE);
              new Handler().postDelayed(new Runnable() {
                  @Override
                  public void run() {
                      addFriendAdapter.getFilter().filter(query);
                      new Handler().postDelayed(new Runnable() {
                          @Override
                          public void run() {
                              if(addFriendAdapter.getCurrentList().size()<1){
                                  notFound.setVisibility(View.VISIBLE);
                                  participient.setVisibility(View.GONE);
                                  progressBar.setVisibility(View.GONE);
                                  rvInvite.setVisibility(View.GONE);
                                  rvAddFriend.setVisibility(View.GONE);
                              }else {
                                  participient.setVisibility(View.GONE);
                                  notFound.setVisibility(View.GONE);
                                  progressBar.setVisibility(View.GONE);
                                  rvAddFriend.setVisibility(View.VISIBLE);
                                  rvInvite.setVisibility(View.GONE);
                              }

                          }
                      },30);
                  }
              },1500);

          }else if(query.equals("")){
              addFriendAdapter.getFilter().filter(query);
              participient.setVisibility(View.VISIBLE);
              rvInvite.setVisibility(View.VISIBLE);
              rvAddFriend.setVisibility(View.GONE);
              notFound.setVisibility(View.GONE);
          }
          return false;
      }

      @Override
      public boolean onQueryTextChange(final String newText) {
          if(newText.length()>2){
              progressBar.setVisibility(View.VISIBLE);
              rvInvite.setVisibility(View.GONE);
              rvAddFriend.setVisibility(View.GONE);
              notFound.setVisibility(View.GONE);
              participient.setVisibility(View.GONE);
              new Handler().postDelayed(new Runnable() {
                  @Override
                  public void run() {
                      addFriendAdapter.getFilter().filter(newText);
                      new Handler().postDelayed(new Runnable() {
                          @Override
                          public void run() {
                              if(addFriendAdapter.getCurrentList().size()<1){
                                  notFound.setVisibility(View.VISIBLE);
                                  participient.setVisibility(View.GONE);
                                  progressBar.setVisibility(View.GONE);
                                  rvInvite.setVisibility(View.GONE);
                                  rvAddFriend.setVisibility(View.GONE);
                              }else {
                                  participient.setVisibility(View.GONE);
                                  notFound.setVisibility(View.GONE);
                                  progressBar.setVisibility(View.GONE);
                                  rvAddFriend.setVisibility(View.VISIBLE);
                                  rvInvite.setVisibility(View.GONE);
                              }

                          }
                      },30);
                  }
              },1500);

          }else if(newText.equals("")){
              addFriendAdapter.getFilter().filter(newText);
              participient.setVisibility(View.VISIBLE);
              rvInvite.setVisibility(View.VISIBLE);
              rvAddFriend.setVisibility(View.GONE);
              notFound.setVisibility(View.GONE);
          }
          return false;
      }
  });
    }
    public void inviteData(){
      dataList=new ArrayList<>();
      InviteData inviteData1=new InviteData();
      inviteData1.setName("Smith");
      inviteData1.setNumber("9867876579");
        InviteData inviteData2=new InviteData();
        inviteData2.setName("Johnson");
        inviteData2.setNumber("986789999");

        InviteData inviteData3=new InviteData();
        inviteData3.setName("Williams");
        inviteData3.setNumber("9867833339");

        InviteData inviteData4=new InviteData();
        inviteData4.setName("Steven Scott");
        inviteData4.setNumber("9866663339");

        InviteData inviteData5=new InviteData();
        inviteData5.setName("Jackson");
        inviteData5.setNumber("638865793");

        InviteData inviteData6=new InviteData();
        inviteData6.setName("Thompson");
        inviteData6.setNumber("638885793");

        InviteData inviteData7=new InviteData();
        inviteData7.setName("Robinson");
        inviteData7.setNumber("6388888793");

        InviteData inviteData8=new InviteData();
        inviteData8.setName("Taylor");
        inviteData8.setNumber("6388888798");

        InviteData inviteData9=new InviteData();
        inviteData9.setName("Moore");
        inviteData9.setNumber("6388888797");

        InviteData inviteData10=new InviteData();
        inviteData10.setName("Davis");
        inviteData10.setNumber("6388888799");


        dataList.add(inviteData1);
        dataList.add(inviteData2);
        dataList.add(inviteData3);
        dataList.add(inviteData4);
        dataList.add(inviteData5);
        dataList.add(inviteData6);
        dataList.add(inviteData7);
        dataList.add(inviteData8);
        dataList.add(inviteData9);
        dataList.add(inviteData10);
    }

    public void friendData(){
        addFriendList=new ArrayList<>();
        AddFriendModel addFriendModel1=new AddFriendModel("Jay","315",false);
        AddFriendModel addFriendModel2=new AddFriendModel("Scott Stevens","316",false);
        AddFriendModel addFriendModel3=new AddFriendModel("Leonid","317",false);
        AddFriendModel addFriendModel4=new AddFriendModel("Sarah","318",false);
        AddFriendModel addFriendModel5=new AddFriendModel("Jordan","319",false);
        addFriendList.add(addFriendModel1);
        addFriendList.add(addFriendModel2);
        addFriendList.add(addFriendModel3);
        addFriendList.add(addFriendModel4);
        addFriendList.add(addFriendModel5);
    }

    public void inviteFriends(List<String> contacts){
        String toNumbers = "";
        for ( String number : contacts)
        {
            toNumbers = toNumbers + number + ";";
        }
        toNumbers = toNumbers.substring(0, toNumbers.length() - 1);

        Intent it = new Intent(Intent.ACTION_SENDTO,Uri.parse("smsto:"+toNumbers));
        it.putExtra("sms_body", "Hey check out my app at: https://play.google.com/store/apps/details?id=com.daxko.mobile&hl=en");
        startActivity(it);
        finish();
    }
}
