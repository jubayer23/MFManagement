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
public class ShopStocksFragment extends Fragment {

    private int shop_id = 0;


    public ShopStocksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        shop_id = getArguments().getInt(GlobalAppAccess.KEY_SHOP_ID);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shop_stocks, container, false);



    }

}
