package com.creative.mahir_floral_management.view.activity;

import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;

import com.creative.mahir_floral_management.R;
import com.creative.mahir_floral_management.databinding.ActivityReadystockBinding;
import com.creative.mahir_floral_management.view.fragment.ReadyStockFragment;

public class ReadyStockActivity extends BaseActivity {

    private static final String TAG = "ReadyStock_Fragment";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityReadystockBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_readystock);


        initToolbar("Ready Stocks", true);



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
