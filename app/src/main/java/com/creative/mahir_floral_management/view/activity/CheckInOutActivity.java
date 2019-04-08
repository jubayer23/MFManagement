package com.creative.mahir_floral_management.view.activity;

import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.creative.mahir_floral_management.R;
import com.creative.mahir_floral_management.databinding.ActivityCheckInOutBinding;
import com.creative.mahir_floral_management.databinding.ActivityHqBinding;
import com.creative.mahir_floral_management.view.fragment.CheckInOutFragment;
import com.creative.mahir_floral_management.view.fragment.HQFragment;

public class CheckInOutActivity extends BaseActivity {


    private static final String TAG_CHECK_FRAGMENT = "check_fragment";
    private CheckInOutFragment checkInOutFragment;

    private ActivityCheckInOutBinding activityCheckInOutBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_check_in_out);

        activityCheckInOutBinding = DataBindingUtil.setContentView(CheckInOutActivity.this, R.layout.activity_check_in_out);

        initToolbar("Check In-Out", true);

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


            checkInOutFragment = new CheckInOutFragment();
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction();
            transaction.replace(R.id.content_layout, checkInOutFragment, TAG_CHECK_FRAGMENT)
                    .commit();


        }
    }
}
