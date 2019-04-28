package com.creative.mahir_floral_management.view.fragment;

import android.app.AlertDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.creative.mahir_floral_management.R;
import com.creative.mahir_floral_management.adapters.CustomSpinnerAdapter;
import com.creative.mahir_floral_management.adapters.DemandedStockAdapter;
import com.creative.mahir_floral_management.appdata.MydApplication;
import com.creative.mahir_floral_management.databinding.FragmentDemandedStocksBinding;
import com.creative.mahir_floral_management.model.BaseModel;
import com.creative.mahir_floral_management.model.DemandedStock;
import com.creative.mahir_floral_management.model.Shop;
import com.creative.mahir_floral_management.viewmodel.DemandedStocksViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DemandedStocksFragment extends BaseFragment implements DemandedStockAdapter.OnItemClickListener {

    private DemandedStocksViewModel mViewModel;

    private FragmentDemandedStocksBinding binding;

    private List<DemandedStock> demandedStocks = new ArrayList<>(0);
    private DemandedStock selectedItem;

    private List<Shop> shops = new ArrayList<>();

    private DemandedStockAdapter adapter;

    private AlertDialog alertDialog;

    public static DemandedStocksFragment newInstance() {
        return new DemandedStocksFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_demanded_stocks, container, false);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_demanded_stocks, container, false);
        binding.setLifecycleOwner(this);

        binding.setViewModel(ViewModelProviders.of(this).get(DemandedStocksViewModel.class));
        // binding.getViewModel().setShopID(shop_id);
        // binding.getViewModel().setStringArray(getResources().getStringArray(R.array.year));
        // CommonMethods.hideKeyboardForcely(getActivity(), binding.etSearch);


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        binding.rvResults.setLayoutManager(mLayoutManager);

        //Set Adapter
        adapter = new DemandedStockAdapter(demandedStocks, this);
        binding.rvResults.setAdapter(adapter);


        binding.getViewModel().mutableLiveData.observe(this, new Observer<List<DemandedStock>>() {
            @Override
            public void onChanged(@Nullable List<DemandedStock> stockList) {

                demandedStocks.clear();

                if (null == stockList || stockList.size() == 0) {
                    showLongToast("No record found");
                } else
                    demandedStocks.addAll(stockList);

                //Notify the adapter
                adapter.notifyDataSetChanged();

            }
        });

        binding.getViewModel().markCompletedLiveData.observe(this, new Observer<BaseModel>() {
            @Override
            public void onChanged(@Nullable BaseModel baseModel) {

                if (baseModel == null) return;

                if (baseModel.getStatus()) {

                    //Remove the item from the list
                    if (null != selectedItem) {

                        demandedStocks.remove(selectedItem);
                        adapter.notifyDataSetChanged();
                        selectedItem = null;
                    }

                }

                showLongToast(baseModel.getMessage());

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

        setFields();

        return binding.getRoot();
    }

    private void setFields() {


        Shop shopAll = new Shop();

        shopAll.setName("All");
        shops.add(shopAll);
        shops.addAll(MydApplication.getInstance().getPrefManger().getShops());

        CustomSpinnerAdapter spinnerAdapter = new CustomSpinnerAdapter(getContext(), R.layout.spinner_item, R.id.tv_item,shops);

        binding.spShop.setPrompt("Select shop");
        binding.spShop.setAdapter(spinnerAdapter);
        // binding.spShop.setOnItemSelectedListener(this);


    }


    @Override
    public void onItemClick(DemandedStock item) {
        markDemandComplete(item);
    }

    private void markDemandComplete(final DemandedStock item) {

        selectedItem = item;

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle(R.string.title_mark_as_completed)
                .setMessage(String.format(Locale.getDefault(), getString(R.string.msg_mark_as_completed), item.getDemandedShopName()))
                .setCancelable(false)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        binding.getViewModel().markReceive(item);
                        dialog.dismiss();

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        alertDialog = builder.create();
        alertDialog.show();

    }

    @Override
    public void onPause() {
        if (null != alertDialog)
            alertDialog.dismiss();

        super.onPause();
    }
}
