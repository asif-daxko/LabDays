package com.daxko.poc.fragment;

import android.content.Intent;
import android.content.IntentSender;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.daxko.poc.R;
import com.daxko.poc.activity.ChallengeDetailActivity;
import com.daxko.poc.activity.FitnessRecordActivity;
import com.daxko.poc.activity.LevelDescriptionActivity;
import com.daxko.poc.adapter.CardAdapter;
import com.daxko.poc.interfaces.ChallengeClickListener;
import com.daxko.poc.utility.AppPrefs;
import com.daxko.poc.utility.GridSpacingItemDecoration;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.data.Value;
import com.google.android.gms.fitness.request.DataSourcesRequest;
import com.google.android.gms.fitness.request.OnDataPointListener;
import com.google.android.gms.fitness.request.SensorRequest;
import com.google.android.gms.fitness.result.DailyTotalResult;
import com.google.android.gms.fitness.result.DataSourcesResult;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class HomeFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, OnDataPointListener, ChallengeClickListener {

    View view;
    TextView timeTxtvw, txtSteps, coinTextvw;
    TextView textView10,textView11;
    TextView startSeekbar, seekbarEnd;
    RecyclerView cardsRecyclerview;
    ConstraintLayout levelCard;
    SeekBar seekBar;
    Button button;
    private static final String TAG = ChallengeDetailActivity.class.getSimpleName();
    private GoogleApiClient mGoogleApiClient;
    private static final int REQUEST_OAUTH = 1;
    int originalvalue = 44241;
    private boolean loginVisibility, logoutVisibility;
    private static final String AUTH_PENDING = "auth_state_pending";
    private boolean authInProgress = false;
    int[] imagesArray = {R.drawable.place_marker, R.drawable.fire, R.drawable.icons_sand_clock, R.drawable.coin_blacknwhite};


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.home_layout, container, false);

        if (savedInstanceState != null) {
            authInProgress = savedInstanceState.getBoolean(AUTH_PENDING);
        }
        setData();
        return view;
    }

    private void setData() {
        textView10 = view.findViewById(R.id.textView10);
        textView11 = view.findViewById(R.id.textView11);
        seekBar = view.findViewById(R.id.seekBar);
        timeTxtvw = view.findViewById(R.id.time_txtvw);
        txtSteps = view.findViewById(R.id.txtSteps);
        startSeekbar = view.findViewById(R.id.start_seekbar);
        seekbarEnd = view.findViewById(R.id.seekbar_end);
        coinTextvw = view.findViewById(R.id.textView8);
        cardsRecyclerview = view.findViewById(R.id.cards_recyclerview);
        levelCard = view.findViewById(R.id.level_card);
        button=view.findViewById(R.id.button);

        connectGoogleClient();
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        sdf.format(currentTime);
        timeTxtvw.setText("Updated at " + sdf.format(currentTime));
        double distancecovered=(float) (AppPrefs.getInstance(getActivity()).getSteps())*0.000762;
        double calorieburned=(float) (AppPrefs.getInstance(getActivity()).getSteps())*0.5;
        double[] fitData = {distancecovered,calorieburned,AppPrefs.getInstance(getActivity()).getCoins(),
                AppPrefs.getInstance(getActivity()).getCoins()};

        cardsRecyclerview.addItemDecoration(new GridSpacingItemDecoration(20,4));
        cardsRecyclerview.setAdapter(new CardAdapter(getActivity(), this, imagesArray,fitData));

        seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        levelCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), LevelDescriptionActivity.class));
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent =   new Intent(android.content.Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT,"Daxko Reward System");
                String app_url = "https://play.google.com/store/apps/details?id=com.daxko.mobile&hl=en";
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,"Invitation Code: FgKY7847  "+app_url);
                startActivity(Intent.createChooser(shareIntent, "Share via"));
            }
        });
    }


    private void connectGoogleClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(Fitness.SENSORS_API)
                .addScope(new Scope(Scopes.FITNESS_ACTIVITY_READ_WRITE))
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        DataSourcesRequest dataSourceRequest = new DataSourcesRequest.Builder()
                .setDataTypes(DataType.TYPE_STEP_COUNT_CUMULATIVE)
                .setDataSourceTypes(DataSource.TYPE_RAW)
                .build();

        ResultCallback<DataSourcesResult> dataSourcesResultCallback = new ResultCallback<DataSourcesResult>() {
            @Override
            public void onResult(DataSourcesResult dataSourcesResult) {
                for (DataSource dataSource : dataSourcesResult.getDataSources()) {
                    if (DataType.TYPE_STEP_COUNT_CUMULATIVE.equals(dataSource.getDataType())) {
                        registerFitnessDataListener(dataSource, DataType.TYPE_STEP_COUNT_CUMULATIVE);
                    }
                }
            }
        };

        Fitness.SensorsApi.findDataSources(mGoogleApiClient, dataSourceRequest)
                .setResultCallback(dataSourcesResultCallback);
    }

    private void registerFitnessDataListener(DataSource dataSource, DataType dataType) {
        SensorRequest request = new SensorRequest.Builder()
                .setDataSource(dataSource)
                .setDataType(dataType)
                .setSamplingRate(3, TimeUnit.SECONDS)
                .build();

        Fitness.SensorsApi.add(mGoogleApiClient, request, this)
                .setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        if (status.isSuccess()) {
                            Log.e("GoogleFit", "SensorApi successfully added");
                        }
                    }
                });
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Google client: onConnectionSuspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (!authInProgress) {
            try {
                authInProgress = true;
                connectionResult.startResolutionForResult(getActivity(), REQUEST_OAUTH);
            } catch (IntentSender.SendIntentException e) {

            }
        } else {
            Log.e("GoogleFit", "authInProgress");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_OAUTH) {
            authInProgress = false;
            if (resultCode == RESULT_OK) {
                if (!mGoogleApiClient.isConnecting() && !mGoogleApiClient.isConnected()) {
                    mGoogleApiClient.connect();
                }
            } else if (resultCode == RESULT_CANCELED) {
                Log.e("GoogleFit", "RESULT_CANCELED");
            }
        } else {
            Log.e("GoogleFit", "requestCode NOT request_oauth");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    private int step_value;

    @Override
    public void onDataPoint(DataPoint dataPoint) {
        for (final Field field : dataPoint.getDataType().getFields()) {
            final Value value = dataPoint.getValue(field);
            step_value = Integer.parseInt(value + "");

            Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (step_value == 0) {
                        return;
                    }

                    coinCalculation(step_value);
//                    float steps_today = step_value - originalvalue;
                    //float steps_today = step_value;

                    //Toast.makeText(getApplicationContext(), "Field: " + field.getName() + " Value: " + value, Toast.LENGTH_SHORT).show();
                }
            });
        }
        AppPrefs.getInstance(getActivity()).setCoins((int)coins);

        }
    int coins;
    private void coinCalculation(int step_value) {
         coins = step_value >= 1000 ? (int) step_value / 1000 : 0;
        if (coins > 10 && coins <= 20) {
            updateLevels( 2,  "Reach 20 coin, 3 day in arow to reach LEVEL 2.");
            updateSeekbar(10, 20);
        } else if (coins > 20 && coins <= 30) {
            updateLevels( 3,  "Reach 30 coin, 3 day in arow to reach LEVEL 3.");
            updateSeekbar(20, 30);
        } else if (coins > 30 && coins <= 40) {
            updateLevels( 4,  "Reach 40 coin, 3 day in arow to reach LEVEL 4.");
            updateSeekbar(30, 40);
        } else if (coins > 40 && coins <= 50) {
            updateLevels( 5,  "Reach 50 coin, 3 day in arow to reach LEVEL 5.");
            updateSeekbar(40, 50);
        } else {
            updateLevels( 1,  "Reach 10 coin, 3 day in arow to reach LEVEL 2.");
            updateSeekbar(0, 10);
        }
        seekBar.setProgress(coins);
        coinTextvw.setText(coins + "");
        txtSteps.setText(step_value + "");
        AppPrefs.getInstance(getActivity()).setSteps(step_value);
    }

    private void updateSeekbar(int progressstart, int progressend) {
        seekBar.setMax(progressend);
        startSeekbar.setText(progressstart + "");
        seekbarEnd.setText(progressend + "");
    }

    @Override
    public void onStop() {
        super.onStop();

        Fitness.SensorsApi.remove(mGoogleApiClient, this)
                .setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        if (status.isSuccess()) {
                            mGoogleApiClient.disconnect();
                        }
                    }
                });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(AUTH_PENDING, authInProgress);
    }

    @Override
    public void OnItemClick(View v, int position) {
        Intent intent = new Intent(getActivity(), FitnessRecordActivity.class);
        intent.putExtra("pos", position);
        startActivity(intent);
    }

    private class FetchCalorieAsync extends AsyncTask<Object, Object, Long> {
        protected Long doInBackground(Object... params) {
            long total = 0;
            PendingResult<DailyTotalResult> result = Fitness.HistoryApi.readDailyTotal(mGoogleApiClient, DataType.TYPE_CALORIES_EXPENDED);
            DailyTotalResult totalResult = result.await(30, TimeUnit.SECONDS);
            if (totalResult.getStatus().isSuccess()) {
                DataSet totalSet = totalResult.getTotal();
                if (totalSet != null) {
                    total = totalSet.isEmpty()
                            ? 0
                            : totalSet.getDataPoints().get(0).getValue(Field.FIELD_CALORIES).asInt();
                }
            } else {
                Log.w(TAG, "There was a problem getting the calories.");
            }
            return total;
        }


        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
            //Total calories burned for that day
            Log.i(TAG, "Total calories: " + aLong);

        }
    }

    public void updateLevels(int level, String levelDescription){
        AppPrefs.getInstance(getContext()).setLevel(level);
        textView10.setText("Level "+level);
        textView11.setText(levelDescription);
    }
}
