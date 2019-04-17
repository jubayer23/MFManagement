package com.creative.mahir_floral_management.view.activity;

import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;

import com.creative.mahir_floral_management.R;
import com.creative.mahir_floral_management.databinding.ActivityTimeSheetsBinding;
import com.creative.mahir_floral_management.view.fragment.TimeSheetFragment;

public class TimeSheetActivity extends BaseActivity {

    private static final String TAG_TIMESHEET_FRAGMENT = "timesheet_fragment";
    private TimeSheetFragment timeSheetFragment;

    private ActivityTimeSheetsBinding activityTimeSheetsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_hq);


        activityTimeSheetsBinding = DataBindingUtil.setContentView(TimeSheetActivity.this, R.layout.activity_time_sheets);

        initToolbar("Time Sheets", true);

        if (savedInstanceState == null) {

            /**
             * This is marshmallow runtime Permissions
             * It will ask user for grand permission in queue order[FIFO]
             * If user gave all permission then check whether user device has google play service or not!
             * NB : before adding runtime request for permission Must add manifest permission for that
             * specific request
             * */


            //getSupportFragmentManager()
            //        .beginTransaction()
            //        .add(R.id.content_layout, new HouseListFragment(), TAG_HOME_FRAGMENT)
            //        .commit();


            timeSheetFragment = new TimeSheetFragment();
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction();
            transaction.replace(R.id.content_layout, timeSheetFragment, TAG_TIMESHEET_FRAGMENT)
                    .commit();


        }else{
            timeSheetFragment = (TimeSheetFragment) getSupportFragmentManager().findFragmentByTag(TAG_TIMESHEET_FRAGMENT);
        }
    }
}
