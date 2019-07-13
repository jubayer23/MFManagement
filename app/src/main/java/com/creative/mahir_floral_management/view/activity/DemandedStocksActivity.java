package com.creative.mahir_floral_management.view.activity;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.creative.mahir_floral_management.R;
import com.creative.mahir_floral_management.databinding.ActivityDemandedStocksBinding;
import com.creative.mahir_floral_management.view.fragment.DemandedStocksFragment;

public class DemandedStocksActivity extends BaseActivity {

    private static final String TAG = "Demanded stocks";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_demanded_stocks);

        ActivityDemandedStocksBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_demanded_stocks);


        initToolbar("Demanded Stocks", true);



        if (savedInstanceState == null) {

            DemandedStocksFragment demandedStocksFragment = new DemandedStocksFragment();
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction();
            transaction.replace(R.id.content_layout, demandedStocksFragment, TAG)
                    .commit();

        }
    }
}
