package com.creative.mahir_floral_management.view.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.creative.mahir_floral_management.R;
import com.creative.mahir_floral_management.adapters.CustomSpinnerAdapter;
import com.creative.mahir_floral_management.appdata.MydApplication;
import com.creative.mahir_floral_management.databinding.FragmentDialogDeliverreadystockBinding;
import com.creative.mahir_floral_management.model.BaseModel;
import com.creative.mahir_floral_management.model.ReadyStock;
import com.creative.mahir_floral_management.model.ShopInfo;
import com.creative.mahir_floral_management.viewmodel.ReadyStockDeliverViewModel;

import java.util.List;
import java.util.Locale;

public class ReadyStockDeliverDialogFragment extends BaseDialogFragment implements AdapterView.OnItemSelectedListener {

    private FragmentDialogDeliverreadystockBinding binding;
    private ReadyStock stockData;
    private List<ShopInfo.Shop> shops;

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

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dialog_deliverreadystock, container, false);
        binding.setLifecycleOwner(this);

        binding.setViewModel(ViewModelProviders.of(this).get(ReadyStockDeliverViewModel.class));
        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        binding.getViewModel().validationLiveData.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer s) {

                if (null == s) return;

                switch (s) {

                    //Product amount not entered
                    case 1:
                        showLongToast(R.string.msg_enter_amount, "");
                        break;
                    //Shop not selected
                    case 2:
                        showLongToast(R.string.msg_select_shop, "");
                        break;
                    //Amount is greater than the total
                    case 3:
                        showLongToast(R.string.msg_amount_grater, "");
                        break;

                    //Comment is not entered
                    case 4:
                        showLongToast(R.string.msg_empty_comment, "");
                        break;

                }

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

                        int qtyDelivered = binding.getViewModel().getProductQuantityDelivered();
                        int totalQty = Integer.parseInt(stockData.getQuantity());

                        stockFragment.refreshScreen(totalQty - qtyDelivered);
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

        CustomSpinnerAdapter spinnerAdapter = new CustomSpinnerAdapter(getContext(), R.layout.spinner_item, R.id.tv_item,
                shops = MydApplication.getInstance().getPrefManger().getShops());

        binding.spShop.setPrompt("Select shop");
        binding.spShop.setAdapter(spinnerAdapter);
        binding.spShop.setOnItemSelectedListener(this);

        binding.tvProductName.setText(stockData.getName());
        binding.tvProductQty.setText(String.format(Locale.getDefault(), getString(R.string.total_amount), stockData.getQuantity(), stockData.getUnit()));
        binding.tvProductUnit.setText(stockData.getUnit());

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        binding.getViewModel().setSelectedShop(shops.get(position));

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



}
