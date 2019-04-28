package com.creative.mahir_floral_management.view.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;

import com.creative.mahir_floral_management.R;
import com.creative.mahir_floral_management.databinding.ActivityRawstockBinding;
import com.creative.mahir_floral_management.databinding.ActivityStaffRegistrationBinding;
import com.creative.mahir_floral_management.view.fragment.RawStockFragment;
import com.creative.mahir_floral_management.view.fragment.StaffRegistrationFragment;

public class StaffRegistrationActivity extends BaseActivity {

    private static final String TAG = "staff registration fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityStaffRegistrationBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_staff_registration);


        initToolbar("Staff Registration", true);



        if (savedInstanceState == null) {

            StaffRegistrationFragment staffRegistrationFragment = new StaffRegistrationFragment();
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction();
            transaction.replace(R.id.content_layout, staffRegistrationFragment, TAG)
                    .commit();

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
