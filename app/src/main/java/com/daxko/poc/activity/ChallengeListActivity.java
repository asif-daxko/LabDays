package com.daxko.poc.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.view.View;

import com.daxko.poc.BuildConfig;
import com.daxko.poc.MyApplication;
import com.daxko.poc.R;
import com.daxko.poc.adapter.ChallengesAdapter;
import com.daxko.poc.customViews.CustomAlertDialog;
import com.daxko.poc.interfaces.ChallengeClickListener;
import com.daxko.poc.interfaces.CustomAlertDialogListener;
import com.daxko.poc.modelData.UserProfile;
import com.daxko.poc.push.Global;
import com.daxko.poc.push.PushUtils;
import com.daxko.poc.storage.AppStorage;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.zendesk.service.ErrorResponse;
import com.zendesk.service.ZendeskCallback;
import com.zendesk.util.FileUtils;
import com.zendesk.util.StringUtils;
import com.zopim.android.sdk.api.ZopimChat;
import com.zopim.android.sdk.model.VisitorInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import zendesk.answerbot.AnswerBot;
import zendesk.answerbot.AnswerBotActivity;
import zendesk.commonui.UiConfig;
import zendesk.core.AnonymousIdentity;
import zendesk.core.Identity;
import zendesk.core.Zendesk;
import zendesk.support.CustomField;
import zendesk.support.Support;
import zendesk.support.guide.HelpCenterActivity;
import zendesk.support.request.RequestActivity;
import zendesk.support.requestlist.RequestListActivity;

public class ChallengeListActivity extends AppCompatActivity implements View.OnClickListener, ChallengeClickListener, CustomAlertDialogListener {
    RecyclerView challengesRecyclerview;
    private static final long TICKET_FORM_ID = 62599L;
    private static final long TICKET_FIELD_APP_VERSION = 24328555L;
    private static final long TICKET_FIELD_DEVICE_FREE_SPACE = 24274009L;

    private static final int POS_DATE_LIST = 0;
    private static final int POS_PROFILE = 1;
    public static final int POS_HELP = 2;

    public static final String EXTRA_VIEWPAGER_POSITION = "extra_viewpager_pos";
    private AppStorage storage;
    private CustomAlertDialog mCustomAlertDialog;
    private FloatingActionButton customerSupportBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_list);

        storage = MyApplication.getStorage(getApplicationContext());
        initialiseChatSdk();


        Zendesk.INSTANCE.init(this, "https://labdays.zendesk.com",
                "95b57579c9dc8b374b411f9484eff001ae181ed6f0260e57",
                "mobile_sdk_client_b0507da732681fbba154");

        //Identity identity = new AnonymousIdentity();
        Identity identity = new AnonymousIdentity.Builder()
                .withNameIdentifier("John Bob")
                .withEmailIdentifier("johnbob@yopmail.com")
                .build();
        Zendesk.INSTANCE.setIdentity(identity);

        Support.INSTANCE.init(Zendesk.INSTANCE);
        AnswerBot.INSTANCE.init(Zendesk.INSTANCE, Support.INSTANCE);

        Zendesk.INSTANCE.provider().pushRegistrationProvider().registerWithUAChannelId("<channel id>", new ZendeskCallback<String>() {
            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onError(ErrorResponse errorResponse) {

            }
        });


        ChallengeListActivity.this.setTitle("Challenges");

        setUpData();
    }

    private void setUpData() {
        challengesRecyclerview=findViewById(R.id.challenges_recyclerview);
        challengesRecyclerview.setAdapter(new ChallengesAdapter(ChallengeListActivity.this,this));
        customerSupportBtn = (FloatingActionButton) findViewById(R.id.btn_customer_support);
        customerSupportBtn.setOnClickListener(this);
        /*customerSupportBtn.setOnClickListener(new LoggedInClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChallengeListActivity.this.openHelpCenter(ChallengeListActivity.this);
            }
        }));*/
    }

    @Override
    public void OnItemClick(View v, int position) {
        startActivity(new Intent(ChallengeListActivity.this, ChallengeDetailActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        //PushUtils.checkPlayServices(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_customer_support:
                mCustomAlertDialog = new CustomAlertDialog(this, this, "");
                mCustomAlertDialog.show();
                break;
        }
    }

    private void openHelpCenter(Context context) {

       /* AnswerBotActivity.builder()
                .show(context);
*/
        UiConfig config = RequestActivity.builder()
                .withTicketForm(TICKET_FORM_ID, getCustomFields())
                .config();

        HelpCenterActivity.builder()
                .show(context, config);
    }

    private List<CustomField> getCustomFields() {
        final String appVersion = String.format(
                Locale.US,
                "version_%s",
                BuildConfig.VERSION_NAME
        );

        final StatFs stat = new StatFs(Environment.getDataDirectory().getPath());
        final long bytesAvailable = (long) stat.getBlockSize() * (long) stat.getAvailableBlocks();
        final String freeSpace = FileUtils.humanReadableFileSize(bytesAvailable);


        final List<CustomField> customFields = new ArrayList<>();
        customFields.add(new CustomField(TICKET_FIELD_APP_VERSION, appVersion));
        customFields.add(new CustomField(TICKET_FIELD_DEVICE_FREE_SPACE, freeSpace));

        return customFields;
    }

    @Override
    public void knowledgeBaseClickHandler() {
        mCustomAlertDialog.dismiss();
        openHelpCenter(ChallengeListActivity.this);
    }

    @Override
    public void contactUsClickHandler() {
        mCustomAlertDialog.dismiss();
        RequestActivity.builder()
                .withTicketForm(TICKET_FORM_ID, getCustomFields())
                .show(ChallengeListActivity.this);
    }

    @Override
    public void myTicketsClickHandler() {
        mCustomAlertDialog.dismiss();
        UiConfig config = RequestActivity.builder()
                .withTicketForm(TICKET_FORM_ID, getCustomFields())
                .config();

        RequestListActivity.builder()
                .show(ChallengeListActivity.this, config);

    }

    @Override
    public void answerBotClickHandler() {
        mCustomAlertDialog.dismiss();
        AnswerBotActivity.builder()
                .show(ChallengeListActivity.this);
    }


    private void initialiseChatSdk() {
        final UserProfile profile = storage.getUserProfile();
        if (StringUtils.hasLength(profile.getEmail())) {
            // Init Zopim Visitor info
            final VisitorInfo.Builder build = new VisitorInfo.Builder()
                    .email(profile.getEmail());

            if (StringUtils.hasLength(profile.getName())) {
                build.name(profile.getName());
            }

            ZopimChat.setVisitorInfo(build.build());
        }
    }
}
