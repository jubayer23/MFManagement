package com.creative.mahir_floral_management.view.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;

import com.creative.mahir_floral_management.R;
import com.creative.mahir_floral_management.databinding.ActivityDeliveredStockBinding;
import com.creative.mahir_floral_management.databinding.ActivityDemandedStocksBinding;
import com.creative.mahir_floral_management.databinding.ActivityReadystockBinding;
import com.creative.mahir_floral_management.view.fragment.DeliveredStockFragment;
import com.creative.mahir_floral_management.view.fragment.ReadyStockFragment;

public class DeliveredStockActivity extends BaseActivity {

    private static final String TAG = "DeliveredStockFragment";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityDeliveredStockBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_delivered_stock);


        initToolbar("Supplies", true);



        if (savedInstanceState == null) {

            DeliveredStockFragment deliveredStockFragment = new DeliveredStockFragment();
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction();
            transaction.replace(R.id.content_layout, deliveredStockFragment, TAG)
                    .commit();

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
