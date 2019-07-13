package com.creative.mahir_floral_management.view.activity;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.fragment.app.FragmentTransaction;

import com.creative.mahir_floral_management.R;
import com.creative.mahir_floral_management.databinding.ActivityAdminMenuBinding;
import com.creative.mahir_floral_management.view.fragment.AdminMenuFragment;

public class AdminMenuActivity extends BaseActivity {

    private static final String TAG_ADMIN_FRAGMENT = "admin_fragment";
    private AdminMenuFragment adminMenuFragment;

    private ActivityAdminMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_hq);


        binding = DataBindingUtil.setContentView(AdminMenuActivity.this, R.layout.activity_admin_menu);

        initToolbar("Admin Menu", true);

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


            adminMenuFragment = new AdminMenuFragment();
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction();
            transaction.replace(R.id.content_layout, adminMenuFragment, TAG_ADMIN_FRAGMENT)
                    .commit();


        }
    }
}
