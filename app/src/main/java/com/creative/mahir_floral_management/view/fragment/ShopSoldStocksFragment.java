package com.creative.mahir_floral_management.view.fragment;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.creative.mahir_floral_management.R;
import com.creative.mahir_floral_management.adapters.ShopSoldStockAdapter;
import com.creative.mahir_floral_management.adapters.ShopStockAdapter;
import com.creative.mahir_floral_management.appdata.GlobalAppAccess;
import com.creative.mahir_floral_management.databinding.FragmentShopSoldStocksBinding;
import com.creative.mahir_floral_management.model.ShopStock;
import com.creative.mahir_floral_management.model.SoldStock;
import com.creative.mahir_floral_management.viewmodel.ShopSoldStockViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopSoldStocksFragment extends BaseFragment {

    private FragmentShopSoldStocksBinding binding;
    private int shop_id = 0;

    private List<SoldStock> soldStocks = new ArrayList<>(0);
    private ShopSoldStockAdapter adapter;

    public ShopSoldStocksFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (null == getArguments()) return;
        shop_id = getArguments().getInt(GlobalAppAccess.KEY_SHOP_ID);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shop_sold_stocks, container, false);
        binding.setLifecycleOwner(this);

        binding.setViewModel(ViewModelProviders.of(this).get(ShopSoldStockViewModel.class));
        binding.getViewModel().setShopID(shop_id);
        binding.getViewModel().setStringArray(getResources().getStringArray(R.array.year));

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        binding.rvResults.setLayoutManager(mLayoutManager);

        //Set Adapter
        adapter = new ShopSoldStockAdapter(soldStocks, null);
        adapter.setSoldStock(true);
        binding.rvResults.setAdapter(adapter);

        binding.getViewModel().validationLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                showLongToast(s);
            }
        });

        binding.getViewModel().mutableLiveData.observe(this, new Observer<List<SoldStock>>() {
            @Override
            public void onChanged(@Nullable List<SoldStock> stockList) {

                soldStocks.clear();

                if (null == stockList || stockList.size() == 0) {
                    showLongToast("No record found");
                } else
                    soldStocks.addAll(stockList);

                //Notify the adapter
                adapter.notifyDataSetChanged();

            }
        });

        binding.getViewModel().loadingLiveData.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean b) {

                if (b)
                    showProgressDialog("Loading...", true, false);
                else
                    dismissProgressDialog();

            }
        });

        binding.getViewModel().searchText.observe(this, new Observer<CharSequence>() {
            @Override
            public void onChanged(@Nullable CharSequence charSequence) {

                adapter.getFilter().filter(charSequence);
            }
        });

        return binding.getRoot();
    }

}
