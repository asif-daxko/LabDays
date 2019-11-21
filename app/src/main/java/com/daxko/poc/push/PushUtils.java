package com.daxko.poc.push;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.daxko.poc.MyApplication;
import com.daxko.poc.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;

import com.zendesk.util.StringUtils;

import zendesk.core.ProviderStore;
import zendesk.core.Zendesk;

import static com.urbanairship.UAirship.getApplicationContext;
import static zendesk.support.guide.HelpCenterFragment.LOG_TAG;

public class PushUtils {

    /**
     * Check if play services are installed and use able.
     *
     * @param activity An activity
     */
    public static void checkPlayServices(Activity activity) {
        final GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int errorCode = apiAvailability.isGooglePlayServicesAvailable(activity);
        if (errorCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(errorCode)) {
                apiAvailability.makeGooglePlayServicesAvailable(activity);
            } else {
                Toast.makeText(activity, "Your device doesn't support push notifications", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static void registerWithZendesk() {
        final ProviderStore providerStore = Zendesk.INSTANCE.provider();

        if (providerStore == null) {
            Log.e(LOG_TAG, "Zendesk Support SDK is not initialized");
            return;
        }

        final String pushToken = FirebaseInstanceId.getInstance().getToken();
        if (StringUtils.hasLength(pushToken)) {
            providerStore.pushRegistrationProvider().registerWithDeviceIdentifier(pushToken, null);
        }
    }
}


