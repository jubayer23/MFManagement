package com.creative.mahir_floral_management.view.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;

import com.creative.mahir_floral_management.R;
import com.creative.mahir_floral_management.databinding.ActivityReadystockBinding;
import com.creative.mahir_floral_management.view.fragment.ReadyStockFragment;

public class ReadyStockActivity extends BaseActivity {

    private static final String TAG = "ReadyStock_Fragment";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityReadystockBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_readystock);
        initToolbar();

        if (null != getActionBar())
            getActionBar().setHomeButtonEnabled(true);

        if (savedInstanceState == null) {

            ReadyStockFragment readyStockFragment = new ReadyStockFragment();
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction();
            transaction.replace(R.id.content_layout, readyStockFragment, TAG)
                    .commit();

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}