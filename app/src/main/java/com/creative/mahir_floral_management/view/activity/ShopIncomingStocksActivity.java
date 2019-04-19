package com.creative.mahir_floral_management.view.activity;

import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.creative.mahir_floral_management.R;
import com.creative.mahir_floral_management.appdata.GlobalAppAccess;
import com.creative.mahir_floral_management.databinding.ActivityShopIncomingStocksBindingImpl;
import com.creative.mahir_floral_management.view.fragment.ShopIncomingStocksFragment;
import com.creative.mahir_floral_management.view.fragment.ShopStocksFragment;

public class ShopIncomingStocksActivity extends BaseActivity {

    private static final String TAG_SHOP_INCOME_STOCKS_FRAGMENT = "shop_income_stock_fragment";
    private ShopIncomingStocksFragment shopIncomingStocksFragment;

    private ActivityShopIncomingStocksBindingImpl activityShopIncomingStocksBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_home);

        activityShopIncomingStocksBinding = DataBindingUtil.setContentView(ShopIncomingStocksActivity.this, R.layout.activity_shop_incoming_stocks);

        initToolbar("Incoming Stocks", true);

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

            shopIncomingStocksFragment = new ShopIncomingStocksFragment();
            shopIncomingStocksFragment.setArguments(bundle);
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction();
            transaction.replace(R.id.content_layout, shopIncomingStocksFragment, TAG_SHOP_INCOME_STOCKS_FRAGMENT)
                    .commit();


        }
    }
}
