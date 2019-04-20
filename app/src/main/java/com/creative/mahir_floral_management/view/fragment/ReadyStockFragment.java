package com.creative.mahir_floral_management.view.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
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
import com.creative.mahir_floral_management.adapters.RawStockAdapter;
import com.creative.mahir_floral_management.databinding.FragmentReadystockBinding;
import com.creative.mahir_floral_management.model.RawStock;
import com.creative.mahir_floral_management.view.activity.EntryReadyStockActivity;
import com.creative.mahir_floral_management.viewmodel.ReadyStockViewModel;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class ReadyStockFragment extends BaseFragment implements RawStockAdapter.OnItemClickListener {

    private FragmentReadystockBinding binding;

    private RawStockAdapter adapter;
    private List<RawStock> rawStockList = new ArrayList<>(0);
    private RawStock selectedItem;

    private final int rawEntrySuccess = 1002;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_readystock, container, false);
        binding.setLifecycleOwner(this);

        binding.setViewModel(ViewModelProviders.of(this).get(ReadyStockViewModel.class));

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        binding.rvResults.setLayoutManager(mLayoutManager);

        //Set Adapter
        adapter = new RawStockAdapter(rawStockList, this);
        binding.rvResults.setAdapter(adapter);

        binding.getViewModel().setStringArray(getResources().getStringArray(R.array.year));

        binding.getViewModel().mutableLiveData.observe(this, new Observer<List<RawStock>>() {
            @Override
            public void onChanged(@Nullable List<RawStock> stockList) {

                rawStockList.clear();

                if (null == stockList || stockList.size() == 0) {
                    showLongToast("No record found");
                } else
                    rawStockList.addAll(stockList);

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

        binding.fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), EntryReadyStockActivity.class), rawEntrySuccess);
            }
        });


        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onItemClick(RawStock item) {

        selectedItem = item;
        showDeliverDialog(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) return;

        if (requestCode == rawEntrySuccess) {
            binding.getViewModel().requestToRefresh();
        }

    }

    public void refreshScreen(int remainingQty) {

        if (null == selectedItem) return;

        if (remainingQty <= 0) {
            rawStockList.remove(selectedItem);
        } else {

            int index = rawStockList.indexOf(selectedItem);
            RawStock rawStock = rawStockList.get(index);

            rawStock.setQuantity(String.valueOf(remainingQty));
            rawStockList.set(index, rawStock);
        }

        adapter.notifyDataSetChanged();

        selectedItem = null;

    }

    private void showDeliverDialog(final RawStock item) {

        DeliverReadyStockDialogFragment editNameDialog = new DeliverReadyStockDialogFragment();

        Bundle data = new Bundle();
        data.putParcelable("stockData", item);

        editNameDialog.setArguments(data);
        editNameDialog.setTargetFragment(ReadyStockFragment.this, 1337);
        editNameDialog.show(getFragmentManager(), "fragment_edit_name");
    }
}
