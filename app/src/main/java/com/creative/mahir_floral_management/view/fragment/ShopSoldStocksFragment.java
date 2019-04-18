package com.creative.mahir_floral_management.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.creative.mahir_floral_management.R;
import com.creative.mahir_floral_management.appdata.GlobalAppAccess;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopSoldStocksFragment extends Fragment {


    int shop_id = 0;

    public ShopSoldStocksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        shop_id = getArguments().getInt(GlobalAppAccess.KEY_SHOP_ID);

        return inflater.inflate(R.layout.fragment_shop_sold_stocks, container, false);
    }

}
