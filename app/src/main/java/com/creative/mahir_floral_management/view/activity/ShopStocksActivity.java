package com.creative.mahir_floral_management.view.activity;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.creative.mahir_floral_management.R;
import com.creative.mahir_floral_management.appdata.GlobalAppAccess;
import com.creative.mahir_floral_management.databinding.ActivityShopStocksBinding;
import com.creative.mahir_floral_management.view.fragment.ShopStocksFragment;

public class ShopStocksActivity extends BaseActivity {


    private static final String TAG_SHOP_STOCKS_FRAGMENT = "shop_stock_fragment";
    private ShopStocksFragment shopStocksFragment;

    private ActivityShopStocksBinding activityShopStocksBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int shop_id = getIntent().getIntExtra(GlobalAppAccess.KEY_SHOP_ID, 2);
        String shop_name = getIntent().getStringExtra(GlobalAppAccess.KEY_SHOP_NAME);
        if(shop_id == GlobalAppAccess.SHOP_ID_CONNECTICUT){
            setTheme(R.style.AppTheme_NoActionBar_Connecticut);
        }else if(shop_id == GlobalAppAccess.SHOP_ID_EAST){
            setTheme(R.style.AppTheme_NoActionBar_East);
        }else if(shop_id == GlobalAppAccess.SHOP_ID_WEST){
            setTheme(R.style.AppTheme_NoActionBar_West);
        }else if(shop_id == GlobalAppAccess.SHOP_ID_VILLAGE){
            setTheme(R.style.AppTheme_NoActionBar_Village);
        }else if(shop_id == GlobalAppAccess.SHOP_ID_HUDSONYARD){
            setTheme(R.style.AppTheme_NoActionBar_Hudson);
        }

        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_home);

        activityShopStocksBinding = DataBindingUtil.setContentView(ShopStocksActivity.this, R.layout.activity_shop_stocks);

        initToolbar("Shop Stocks", true);

        //int shop_id = getIntent().getIntExtra(GlobalAppAccess.KEY_SHOP_ID, 2);

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

            shopStocksFragment = new ShopStocksFragment();
            shopStocksFragment.setArguments(bundle);
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction();
            transaction.replace(R.id.content_layout, shopStocksFragment, TAG_SHOP_STOCKS_FRAGMENT)
                    .commit();


        }
    }
}
