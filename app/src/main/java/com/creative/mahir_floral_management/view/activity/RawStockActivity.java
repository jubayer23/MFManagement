package com.creative.mahir_floral_management.view.activity;

import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import com.creative.mahir_floral_management.R;
import com.creative.mahir_floral_management.databinding.ActivityRawstockBinding;
import com.creative.mahir_floral_management.view.fragment.RawStockFragment;

public class RawStockActivity extends BaseActivity {

    private static final String TAG = "RawStock_Fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityRawstockBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_rawstock);


        initToolbar("Raw Stocks", true);



        if (savedInstanceState == null) {

            RawStockFragment rawStockFragment = new RawStockFragment();
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction();
            transaction.replace(R.id.content_layout, rawStockFragment, TAG)
                    .commit();

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
