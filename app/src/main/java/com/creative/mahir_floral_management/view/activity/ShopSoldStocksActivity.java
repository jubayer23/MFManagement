package com.creative.mahir_floral_management.view.activity;

import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.creative.mahir_floral_management.R;
import com.creative.mahir_floral_management.appdata.GlobalAppAccess;
import com.creative.mahir_floral_management.databinding.ActivityShopSoldStocksBinding;
import com.creative.mahir_floral_management.databinding.ActivityShopStocksBinding;
import com.creative.mahir_floral_management.view.fragment.ShopSoldStocksFragment;
import com.creative.mahir_floral_management.view.fragment.ShopStocksFragment;

public class ShopSoldStocksActivity extends BaseActivity {


    private static final String TAG_SHOP_SOLD_STOCKS_FRAGMENT = "shop_stock_sold_fragment";
    private ShopSoldStocksFragment shopSoldStocksFragment;

    private ActivityShopSoldStocksBinding activityShopSoldStocksBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_home);

        activityShopSoldStocksBinding = DataBindingUtil.setContentView(ShopSoldStocksActivity.this, R.layout.activity_shop_sold_stocks);

        initToolbar("Shop Sold Stocks", true);

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

            shopSoldStocksFragment = new ShopSoldStocksFragment();
            shopSoldStocksFragment.setArguments(bundle);
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction();
            transaction.replace(R.id.content_layout, shopSoldStocksFragment, TAG_SHOP_SOLD_STOCKS_FRAGMENT)
                    .commit();


        }
    }
}
