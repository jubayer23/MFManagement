package com.creative.mahir_floral_management.view.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;

import com.creative.mahir_floral_management.R;
import com.creative.mahir_floral_management.databinding.ActivityReadyentrystockBinding;
import com.creative.mahir_floral_management.view.fragment.EntryRawStockFragment;
import com.creative.mahir_floral_management.view.fragment.EntryReadyStockFragment;

public class EntryReadyStockActivity extends BaseActivity {

    private static final String TAG = "EntryReadyStock_Fragment";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityReadyentrystockBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_readyentrystock);
        initToolbar();

        if (null != getActionBar())
            getActionBar().setHomeButtonEnabled(true);

        if (savedInstanceState == null) {

            EntryReadyStockFragment entryReadyStockFragment = new EntryReadyStockFragment();
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction();
            transaction.replace(R.id.content_layout, entryReadyStockFragment, TAG)
                    .commit();

        }

    }
}