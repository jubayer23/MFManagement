package com.creative.mahir_floral_management.view.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;

import com.creative.mahir_floral_management.R;
import com.creative.mahir_floral_management.databinding.ActivityEnteryrawstockBinding;
import com.creative.mahir_floral_management.view.fragment.RawStockEntryFragment;

public class EntryRawStockActivity extends BaseActivity {

    private static final String TAG = "EntryRawStock_Fragment";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityEnteryrawstockBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_enteryrawstock);
        initToolbar();

        if (null != getActionBar())
            getActionBar().setHomeButtonEnabled(true);

        if (savedInstanceState == null) {

            RawStockEntryFragment rawStockFragment = new RawStockEntryFragment();
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction();
            transaction.replace(R.id.content_layout, rawStockFragment, TAG)
                    .commit();

        }

    }
}
