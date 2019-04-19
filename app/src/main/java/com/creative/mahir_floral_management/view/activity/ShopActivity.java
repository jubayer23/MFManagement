package com.creative.mahir_floral_management.view.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.creative.mahir_floral_management.R;
import com.creative.mahir_floral_management.Utility.CheckLocationEnableStatus;
import com.creative.mahir_floral_management.Utility.GpsEnableTool;
import com.creative.mahir_floral_management.appdata.GlobalAppAccess;
import com.creative.mahir_floral_management.databinding.ActivityHomeBinding;
import com.creative.mahir_floral_management.databinding.ActivityShopBinding;
import com.creative.mahir_floral_management.view.fragment.HomeFragment;
import com.creative.mahir_floral_management.view.fragment.ShopFragment;

public class ShopActivity extends BaseActivity {

    private static final String TAG_SHOP_FRAGMENT = "shop_fragment";
    private ShopFragment shopFragment;

    private ActivityShopBinding activityShopBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_shop);

        activityShopBinding = DataBindingUtil.setContentView(ShopActivity.this, R.layout.activity_shop);

        int shop_id = getIntent().getIntExtra(GlobalAppAccess.KEY_SHOP_ID, 2);
        String shop_name = getIntent().getStringExtra(GlobalAppAccess.KEY_SHOP_NAME);


        initToolbar(shop_name, true);



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

            shopFragment = new ShopFragment();
            shopFragment.setArguments(bundle);
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction();
            transaction.replace(R.id.content_layout, shopFragment, TAG_SHOP_FRAGMENT)
                    .commit();

        }

    }

}
