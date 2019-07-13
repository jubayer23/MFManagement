package com.creative.mahir_floral_management.view.activity;

import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import com.creative.mahir_floral_management.R;
import com.creative.mahir_floral_management.databinding.ActivityReadyentrystockBinding;
import com.creative.mahir_floral_management.view.fragment.ReadyStockEntryFragment;

public class ReadyStockEntryActivity extends BaseActivity {

    private static final String TAG = "EntryReadyStock_Fragment";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityReadyentrystockBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_readyentrystock);
        initToolbar();

        if (null != getActionBar())
            getActionBar().setHomeButtonEnabled(true);

        if (savedInstanceState == null) {

            ReadyStockEntryFragment readyStockEntryFragment = new ReadyStockEntryFragment();
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction();
            transaction.replace(R.id.content_layout, readyStockEntryFragment, TAG)
                    .commit();

        }

    }
}
