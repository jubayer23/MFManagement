package com.creative.mahir_floral_management.view.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.creative.mahir_floral_management.R;
import com.creative.mahir_floral_management.appdata.GlobalAppAccess;
import com.creative.mahir_floral_management.databinding.FragmentHomeBinding;
import com.creative.mahir_floral_management.databinding.FragmentShopBinding;
import com.creative.mahir_floral_management.view.activity.CheckInOutActivity;
import com.creative.mahir_floral_management.view.activity.HQActivity;
import com.creative.mahir_floral_management.view.activity.ShopIncomingStocksActivity;
import com.creative.mahir_floral_management.view.activity.ShopSoldStocksActivity;
import com.creative.mahir_floral_management.view.activity.ShopStocksActivity;
import com.creative.mahir_floral_management.viewmodel.HomeFragViewModel;
import com.creative.mahir_floral_management.viewmodel.ShopFragViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopFragment extends Fragment {


    private FragmentShopBinding fragmentShopBinding;
    private ShopFragViewModel shopFragViewModel;

    private int shop_id = 0;


    public ShopFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        shopFragViewModel = ViewModelProviders.of(this).get(ShopFragViewModel.class);

        fragmentShopBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_shop,container,false);
        fragmentShopBinding.setLifecycleOwner(this);
        fragmentShopBinding.setShopFragViewModel(shopFragViewModel);

        View view = fragmentShopBinding.getRoot();


        shop_id = getArguments().getInt(GlobalAppAccess.KEY_SHOP_ID);
        Log.d("DEBUG", String.valueOf(shop_id));
        // Inflate the layout for this fragment
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        observerUserClickEvent();
    }

    private void observerUserClickEvent(){
        shopFragViewModel.getUserButtonClickEvent().observe(this, new Observer<View>() {
            @Override
            public void onChanged(@Nullable View view) {
                int id = view.getId();
                if(id == fragmentShopBinding.btnShopStock.getId()){

                    Intent intent = new Intent(getActivity(), ShopStocksActivity.class);
                    intent.putExtra(GlobalAppAccess.KEY_SHOP_ID, shop_id);
                    startActivity(intent);
                    //startActivity(new Intent(getActivity(), HQActivity.class));
                }else if(id == fragmentShopBinding.btnSold.getId()){
                    Intent intent = new Intent(getActivity(), ShopSoldStocksActivity.class);
                    intent.putExtra(GlobalAppAccess.KEY_SHOP_ID, shop_id);
                    startActivity(intent);
                }else if(id == fragmentShopBinding.btnShopIncomingStock.getId()){
                    //startActivity(new Intent(getActivity(), CheckInOutActivity.class));
                    Intent intent = new Intent(getActivity(), ShopIncomingStocksActivity.class);
                    intent.putExtra(GlobalAppAccess.KEY_SHOP_ID, shop_id);
                    startActivity(intent);
                }
            }
        });

    }

}
