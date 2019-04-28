package com.creative.mahir_floral_management.view.activity;

import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;

import com.creative.mahir_floral_management.R;
import com.creative.mahir_floral_management.appdata.GlobalAppAccess;
import com.creative.mahir_floral_management.databinding.ActivityShopAvailableReadyStockBinding;
import com.creative.mahir_floral_management.view.fragment.ShopAvailableReadyStockFragment;

public class ShopAvailableReadyStockActivity extends BaseActivity {

    private static final String TAG_SHOP_DEMAND_STOCKS_FRAGMENT = "shop_demand_stock_fragment";
    private ShopAvailableReadyStockFragment shopAvailableReadyStockFragment;

    private ActivityShopAvailableReadyStockBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_shop_demand_stock);

        binding = DataBindingUtil.setContentView(ShopAvailableReadyStockActivity.this, R.layout.activity_shop_available_ready_stock);

        initToolbar("Shop Demand Stocks", true);

        int shop_id = getIntent().getIntExtra(GlobalAppAccess.KEY_SHOP_ID, 2);

        if (savedInstanceState == null) {

            /**
             * This is marshmallow runtime Permissions
             * It will ask user for grand permission in queue order[FIFO]
             * If user gave all permission then check whether user device has google play service or not!
             * NB : before adding runtime request for permission Must add manifest permission for that
             * specific request
             * */


            //getSupportFragmentManager()
            //        .beginTransaction()
            //        .add(R.id.content_layout, new HouseListFragment(), TAG_HOME_FRAGMENT)
            //        .commit();

            Bundle bundle = new Bundle();
            bundle.putInt(GlobalAppAccess.KEY_SHOP_ID, shop_id);

            shopAvailableReadyStockFragment = new ShopAvailableReadyStockFragment();
            shopAvailableReadyStockFragment.setArguments(bundle);
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction();
            transaction.replace(R.id.content_layout, shopAvailableReadyStockFragment, TAG_SHOP_DEMAND_STOCKS_FRAGMENT)
                    .commit();


        }
    }
}
