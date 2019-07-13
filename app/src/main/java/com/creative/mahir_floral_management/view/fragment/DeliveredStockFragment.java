package com.creative.mahir_floral_management.view.fragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.creative.mahir_floral_management.R;
import com.creative.mahir_floral_management.adapters.DeliveredStockAdapter;
import com.creative.mahir_floral_management.databinding.FragmentDeliveredStockBinding;
import com.creative.mahir_floral_management.model.DeliveredStock;
import com.creative.mahir_floral_management.view.activity.ReadyStockEntryActivity;
import com.creative.mahir_floral_management.viewmodel.DeliveredStockViewModel;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class DeliveredStockFragment extends BaseFragment {

    private FragmentDeliveredStockBinding binding;

    private DeliveredStockAdapter deliveredStockAdapter;
    private List<DeliveredStock> deliveredStocks = new ArrayList<>(0);
    private DeliveredStock selectedItem;

    private final int rawEntrySuccess = 1002;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_delivered_stock, container, false);
        binding.setLifecycleOwner(this);

        binding.setViewModel(ViewModelProviders.of(this).get(DeliveredStockViewModel.class));

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        binding.rvResults.setLayoutManager(mLayoutManager);

        //Set Adapter
        deliveredStockAdapter = new DeliveredStockAdapter(deliveredStocks);
        binding.rvResults.setAdapter(deliveredStockAdapter);

        binding.getViewModel().setStringArray(getResources().getStringArray(R.array.year));

        binding.getViewModel().mutableLiveData.observe(this, new Observer<List<DeliveredStock>>() {
            @Override
            public void onChanged(@Nullable List<DeliveredStock> stockList) {

                deliveredStocks.clear();

                if (null == stockList || stockList.size() == 0) {
                    showLongToast("No record found");
                } else
                    deliveredStocks.addAll(stockList);

                //Notify the adapter
                deliveredStockAdapter.notifyDataSetChanged();

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

        binding.fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), ReadyStockEntryActivity.class), rawEntrySuccess);
            }
        });

        binding.getViewModel().searchText.observe(this, new Observer<CharSequence>() {
            @Override
            public void onChanged(@Nullable CharSequence charSequence) {

                deliveredStockAdapter.getFilter().filter(charSequence);
            }
        });


        // Inflate the layout for this fragment
        return binding.getRoot();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) return;

        if (requestCode == rawEntrySuccess) {
            binding.getViewModel().requestToRefresh();
        }

    }


}
