package com.daxko.poc;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.multidex.MultiDexApplication;

import com.daxko.poc.storage.AppStorage;
import com.squareup.picasso.Picasso;
import com.urbanairship.UAirship;
import com.urbanairship.push.notifications.DefaultNotificationFactory;
import com.zendesk.logger.Logger;
import com.zopim.android.sdk.api.ZopimChat;

import zendesk.answerbot.AnswerBot;
import zendesk.core.Zendesk;
import zendesk.support.Support;

public class MyApplication extends MultiDexApplication {

    public static Context context;
    public final static String LOG_TAG = "RTD";

    private AppStorage storage;

    public static AppStorage getStorage(@Nullable Context context) {
        if (context != null && context.getApplicationContext() instanceof MyApplication) {
            return ((MyApplication) context.getApplicationContext()).storage;
        }

        throw new IllegalArgumentException("Can't find global Application");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        storage = new AppStorage(this);

        Picasso.with(this).setLoggingEnabled(true);
        // Enable logging in Support and Chat SDK
        Logger.setLoggable(true);

        // Init Support SDK
        Zendesk.INSTANCE.init(this, getResources().getString(R.string.zd_url),
                getResources().getString(R.string.zd_appid),
                getResources().getString(R.string.zd_oauth));
        Support.INSTANCE.init(Zendesk.INSTANCE);
        AnswerBot.INSTANCE.init(Zendesk.INSTANCE, Support.INSTANCE);
        initUrbanAirship();

        // Init Chat SDK
        if ("replace_me_chat_account_id".equals(getString(R.string.zopim_account_id))) {
            Log.w(LOG_TAG, "==============================================================================================================");
            Log.w(LOG_TAG, "Zopim chat is not connected to an account, if you wish to try chat please add your Zopim accountId to 'zd.xml'");
            Log.w(LOG_TAG, "==============================================================================================================");
        }
        ZopimChat.init(getString(R.string.zopim_account_id));
    }

    private void initUrbanAirship() {

        // Initialize Urban Airship
        UAirship.takeOff(this);

        // Enable push notification
        UAirship.shared().getPushManager().setUserNotificationsEnabled(true);

        // 'ic_chat_bubble_outline_black_24dp' should be displayed as notification icon
        final DefaultNotificationFactory defaultNotificationFactory = new DefaultNotificationFactory(getApplicationContext());
        defaultNotificationFactory.setSmallIconId(R.drawable.ic_date);
        UAirship.shared().getPushManager().setNotificationFactory(defaultNotificationFactory);
    }




}
