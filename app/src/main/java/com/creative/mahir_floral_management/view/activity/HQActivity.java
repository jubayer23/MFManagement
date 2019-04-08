package com.creative.mahir_floral_management.view.activity;

import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.creative.mahir_floral_management.R;
import com.creative.mahir_floral_management.Utility.CheckLocationEnableStatus;
import com.creative.mahir_floral_management.Utility.GpsEnableTool;
import com.creative.mahir_floral_management.databinding.ActivityHqBinding;
import com.creative.mahir_floral_management.view.fragment.HQFragment;
import com.creative.mahir_floral_management.view.fragment.HomeFragment;

public class HQActivity extends BaseActivity {

    private static final String TAG_HQ_FRAGMENT = "hq_fragment";
    private HQFragment hqFragment;

    private ActivityHqBinding activityHqBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_hq);


        activityHqBinding = DataBindingUtil.setContentView(HQActivity.this, R.layout.activity_hq);

        initToolbar();

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


            hqFragment = new HQFragment();
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction();
            transaction.replace(R.id.content_layout, hqFragment, TAG_HQ_FRAGMENT)
                    .commit();


        }
    }
}
