package com.creative.mahir_floral_management.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.creative.mahir_floral_management.R;
import com.creative.mahir_floral_management.adapters.CustomSpinnerAdapter;
import com.creative.mahir_floral_management.appdata.MydApplication;
import com.creative.mahir_floral_management.databinding.FragmentDialogDeliverreadystockBinding;
import com.creative.mahir_floral_management.databinding.FragmentDialogEditReadyStockBinding;
import com.creative.mahir_floral_management.model.BaseModel;
import com.creative.mahir_floral_management.model.ReadyStock;
import com.creative.mahir_floral_management.model.Shop;
import com.creative.mahir_floral_management.viewmodel.ReadyStockDeliverViewModel;
import com.creative.mahir_floral_management.viewmodel.ReadyStockDialogUpdateViewModel;

import java.util.List;
import java.util.Locale;

public class ReadyStockUpdateDialogFragment extends BaseDialogFragment {

    private FragmentDialogEditReadyStockBinding binding;
    private ReadyStock stockData;
    private List<Shop> shops;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

            stockData = getArguments().getParcelable("stockData");

        }

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dialog_edit_ready_stock, container, false);
        binding.setLifecycleOwner(this);

        binding.setViewModel(ViewModelProviders.of(this).get(ReadyStockDialogUpdateViewModel.class));
        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        binding.getViewModel().validationLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

                if (null == s) return;
                showLongToast(0, s);

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

        binding.getViewModel().resultLiveData.observe(this, new Observer<BaseModel>() {
            @Override
            public void onChanged(@Nullable BaseModel baseModel) {

                if (null == baseModel) return;

                showLongToast(0, baseModel.getMessage());

                if (baseModel.getStatus()) {

                    dismiss();

                    ReadyStockFragment stockFragment = ((ReadyStockFragment) getTargetFragment());
                    if (null != stockFragment) {

                        ReadyStock readyStock = binding.getViewModel().getStock();
                        int totalQty = Integer.parseInt(stockData.getQuantity());

                        stockFragment.refreshScreen(readyStock);
                    }

                }

            }
        });

        //Fill data
        setFields();

        return binding.getRoot();
    }

    private void setFields() {

        if (null == stockData) {
            dismiss();
            return;
        }

        binding.getViewModel().setStock(stockData);

        //CustomSpinnerAdapter spinnerAdapter = new CustomSpinnerAdapter(getContext(), R.layout.spinner_item, R.id.tv_item,
        //        shops = MydApplication.getInstance().getPrefManger().getShops());

        binding.getViewModel().setUnitArray(getResources().getStringArray(R.array.units));


        binding.getViewModel().productName.setValue(stockData.getName());
        binding.getViewModel().productQuantity.setValue(stockData.getQuantity());
        binding.getViewModel().productPrice.setValue(stockData.getPrice());
        binding.getViewModel().unit.setValue(getUnitIndex(stockData.getUnit()));
        binding.getViewModel().productColor.setValue(stockData.getColor());

        //binding.tvProductQty.setText(String.format(Locale.getDefault(), getString(R.string.total_amount), stockData.getQuantity(), stockData.getUnit()));
       // binding.tvProductUnit.setText(stockData.getUnit());

    }

    private int getUnitIndex(String unitName){
        int count = 0;
        for( String name: getResources().getStringArray(R.array.units)){

            if(name.equals(unitName)) return count;

            count++;
        }

        return count;
    }





}
