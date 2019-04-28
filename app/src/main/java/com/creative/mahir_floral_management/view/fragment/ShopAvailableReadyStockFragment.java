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
import com.creative.mahir_floral_management.adapters.ShopDemandStockAdapter;
import com.creative.mahir_floral_management.appdata.GlobalAppAccess;
import com.creative.mahir_floral_management.databinding.FragmentShopAvailableReadyStockBinding;
import com.creative.mahir_floral_management.model.ReadyStock;
import com.creative.mahir_floral_management.view.alertbanner.AlertDialogForAnything;
import com.creative.mahir_floral_management.viewmodel.ShopAvailableReadyStockViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopAvailableReadyStockFragment extends BaseFragment implements ShopDemandStockAdapter.OnItemClickListener {

    private FragmentShopAvailableReadyStockBinding binding;

    private int shop_id = 0;
    private ShopDemandStockAdapter adapter;
    private List<ReadyStock> readyStocks = new ArrayList<>(0);
    private ReadyStock selectedItem;

    public ShopAvailableReadyStockFragment() {
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
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_shop_demand_stock, container, false);

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_shop_available_ready_stock, container, false);
        binding.setLifecycleOwner(this);

        binding.setViewModel(ViewModelProviders.of(this).get(ShopAvailableReadyStockViewModel.class));
        binding.getViewModel().setShopID(shop_id);
        binding.getViewModel().setStringArray(getResources().getStringArray(R.array.year));

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        binding.rvResults.setLayoutManager(mLayoutManager);

        //Set Adapter
        adapter = new ShopDemandStockAdapter(readyStocks, this);
        binding.rvResults.setAdapter(adapter);


        binding.getViewModel().setStringArray(getResources().getStringArray(R.array.year));

        binding.getViewModel().mutableLiveData.observe(this, new Observer<List<ReadyStock>>() {
            @Override
            public void onChanged(@Nullable List<ReadyStock> stockList) {

                readyStocks.clear();

                if (null == stockList || stockList.size() == 0) {
                    showLongToast("No record found");
                } else
                    readyStocks.addAll(stockList);

                //Notify the adapter
                adapter.notifyDataSetChanged();

            }
        });

        binding.getViewModel().validationLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                showLongToast(s);
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

        /*binding.fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), EntryReadyStockActivity.class), rawEntrySuccess);
            }
        });*/

        binding.getViewModel().searchText.observe(this, new Observer<CharSequence>() {
            @Override
            public void onChanged(@Nullable CharSequence charSequence) {

                adapter.getFilter().filter(charSequence);
            }
        });


        return binding.getRoot();
    }

    @Override
    public void onItemClick(ReadyStock item) {
        selectedItem = item;
        showMakeDemandDialog(item);
    }

    private void showMakeDemandDialog(final ReadyStock item) {

        MakeDemandDialogFragment editNameDialog = new MakeDemandDialogFragment();

        Bundle data = new Bundle();
        data.putParcelable("stockData", item);
        data.putInt(GlobalAppAccess.KEY_SHOP_ID, shop_id);

        editNameDialog.setArguments(data);
        editNameDialog.setTargetFragment(ShopAvailableReadyStockFragment.this, 1337);
        editNameDialog.show(getFragmentManager(), "fragment_edit_name");
    }

    public void showSuccessDialog(){
        AlertDialogForAnything.showNotifyDialog(getActivity(),AlertDialogForAnything.ALERT_TYPE_SUCCESS, "Demand successful");
    }
}
