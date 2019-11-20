package com.creative.mahir_floral_management.view.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;

import com.creative.mahir_floral_management.R;
import com.creative.mahir_floral_management.databinding.ActivityRawstockBinding;
import com.creative.mahir_floral_management.view.fragment.RawStockFragment;
import com.creative.mahir_floral_management.view.fragment.ReturnStockFragment;

public class ReturnStockActivity extends BaseActivity {

    private static final String TAG = "ReturnStock_Fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         DataBindingUtil.setContentView(this, R.layout.activity_return_stock);


        initToolbar("Return Stocks", true);



        if (savedInstanceState == null) {

            ReturnStockFragment returnStockFragment = new ReturnStockFragment();
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction();
            transaction.replace(R.id.content_layout, returnStockFragment, TAG)
                    .commit();

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
