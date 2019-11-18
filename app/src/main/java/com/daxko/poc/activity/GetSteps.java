package com.daxko.poc.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.daxko.poc.R;
import com.daxko.poc.interfaces.StepUpdateListener;
import com.daxko.poc.task.GetStepsCountTask;
import com.daxko.poc.utility.AppPrefs;
import com.daxko.poc.utility.CustomProgressbar;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessStatusCodes;
import com.google.android.gms.fitness.data.DataType;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GetSteps extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, StepUpdateListener {

    private static final String TAG = GetSteps.class.getSimpleName();
    private GoogleApiClient mGoogleApiClient;
    private static final int REQUEST_OAUTH = 1;
    TextView txtSteps;
    TextView txtFrom;
    TextView txtTo;
    ProgressBar pbSteps;
    ProgressBar pbFrom;
    ProgressBar pbTo;
    private boolean loginVisibility, logoutVisibility;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_steps);
        findids();

        connectGoogleClient();
    }

    private void findids() {
        txtSteps=findViewById(R.id.tvSteps);
        txtFrom=findViewById(R.id.txtFrom);
        txtTo=findViewById(R.id.txtTo);
        pbSteps=findViewById(R.id.progressBarSteps);
        pbFrom=findViewById(R.id.progressBarFrom);
        pbTo=findViewById(R.id.progressBarTo);
    }

//    @Override
//    protected int getViewId() {
//        return R.layout.activity_dashboard;
//    }
//
//    @Override
//    protected void activityOnCreate(@Nullable Bundle savedInstanceState) {
//        connectGoogleClient();
//    }

    @Override
    protected void onDestroy() {
        mGoogleApiClient.disconnect();
        super.onDestroy();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.menuLogin).setVisible(loginVisibility);
        menu.findItem(R.id.menuLogout).setVisible(logoutVisibility);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menuLogin:
                connectGoogleClient();
                return true;
            case R.id.menuLogout:
                signOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.i(TAG, "Google client: onConnected");
        // Disable login menu
        // Enable logout menu
        updateMenu(false, true);
        recordStepsActivity();
        getStepsCount();
    }

    /**
     * To connect GoogleClient
     * History API: To get steps count between two times
     * RECORDING API: To record user's steps count and store data in FIT store
     * CONFIG API: To enable/disable application with FIT
     */
    private void connectGoogleClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Fitness.HISTORY_API)
                .addApi(Fitness.RECORDING_API)
                .addApi(Fitness.CONFIG_API)
                .addScope(new Scope(Scopes.FITNESS_ACTIVITY_READ_WRITE))
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();
    }

    /**
     * To sign-out/disable client with fit api
     */
    private void signOut() {
        CustomProgressbar.showProgressBar(GetSteps.this, false);
        PendingResult<Status> statusPendingResult = Fitness.ConfigApi.disableFit(mGoogleApiClient);
        statusPendingResult.setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                CustomProgressbar.hideProgressBar();
                // Enable login menu if result succeeded else disable
                // Disable logout menu if result succeeded else enable
                txtSteps.setText("-");
                txtFrom.setText("-");
                txtTo.setText("-");
                updateMenu(status.isSuccess(), !status.isSuccess());
            }
        });
    }

    /**
     * This method handle menu's visibility according to user login and logout
     *
     * @param loginVisibility  - Login button should visible or not. True / false accordingly
     * @param logoutVisibility - Logout button should visible or not. True / false accordingly
     */
    private void updateMenu(boolean loginVisibility, boolean logoutVisibility) {
        this.loginVisibility = loginVisibility;
        this.logoutVisibility = logoutVisibility;
        invalidateOptionsMenu();
    }

    /**
     * Get steps count from FIT store between dates range
     * Start GetStepsCountTask once dates calculated and notify UI by interface
     */
    private void getStepsCount() {
        CustomProgressbar.showProgressBar(GetSteps.this, false);
        // If no last time stamp previously store in shared preference, means application opened first time so need to get last 30 days totalSteps count from Google FIT.
        // If last time stamp previously store in shared preference, means application opened next time so need to get totalSteps count from Google FIT from last time stamp to till now.
        if (AppPrefs.getLong(GetSteps.this) == 0) {
            Calendar cal = Calendar.getInstance();
            long endTime = cal.getTimeInMillis();

            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);

            cal.add(Calendar.MONTH, -1);
            long startTime = cal.getTimeInMillis();
            new GetStepsCountTask(mGoogleApiClient, startTime, endTime, this).execute();
        } else {
            long startTime = AppPrefs.getLong(GetSteps.this);
            Calendar cal = Calendar.getInstance();
            Date now = new Date();
            cal.setTime(now);
            long endTime = cal.getTimeInMillis();
            new GetStepsCountTask(mGoogleApiClient, startTime, endTime, this).execute();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Google client: onConnectionSuspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        try {
            connectionResult.startResolutionForResult(GetSteps.this, REQUEST_OAUTH);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_OAUTH) {
            if (resultCode == RESULT_OK) {
                if (!mGoogleApiClient.isConnecting() && !mGoogleApiClient.isConnected()) {
                    mGoogleApiClient.connect();
                }
            } else if (resultCode == RESULT_CANCELED) {
                Log.i(TAG, "Google client: RESULT_CANCELED");

            }
        }
    }

    /**
     * Create subscription for user's step
     * Start recording of user's steps and data will be stored in FIT store
     */
    private void recordStepsActivity() {
        // To create a subscription, invoke the Recording API. As soon as the subscription is
        // active, fitness data will start recording.
        Fitness.RecordingApi.subscribe(mGoogleApiClient, DataType.TYPE_STEP_COUNT_CUMULATIVE)
                .setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        if (status.isSuccess()) {
                            if (status.getStatusCode() == FitnessStatusCodes.SUCCESS_ALREADY_SUBSCRIBED) {
                                Log.i(TAG, "Existing subscription for activity detected.");
                            } else {
                                Log.i(TAG, "Successfully subscribed!");
                            }
                        } else {
                            Log.w(TAG, "There was a problem subscribing.");
                        }
                    }
                });
    }

    @Override
    public void onStepUpdate(int steps, long startTime, long endTime) {
        CustomProgressbar.hideProgressBar();
        AppPrefs.storeLong(GetSteps.this, endTime);
        txtSteps.setVisibility(View.VISIBLE);
        txtSteps.setText(String.valueOf(steps));
        pbSteps.setVisibility(View.GONE);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        txtFrom.setVisibility(View.VISIBLE);
        txtFrom.setText(dateFormat.format(startTime));
        pbFrom.setVisibility(View.GONE);
        txtTo.setVisibility(View.VISIBLE);
        txtTo.setText(dateFormat.format(endTime));
        pbTo.setVisibility(View.GONE);
    }

}
